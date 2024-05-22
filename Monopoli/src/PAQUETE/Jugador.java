/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PAQUETE;

import java.util.ArrayList;

/**
 *
 * @author juang
 */
public class Jugador {
    private String nombre;
    private int posicion;
    private int dinero;
    private ArrayList<Propiedad> propiedades;
    private boolean enCarcel;
    private int turnosEnCarcel;
    private MonopolyGUI gui;
    

    public Jugador(String nombre, MonopolyGUI gui) {
        this.gui = gui;
        this.nombre = nombre;
        this.posicion = 0;  // Todos los jugadores comienzan en la casilla de salida
        this.dinero = 1500; // Monto inicial de dinero; esto puede ajustarse según las reglas personalizadas
        this.propiedades = new ArrayList<>();
    }

    public void moverFicha(int cantidadCasillas) {
        this.posicion = (this.posicion + cantidadCasillas) % 40; // Asegura que la posición siempre sea válida en el tablero de 40 casillas
        gui.log(this.nombre + " se mueve a la posición " + this.posicion);
        
    }

    public void pagar(int monto) {
        this.dinero -= monto;
        gui.log(this.nombre + " ha pagado " + monto + " y ahora tiene " + this.dinero);
        
    }

    public void recibirDinero(int monto) {
        this.dinero += monto;
        
        gui.log(this.nombre + " ha pagado " + monto + " y ahora tiene " + this.dinero);
    }

    public void comprarPropiedad(Propiedad propiedad) {
        if (dinero >= propiedad.getCosto()) {
            this.dinero -= propiedad.getCosto();
            this.propiedades.add(propiedad);
            propiedad.setPropietario(this);
            System.out.println(this.nombre + " ha comprado " + propiedad.getNombre());
        } else {
            System.out.println(this.nombre + " no tiene suficiente dinero para comprar " + propiedad.getNombre());
        }
    }
    
    public void agregarPropiedad(Propiedad propiedad) {
        propiedades.add(propiedad);
    }
    
    public void enviarACarcel() {
        this.enCarcel = true;
        this.turnosEnCarcel = 0;
        this.posicion = 10; // Posición de la cárcel en el tablero
        System.out.println(nombre + " ha sido enviado a la cárcel.");
    }

    public void intentarSalirDeCarcel() {
        turnosEnCarcel++;
        if (turnosEnCarcel >= 3) {
            pagar(50); 
            enCarcel = false;
            System.out.println(nombre + " ha pagado para salir de la cárcel.");
        }
    }

    public boolean estaEnCarcel() {
        return enCarcel;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public int getPosicion() {
        return posicion;
    }

    public int getDinero() {
        return dinero;
    }

    public ArrayList<Propiedad> getPropiedades() {
        return propiedades;
    }

    public void setDinero(int dinero) {
        this.dinero = dinero;
    }
}
