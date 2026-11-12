package repositorios;

import entidades.Calendario;
import java.util.Date;
import java.util.List;

public interface ICalendarioRepositorio {
    List<Calendario> listar();
    Calendario obtener(long id);
    Calendario obtenerPorFecha(Date fecha);
    Calendario guardar(Calendario calendario);
    long contarPorTipo(long idTipo);
}