package persistencia.mapeadores;

import entidades.Calendario;
import entidades.Tipo;
import persistencia.entidades.CalendarioEntidad;

public class CalendarioMapeador {

    public static Calendario aDominio(CalendarioEntidad entity) {
        if (entity == null) return null;

        Tipo tipoDominio = TipoMapeador.aDominio(entity.getTipo());

        return new Calendario(
                entity.getId(),
                entity.getFecha(),
                tipoDominio,
                entity.getDescripcion()
        );
    }

    public static CalendarioEntidad aEntidad(Calendario dominio) {
        if (dominio == null) return null;

        return new CalendarioEntidad(
                (int) dominio.getId(), // Casteo explícito a int
                dominio.getFecha(),
                TipoMapeador.aEntidad(dominio.getTipo()),
                dominio.getDescripcion()
        );
    }
}