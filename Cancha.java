package Ejercicioindv;

public class Cancha {
    private int numero;
    private String tipo; 
    private int capacidad;
    private double costoHora;

    public Cancha(int numero, String tipo, int capacidad, double costoHora) {
        this.numero = numero;
        this.tipo = tipo;
        this.capacidad = capacidad;
        this.costoHora = costoHora;
    }

    public int getNumero() { return numero; }
    public String getTipo() { return tipo; }
    public int getCapacidad() { return capacidad; }
    public double getCostoHora() { return costoHora; }

    public void setNumero(int numero) { this.numero = numero; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }
    public void setCostoHora(double costoHora) { this.costoHora = costoHora; }
}
