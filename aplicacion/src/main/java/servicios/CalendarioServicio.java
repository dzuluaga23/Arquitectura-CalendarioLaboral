package servicios;

import entidades.Calendario;
import entidades.Tipo;
import repositorios.ICalendarioRepositorio;
import repositorios.ITipoRepositorio;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class CalendarioServicio implements ICalendarioServicio {

    private static final String NOMBRE_TIPO_FESTIVO = "Día festivo";

    private final ICalendarioRepositorio calendarioRepositorio;
    private final ITipoRepositorio tipoRepositorio;
    private final IFestivosApiClientes festivosApiClientes;

    public CalendarioServicio(ICalendarioRepositorio calendarioRepositorio,
                              ITipoRepositorio tipoRepositorio,
                              IFestivosApiClientes festivosApiClientes) {
        this.calendarioRepositorio = calendarioRepositorio;
        this.tipoRepositorio = tipoRepositorio;
        this.festivosApiClientes = festivosApiClientes;
    }

    @Override
    public List<Calendario> listar() {
        return calendarioRepositorio.listar();
    }

    @Override
    public Calendario obtener(long id) {
        return calendarioRepositorio.obtener(id);
    }

    // Consulta la lista anual de festivos a la API externa y verifica en memoria.
    // Así solo se hace una llamada por año en vez de una por cada día.
    @Override
    public boolean esFestivo(int año, int mes, int dia) {
        validarFecha(año, mes, dia);

        LocalDate fechaConsulta = LocalDate.of(año, mes, dia);
        return obtenerFestivosPorAño(año).stream().anyMatch(calendario ->
                calendario.getFecha() != null
                        && fechaConsulta.equals(aLocalDate(calendario.getFecha())));
    }

    // Consulta la lista de festivos del año a la API externa y la persiste en PostgreSQL
    @Override
    public List<Calendario> obtenerFestivosPorAño(int año) {
        validarAño(año);

        List<Calendario> festivos = festivosApiClientes.obtenerFestivosPorAño(año);
        guardarFestivos(festivos);
        return festivos;
    }

    // Guarda en PostgreSQL los festivos calculados, evitando duplicados por fecha
    private void guardarFestivos(List<Calendario> festivos) {
        if (festivos == null || festivos.isEmpty()) {
            return;
        }

        Tipo tipoFestivo = obtenerTipoFestivo();

        for (Calendario festivo : festivos) {
            festivo.setTipo(tipoFestivo);
            if (calendarioRepositorio.obtenerPorFecha(festivo.getFecha()) == null) {
                calendarioRepositorio.guardar(festivo);
            }
        }
    }

    // Obtiene el tipo de festivo existente en la BD (comparación sin espacios
    // finales y sin importar mayúsculas). No inserta en la tabla tipo: esos
    // registros son datos maestros del script de creación de la BD.
    private Tipo obtenerTipoFestivo() {
        return tipoRepositorio.listar().stream()
                .filter(tipo -> tipo.getTipo() != null
                        && tipo.getTipo().trim().equalsIgnoreCase(NOMBRE_TIPO_FESTIVO))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "El tipo '" + NOMBRE_TIPO_FESTIVO + "' no existe en la base de datos. "
                                + "Ejecute los INSERT del script de creación de la BD."));
    }

    private void validarAño(int año) {
        if (año < 1900 || año > 2099) {
            throw new IllegalArgumentException("El año debe estar entre 1900 y 2099");
        }
    }

    private void validarFecha(int año, int mes, int dia) {
        validarAño(año);
        if (mes < 1 || mes > 12) {
            throw new IllegalArgumentException("El mes debe estar entre 1 y 12");
        }
        if (dia < 1 || dia > 31) {
            throw new IllegalArgumentException("El día debe estar entre 1 y 31");
        }
    }

    private LocalDate aLocalDate(Date fecha) {
        return fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
