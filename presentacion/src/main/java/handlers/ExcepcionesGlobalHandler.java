package handlers;

import excepciones.ServicioFestivosNoDisponibleException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.DateTimeException;
import java.util.Map;

/**
 * Handlers de excepciones globales para todos los controladores REST.
 */
@RestControllerAdvice
public class ExcepcionesGlobalHandler {

    // La API Festivos externa (Node.js) está caída o no respondió a tiempo
    @ExceptionHandler(ServicioFestivosNoDisponibleException.class)
    public ResponseEntity<Map<String, String>> manejarServicioNoDisponible(
            ServicioFestivosNoDisponibleException excepcion) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of("mensaje", excepcion.getMessage()));
    }

    // Fecha o año inválido (ej. mes 13, día 32, año 1800)
    @ExceptionHandler({IllegalArgumentException.class, DateTimeException.class})
    public ResponseEntity<Map<String, String>> manejarSolicitudInvalida(RuntimeException excepcion) {
        return ResponseEntity.badRequest()
                .body(Map.of("mensaje", excepcion.getMessage()));
    }
}
