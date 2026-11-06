package controladores;

import entidades.Calendario;
import servicios.ICalendarioServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/calendario")
public class CalendarioControlador {

    private final ICalendarioServicio calendarioServicio;

    public CalendarioControlador(ICalendarioServicio calendarioServicio) {
        this.calendarioServicio = calendarioServicio;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Calendario>> listar() {
        return ResponseEntity.ok(calendarioServicio.listar());
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<Calendario> obtenerPorId(@PathVariable long id) {
        Calendario calendario = calendarioServicio.obtener(id);
        return calendario != null ? ResponseEntity.ok(calendario) : ResponseEntity.notFound().build();
    }

    @GetMapping("/es-festivo/{anio}/{mes}/{dia}")
    public ResponseEntity<Boolean> esFestivo(
            @PathVariable int anio,
            @PathVariable int mes,
            @PathVariable int dia) {
        return ResponseEntity.ok(calendarioServicio.esFestivo(anio, mes, dia));
    }

    @GetMapping("/festivos-año/{anio}")
    public ResponseEntity<List<Calendario>> obtenerFestivosPorAño(@PathVariable int anio) {
        return ResponseEntity.ok(calendarioServicio.obtenerFestivosPorAño(anio));
    }
}