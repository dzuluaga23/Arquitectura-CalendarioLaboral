package servicios;

import entidades.Calendario;
import java.util.List;

public interface IFestivosApiClientes {
    List<Calendario> obtenerFestivosPorAño(int año);
    boolean verificarEsFestivo(int año, int mes, int dia);
}
