package servicios;

import repositorios.ITipoRepositorio;
import entidades.Tipo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoServicio implements ITipoServicio {

    private final ITipoRepositorio tipoRepositorio;

    public TipoServicio(ITipoRepositorio tipoRepositorio) {
        this.tipoRepositorio = tipoRepositorio;
    }

    @Override
    public List<Tipo> listar() {
        return tipoRepositorio.listar();
    }

    @Override
    public Tipo obtener(long id) {
        return tipoRepositorio.obtener(id);
    }
}