package Ejercicioindv;

public class Controlador {
    private final GestorReservas gestor;

    public Controlador() {
        this.gestor = new GestorReservas(); // aquí se instancian los objetos del modelo/servicio
    }

    // --- Canchas ---
    public boolean agregarCancha(int numero, String tipo, int capacidad, double costoHora) {
        return gestor.agregarCancha(new Cancha(numero, tipo, capacidad, costoHora));
    }

    // --- Reservas / Solicitudes ---
    public int registrarSolicitud(String responsable, String nombreEvt, String tipoEvt,
                                  String fecha, String hi, String hf, int jugadores, double deposito) {
        return gestor.crearSolicitud(responsable, nombreEvt, tipoEvt, fecha, hi, hf, jugadores, deposito);
    }

    public boolean intentarAsignar(int idReserva) {
        return gestor.asignarCancha(idReserva);
    }

    public boolean cancelar(int idReserva) {
        return gestor.cancelarReserva(idReserva);
    }

    // --- Consultas ---
    public Reserva[] reservasPorCancha(int numeroCancha) {
        return gestor.reservasPorCancha(numeroCancha);
    }

    public int[] obtenerListaEspera() {
        return gestor.obtenerListaEspera();
    }
}
