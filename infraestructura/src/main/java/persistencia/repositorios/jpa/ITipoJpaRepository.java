package persistencia.repositorios.jpa;

import persistencia.entidades.TipoEntidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ITipoJpaRepository extends JpaRepository<TipoEntidad, Long> {
    Optional<TipoEntidad> findByTipo(String tipo);
}