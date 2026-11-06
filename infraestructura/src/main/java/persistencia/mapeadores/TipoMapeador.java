package persistencia.mapeadores;

import entidades.Tipo;
import persistencia.entidades.TipoEntidad;

public class TipoMapeador {

    public static Tipo aDominio(TipoEntidad entity) {
        if (entity == null) return null;
        return new Tipo(entity.getId(), entity.getTipo());
    }

    public static TipoEntidad aEntidad(Tipo dominio) {
        if (dominio == null) return null;
        return new TipoEntidad((int) dominio.getId(), dominio.getTipo());
    }
}