package clientes.dto;

/**
 * DTO que refleja el formato real de respuesta de la API Festivos (Node.js/Express):
 * GET /api/festivos/obtener/{anio} -> [{ "festivo": "Año nuevo", "fecha": "2026-01-01" }]
 */
public class FestivoDto {
    private String festivo;
    private String fecha; // Formato "YYYY-MM-DD"

    public FestivoDto() {
    }

    public String getFestivo() { return festivo; }
    public void setFestivo(String festivo) { this.festivo = festivo; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    @Override
    public String toString() {
        return "FestivoDto{festivo='" + festivo + "', fecha='" + fecha + "'}";
    }
}
