package persistencia.repositorios;

import repositorios.ITipoRepositorio;
import entidades.Tipo;
import persistencia.entidades.TipoEntidad;
import persistencia.mapeadores.TipoMapeador;
import persistencia.repositorios.jpa.ITipoJpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class TipoRepositorio implements ITipoRepositorio {

    private final ITipoJpaRepository tipoJpaRepository;

    public TipoRepositorio(ITipoJpaRepository tipoJpaRepository) {
        this.tipoJpaRepository = tipoJpaRepository;
    }

    @Override
    public List<Tipo> listar() {
        return tipoJpaRepository.findAll()
                .stream()
                .map(TipoMapeador::aDominio)
                .collect(Collectors.toList());
    }

    @Override
    public Tipo obtener(long id) {
        Optional<TipoEntidad> entidad = tipoJpaRepository.findById(id);
        return entidad.map(TipoMapeador::aDominio).orElse(null);
    }
}