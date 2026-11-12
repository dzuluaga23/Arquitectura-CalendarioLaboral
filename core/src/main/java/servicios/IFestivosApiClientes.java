package servicios;

import entidades.Calendario;
import java.util.List;

/**
 * Interfaz de integración con la API Festivos externa (Node.js).
 */
public interface IFestivosApiClientes {
    List<Calendario> obtenerFestivosPorAño(int año);
}
