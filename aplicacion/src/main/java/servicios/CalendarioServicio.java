package servicios;

import entidades.Calendario;
import repositorios.ICalendarioRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalendarioServicio implements ICalendarioServicio {

    private final ICalendarioRepositorio calendarioRepositorio;
    private final IFestivosApiClientes festivosApiClientes;

    public CalendarioServicio(ICalendarioRepositorio calendarioRepositorio, IFestivosApiClientes festivosApiClientes) {
        this.calendarioRepositorio = calendarioRepositorio;
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

    // Consulta directa a la API externa para verificar festivo por fecha
    @Override
    public boolean esFestivo(int año, int mes, int dia) {
        return festivosApiClientes.verificarEsFestivo(año, mes, dia);
    }

    // Consulta la lista de festivos del año a la API externa
    public List<Calendario> obtenerFestivosPorAño(int año) {
        return festivosApiClientes.obtenerFestivosPorAño(año);
    }
}