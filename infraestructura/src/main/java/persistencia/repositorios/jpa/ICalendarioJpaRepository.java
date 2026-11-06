package persistencia.repositorios.jpa;

import persistencia.entidades.CalendarioEntidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface ICalendarioJpaRepository extends JpaRepository<CalendarioEntidad, Long> {
    Optional<CalendarioEntidad> findByFecha(Date fecha);
}