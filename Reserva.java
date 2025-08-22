package Ejercicioindv;

public class Reserva {
    private int id;
    private String responsable;
    private String nombreEvento;
    private String tipoEvento;
    private String fecha;       // "YYYY-MM-DD"
    private String horaInicio;  // "HH:MM"
    private String horaFin;     // "HH:MM"
    private int jugadores;
    private double depositoPagado;
    private String estado;      // "EN_ESPERA" | "RESERVADA" | "CANCELADA"
    private int numeroCancha;   // -1 si no asignada

    public Reserva(int id, String responsable, String nombreEvento, String tipoEvento,
                   String fecha, String horaInicio, String horaFin,
                   int jugadores, double depositoPagado) {
        this.id = id;
        this.responsable = responsable;
        this.nombreEvento = nombreEvento;
        this.tipoEvento = tipoEvento;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.jugadores = jugadores;
        this.depositoPagado = depositoPagado;
        this.estado = "EN_ESPERA";
        this.numeroCancha = -1;
    }

    public int getId() { return id; }
    public String getResponsable() { return responsable; }
    public String getNombreEvento() { return nombreEvento; }
    public String getTipoEvento() { return tipoEvento; }
    public String getFecha() { return fecha; }
    public String getHoraInicio() { return horaInicio; }
    public String getHoraFin() { return horaFin; }
    public int getJugadores() { return jugadores; }
    public double getDepositoPagado() { return depositoPagado; }
    public String getEstado() { return estado; }
    public int getNumeroCancha() { return numeroCancha; }

    public void setEstado(String estado) { this.estado = estado; }
    public void setNumeroCancha(int numeroCancha) { this.numeroCancha = numeroCancha; }
}

