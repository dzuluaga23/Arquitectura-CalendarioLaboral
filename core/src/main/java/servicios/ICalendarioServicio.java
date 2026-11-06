package servicios;

import entidades.Calendario;
import java.util.List;

public interface ICalendarioServicio {
    List<Calendario> listar();
    Calendario obtener(long id);
    boolean esFestivo(int anio, int mes, int dia);

    List<Calendario> obtenerFestivosPorAño(int año);
}