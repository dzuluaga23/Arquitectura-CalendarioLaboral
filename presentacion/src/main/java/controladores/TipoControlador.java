package controladores;

import entidades.Tipo;
import servicios.ITipoServicio; // Se conecta con la interfaz en Core
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos")
public class TipoControlador {

    private final ITipoServicio tipoServicio;

    // Inyeccion de dependencias por constructor
    public TipoControlador(ITipoServicio tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Tipo>> listar() {
        return ResponseEntity.ok(tipoServicio.listar());
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<Tipo> obtenerPorId(@PathVariable long id) {
        Tipo tipo = tipoServicio.obtener(id);
        if (tipo != null) {
            return ResponseEntity.ok(tipo);
        }
        return ResponseEntity.notFound().build();
    }
}