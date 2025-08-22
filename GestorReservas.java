package Ejercicioindv;

public class GestorReservas {
    // Capacidad fija de arreglos
    private static final int MAX_CANCHAS = 50;
    private static final int MAX_RESERVAS = 500;
    private static final int MAX_ESPERA = 500;

    private Cancha[] canchas = new Cancha[MAX_CANCHAS];
    private Reserva[] reservas = new Reserva[MAX_RESERVAS];
    private int[] listaEspera = new int[MAX_ESPERA];

    private int countCanchas = 0;
    private int countReservas = 0;
    private int countEspera = 0;
    private int siguienteId = 1;

    // --------- Altas ---------
    public boolean agregarCancha(Cancha c) {
        if (countCanchas >= MAX_CANCHAS) return false;
        for (int i = 0; i < countCanchas; i++) {
            if (canchas[i].getNumero() == c.getNumero()) return false;
        }
        canchas[countCanchas++] = c;
        return true;
    }

    public int crearSolicitud(String responsable, String nombreEvt, String tipoEvt,
                              String fecha, String hi, String hf,
                              int jugadores, double deposito) {
        if (countReservas >= MAX_RESERVAS) return -1;
        Reserva r = new Reserva(siguienteId++, responsable, nombreEvt, tipoEvt, fecha, hi, hf, jugadores, deposito);
        reservas[countReservas++] = r;
        return r.getId();
    }

    // --------- Asignación / Cancelación ---------
    public boolean asignarCancha(int idReserva) {
        Reserva r = buscarReserva(idReserva);
        if (r == null || "CANCELADA".equals(r.getEstado())) return false;

        for (int i = 0; i < countCanchas; i++) {
            Cancha c = canchas[i];
            if (validarReglas(r, c) && !hayTraslape(r, c)) {
                r.setNumeroCancha(c.getNumero());
                r.setEstado("RESERVADA");
                return true;
            }
        }

        encolarEspera(r.getId());
        r.setEstado("EN_ESPERA");
        r.setNumeroCancha(-1);
        return false;
    }

    public boolean cancelarReserva(int idReserva) {
        Reserva r = buscarReserva(idReserva);
        if (r == null || "CANCELADA".equals(r.getEstado())) return false;
        r.setEstado("CANCELADA");
        r.setNumeroCancha(-1);
        reasignarDesdeEspera();
        return true;
    }

    public void reasignarDesdeEspera() {
        int n = countEspera;
        for (int k = 0; k < n; k++) {
            int id = desencolarEspera();
            if (id == -1) break;
            Reserva r = buscarReserva(id);
            if (r == null || "CANCELADA".equals(r.getEstado())) continue;

            boolean asignada = false;
            for (int i = 0; i < countCanchas; i++) {
                Cancha c = canchas[i];
                if (validarReglas(r, c) && !hayTraslape(r, c)) {
                    r.setNumeroCancha(c.getNumero());
                    r.setEstado("RESERVADA");
                    asignada = true;
                    break;
                }
            }
            if (!asignada) encolarEspera(r.getId());
        }
    }

    // --------- Consultas ---------
    public Reserva[] reservasPorCancha(int numeroCancha) {
        int cnt = 0;
        for (int i = 0; i < countReservas; i++) {
            if (reservas[i].getNumeroCancha() == numeroCancha) cnt++;
        }

        Reserva[] out = new Reserva[cnt];
        int j = 0;
        for (int i = 0; i < countReservas; i++) {
            if (reservas[i].getNumeroCancha() == numeroCancha) out[j++] = reservas[i];
        }
        return out;
    }

    public int[] obtenerListaEspera() {
        int[] copy = new int[countEspera];
        for (int i = 0; i < countEspera; i++) copy[i] = listaEspera[i];
        return copy;
    }

    // --------- Validaciones ---------
    public boolean validarReglas(Reserva r, Cancha c) {
        if (r.getJugadores() > c.getCapacidad()) return false;
        if (r.getDepositoPagado() < 0.5 * c.getCostoHora()) return false;
        int hi = hhmm(r.getHoraInicio());
        int hf = hhmm(r.getHoraFin());
        if (hi < 800 || hf > 2200 || hi >= hf) return false;
        return true;
    }

    public boolean hayTraslape(Reserva r, Cancha c) {
        for (int i = 0; i < countReservas; i++) {
            Reserva x = reservas[i];
            if (!"RESERVADA".equals(x.getEstado())) continue;
            if (x.getNumeroCancha() != c.getNumero()) continue;
            if (!x.getFecha().equals(r.getFecha())) continue;

            int a1 = hhmm(x.getHoraInicio()), a2 = hhmm(x.getHoraFin());
            int b1 = hhmm(r.getHoraInicio()), b2 = hhmm(r.getHoraFin());
            if (a1 < b2 && b1 < a2) return true;
        }
        return false;
    }

    // --------- Utilidades privadas ---------
    private Reserva buscarReserva(int id) {
        for (int i = 0; i < countReservas; i++) {
            if (reservas[i].getId() == id) return reservas[i];
        }
        return null;
    }

    private void encolarEspera(int id) {
        for (int i = 0; i < countEspera; i++) if (listaEspera[i] == id) return;
        if (countEspera < MAX_ESPERA) listaEspera[countEspera++] = id;
    }

    private int desencolarEspera() {
        if (countEspera == 0) return -1;
        int id = listaEspera[0];
        for (int i = 1; i < countEspera; i++) listaEspera[i - 1] = listaEspera[i];
        countEspera--;
        return id;
    }

    private int hhmm(String hhmm) {
        String s = hhmm.replace(":", "");
        return Integer.parseInt(s);
    }
}
