package persistencia.repositorios.jpa;

import persistencia.entidades.TipoEntidad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITipoJpaRepository extends JpaRepository<TipoEntidad, Long> {
}