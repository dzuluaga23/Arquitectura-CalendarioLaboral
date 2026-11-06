package persistencia.repositorios;

import repositorios.ICalendarioRepositorio;
import entidades.Calendario;
import persistencia.entidades.CalendarioEntidad;
import persistencia.mapeadores.CalendarioMapeador;
import persistencia.repositorios.jpa.ICalendarioJpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CalendarioRepositorio implements ICalendarioRepositorio {

    private final ICalendarioJpaRepository calendarioJpaRepository;

    public CalendarioRepositorio(ICalendarioJpaRepository calendarioJpaRepository) {
        this.calendarioJpaRepository = calendarioJpaRepository;
    }

    @Override
    public List<Calendario> listar() {
        return calendarioJpaRepository.findAll()
                .stream()
                .map(CalendarioMapeador::aDominio)
                .collect(Collectors.toList());
    }

    @Override
    public Calendario obtener(long id) {
        Optional<CalendarioEntidad> entidad = calendarioJpaRepository.findById(id);
        return entidad.map(CalendarioMapeador::aDominio).orElse(null);
    }

    @Override
    public Calendario obtenerPorFecha(Date fecha) {
        Optional<CalendarioEntidad> entidad = calendarioJpaRepository.findByFecha(fecha);
        return entidad.map(CalendarioMapeador::aDominio).orElse(null);
    }
}