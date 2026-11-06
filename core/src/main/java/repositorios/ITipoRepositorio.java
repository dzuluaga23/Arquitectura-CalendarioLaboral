package repositorios;

import entidades.Tipo;
import java.util.List;

public interface ITipoRepositorio {
    List<Tipo> listar();
    Tipo obtener(long id);
}