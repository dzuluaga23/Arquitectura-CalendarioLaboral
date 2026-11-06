package dtos;

import java.util.Date;

public class CalendarioDto {

    private long id;
    private Date fecha;
    private long idTipo;
    private String nombreTipo;
    private String descripcion;

    public CalendarioDto() {
    }

    public CalendarioDto(long id, Date fecha, long idTipo, String nombreTipo, String descripcion) {
        this.id = id;
        this.fecha = fecha;
        this.idTipo = idTipo;
        this.nombreTipo = nombreTipo;
        this.descripcion = descripcion;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public long getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(long idTipo) {
        this.idTipo = idTipo;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}