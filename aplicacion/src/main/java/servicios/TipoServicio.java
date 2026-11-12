package servicios;

import repositorios.ICalendarioRepositorio;
import repositorios.ITipoRepositorio;
import entidades.Tipo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoServicio implements ITipoServicio {

    private final ITipoRepositorio tipoRepositorio;
    private final ICalendarioRepositorio calendarioRepositorio;

    public TipoServicio(ITipoRepositorio tipoRepositorio, ICalendarioRepositorio calendarioRepositorio) {
        this.tipoRepositorio = tipoRepositorio;
        this.calendarioRepositorio = calendarioRepositorio;
    }

    @Override
    public List<Tipo> listar() {
        return tipoRepositorio.listar();
    }

    @Override
    public Tipo obtener(long id) {
        return tipoRepositorio.obtener(id);
    }

    @Override
    public Tipo crear(Tipo tipo) {
        validar(tipo);
        validarNombreDuplicado(tipo.getTipo(), 0);
        return tipoRepositorio.guardar(tipo);
    }

    @Override
    public Tipo modificar(Tipo tipo) {
        if (tipo == null || tipo.getId() <= 0) {
            throw new IllegalArgumentException("El id del tipo es obligatorio para modificar");
        }
        if (tipoRepositorio.obtener(tipo.getId()) == null) {
            return null;
        }
        validar(tipo);
        validarNombreDuplicado(tipo.getTipo(), tipo.getId());
        return tipoRepositorio.modificar(tipo);
    }

    @Override
    public void eliminar(long id) {
        if (tipoRepositorio.obtener(id) == null) {
            throw new IllegalArgumentException("No existe el tipo con id=" + id);
        }
        if (calendarioRepositorio.contarPorTipo(id) > 0) {
            throw new IllegalArgumentException(
                    "No se puede eliminar el tipo con id=" + id + " porque tiene calendarios asociados");
        }
        tipoRepositorio.eliminar(id);
    }

    private void validar(Tipo tipo) {
        if (tipo == null || tipo.getTipo() == null || tipo.getTipo().isBlank()) {
            throw new IllegalArgumentException("El nombre del tipo es obligatorio");
        }
    }

    // Evita nombres repetidos comparando sin espacios finales y sin importar
    // mayúsculas, descartando el propio tipo cuando se trata de una modificación
    private void validarNombreDuplicado(String nombre, long idAExcluir) {
        boolean duplicado = tipoRepositorio.listar().stream()
                .anyMatch(tipo -> tipo.getId() != idAExcluir
                        && tipo.getTipo() != null
                        && tipo.getTipo().trim().equalsIgnoreCase(nombre.trim()));
        if (duplicado) {
            throw new IllegalArgumentException("Ya existe un tipo con el nombre '" + nombre.trim() + "'");
        }
    }
}
