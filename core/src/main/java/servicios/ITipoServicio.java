package servicios;

import entidades.Tipo;
import java.util.List;

public interface ITipoServicio {
    List<Tipo> listar();
    Tipo obtener(long id);
    Tipo crear(Tipo tipo);
    Tipo modificar(Tipo tipo);
    void eliminar(long id);
}
