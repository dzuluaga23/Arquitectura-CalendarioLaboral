package controladores;

import entidades.Tipo;
import servicios.ITipoServicio; // Se conecta con la interfaz en Core
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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

    @PostMapping("/agregar")
    public ResponseEntity<Tipo> agregar(@RequestBody Tipo tipo) {
        Tipo tipoCreado = tipoServicio.crear(tipo);
        URI ubicacion = URI.create("/api/tipos/obtener/" + tipoCreado.getId());
        return ResponseEntity.created(ubicacion).body(tipoCreado);
    }

    @PutMapping("/modificar")
    public ResponseEntity<Tipo> modificar(@RequestBody Tipo tipo) {
        Tipo tipoModificado = tipoServicio.modificar(tipo);
        if (tipoModificado != null) {
            return ResponseEntity.ok(tipoModificado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable long id) {
        tipoServicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
