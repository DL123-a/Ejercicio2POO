package Ejercicioindv;

import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Controlador ctrl = new Controlador(); // Main no gestiona lógica: solo entrada/salida
        System.out.println("=== Complejo Deportivo - Reservas de Canchas ===");

        // Ingreso de 4 canchas iniciales (I/O)
        System.out.println("Ingrese 4 canchas iniciales:");
        for (int i = 0; i < 4; i++) {
            System.out.print("Numero: ");
            int num = Integer.parseInt(sc.nextLine());
            System.out.print("Tipo (Futbol/Baloncesto/Tenis): ");
            String tipo = sc.nextLine();
            System.out.print("Capacidad: ");
            int cap = Integer.parseInt(sc.nextLine());
            System.out.print("Costo por hora: ");
            double costo = Double.parseDouble(sc.nextLine());

            boolean ok = ctrl.agregarCancha(num, tipo, cap, costo);
            System.out.println(ok ? "Cancha agregada." : "No se pudo agregar (duplicada o limite).");
        }

        int op;
        do {
            System.out.println("\nMENU");
            System.out.println("1) Registrar solicitud de reserva");
            System.out.println("2) Intentar asignar cancha a una solicitud");
            System.out.println("3) Cancelar reserva");
            System.out.println("4) Ver reservas por cancha");
            System.out.println("5) Ver lista de espera");
            System.out.println("0) Salir");
            System.out.print("Opcion: ");
            op = Integer.parseInt(sc.nextLine());

            switch (op) {
                case 1 -> registrarSolicitud(ctrl);
                case 2 -> asignar(ctrl);
                case 3 -> cancelar(ctrl);
                case 4 -> verPorCancha(ctrl);
                case 5 -> verEspera(ctrl);
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opcion invalida.");
            }
        } while (op != 0);
    }

    // ---- Solo métodos de impresión/lectura (View) ----
    private static void registrarSolicitud(Controlador ctrl) {
        System.out.print("Responsable: ");
        String resp = sc.nextLine();
        System.out.print("Nombre evento: ");
        String nom = sc.nextLine();
        System.out.print("Tipo evento: ");
        String tipoEvt = sc.nextLine();
        System.out.print("Fecha (AAAA-MM-DD): ");
        String fecha = sc.nextLine();
        System.out.print("Hora inicio (HH:MM): ");
        String hi = sc.nextLine();
        System.out.print("Hora fin (HH:MM): ");
        String hf = sc.nextLine();
        System.out.print("Jugadores: ");
        int jugadores = Integer.parseInt(sc.nextLine());
        System.out.print("Deposito pagado: ");
        double dep = Double.parseDouble(sc.nextLine());

        int id = ctrl.registrarSolicitud(resp, nom, tipoEvt, fecha, hi, hf, jugadores, dep);
        if (id == -1) {
            System.out.println("No se pudo crear la solicitud (limite).");
            return;
        }
        System.out.println("Solicitud creada con id = " + id);
        boolean asignada = ctrl.intentarAsignar(id);
        System.out.println(asignada ? "Asignada inmediatamente."
                                    : "No hay cancha disponible o no cumple reglas. Queda EN ESPERA.");
    }

    private static void asignar(Controlador ctrl) {
        System.out.print("ID de solicitud: ");
        int id = Integer.parseInt(sc.nextLine());
        boolean ok = ctrl.intentarAsignar(id);
        System.out.println(ok ? "Asignacion realizada."
                              : "No fue posible asignar (reglas/conflicto/o inexistente).");
    }

    private static void cancelar(Controlador ctrl) {
        System.out.print("ID de reserva a cancelar: ");
        int id = Integer.parseInt(sc.nextLine());
        boolean ok = ctrl.cancelar(id);
        System.out.println(ok ? "Reserva cancelada. Se intento reasignar la lista de espera."
                              : "No fue posible cancelar (ID invalido o ya cancelada).");
    }

    private static void verPorCancha(Controlador ctrl) {
        System.out.print("Numero de cancha: ");
        int num = Integer.parseInt(sc.nextLine());
        Reserva[] arr = ctrl.reservasPorCancha(num);
        if (arr.length == 0) {
            System.out.println("No hay reservas en esta cancha.");
            return;
        }
        System.out.println("Reservas en cancha " + num + ":");
        for (Reserva r : arr) {
            System.out.println("ID=" + r.getId() + " | " + r.getFecha() + " " +
                r.getHoraInicio() + "-" + r.getHoraFin() + " | Evento: " +
                r.getNombreEvento() + " | Estado=" + r.getEstado());
        }
    }

    private static void verEspera(Controlador ctrl) {
        int[] espera = ctrl.obtenerListaEspera();
        if (espera.length == 0) {
            System.out.println("No hay solicitudes en espera.");
            return;
        }
        System.out.println("Lista de espera (IDs):");
        for (int id : espera) System.out.print(id + " ");
        System.out.println();
    }
}