package clientes;

import clientes.dto.FestivoDto;
import entidades.Calendario;
import entidades.Tipo;
import excepciones.ServicioFestivosNoDisponibleException;
import servicios.IFestivosApiClientes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Cliente de integración hacia la API Festivos externa (Node.js/Express, puerto 3000).
 * Implementa la interfaz de integración definida en el módulo core.
 */
@Service
@SuppressWarnings("null")
public class FestivosApiClient implements IFestivosApiClientes {

    private final RestClient restClient;

    public FestivosApiClient(@Value("${api.festivos.url}") String baseUrl) {
        SimpleClientHttpRequestFactory fabrica = new SimpleClientHttpRequestFactory();
        fabrica.setConnectTimeout(5000);  // 5 segundos para conectar
        fabrica.setReadTimeout(10000);    // 10 segundos de espera por la respuesta

        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .requestFactory(fabrica)
                .build();
    }

    @Override
    public List<Calendario> obtenerFestivosPorAño(int año) {
        FestivoDto[] festivosDto;
        try {
            festivosDto = restClient.get()
                    .uri("/festivos/obtener/{anio}", año)
                    .retrieve()
                    .body(FestivoDto[].class);
        } catch (RestClientException excepcion) {
            throw new ServicioFestivosNoDisponibleException(
                    "No se pudo consultar la API Festivos externa. Verifica que esté en ejecución en "
                            + "el puerto 3000.",
                    excepcion);
        }

        List<Calendario> listaCalendario = new ArrayList<>();

        if (festivosDto != null) {
            for (FestivoDto dto : festivosDto) {
                Date fecha = parsearFecha(dto.getFecha());
                if (fecha == null) {
                    continue; // Ignora festivos con fecha ilegible
                }
                Tipo tipo = new Tipo(0, "Festivo");
                listaCalendario.add(new Calendario(0, fecha, tipo, dto.getFestivo()));
            }
        }

        return listaCalendario;
    }

    /**
     * Convierte la fecha en texto "YYYY-MM-DD" de la API externa a java.util.Date.
     */
    private Date parsearFecha(String fecha) {
        if (fecha == null || fecha.isBlank()) {
            return null;
        }
        try {
            return Date.from(LocalDate.parse(fecha).atStartOfDay(ZoneId.systemDefault()).toInstant());
        } catch (DateTimeParseException excepcion) {
            return null;
        }
    }
}
