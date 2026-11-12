package excepciones;

/**
 * Se lanza cuando la API Festivos externa (Node.js) no está disponible
 * o responde con un error al consultar los festivos.
 */
public class ServicioFestivosNoDisponibleException extends RuntimeException {

    public ServicioFestivosNoDisponibleException(String mensaje) {
        super(mensaje);
    }

    public ServicioFestivosNoDisponibleException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
