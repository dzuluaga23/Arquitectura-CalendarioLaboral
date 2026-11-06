package clientes;

import clientes.dto.FestivoDto;
import entidades.Calendario;
import entidades.Tipo;
import servicios.IFestivosApiClientes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@SuppressWarnings("null")
public class FestivosApiClient implements IFestivosApiClientes {

    private final RestClient restClient;

    public FestivosApiClient(@Value("${api.festivos.url}") String baseUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public List<Calendario> obtenerFestivosPorAño(int año) {
        FestivoDto[] festivosDto = restClient.get()
                .uri("/festivos/obtener/{anio}", año)
                .retrieve()
                .body(FestivoDto[].class);

        List<Calendario> listaCalendario = new ArrayList<>();

        if (festivosDto != null) {
            for (FestivoDto dto : festivosDto) {
                Tipo tipo = new Tipo(dto.getIdTipo(), "Festivo");
                Calendario calendario = new Calendario(0, new Date(), tipo, dto.getNombre());
                listaCalendario.add(calendario);
            }
        }

        return listaCalendario;
    }

    @Override
    public boolean verificarEsFestivo(int año, int mes, int dia) {
        Boolean esFestivo = restClient.get()
                .uri("/festivos/verificar/{anio}/{mes}/{dia}", año, mes, dia)
                .retrieve()
                .body(Boolean.class);

        return Boolean.TRUE.equals(esFestivo);
    }
}