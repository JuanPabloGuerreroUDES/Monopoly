package PAQUETE;



import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author juang
 */
public class Juego {
    private Tablero tablero;
    private ArrayList<Jugador> jugadores;
    private ArrayList<Tarjeta> tarjetasCajaComunidad;
    private ArrayList<Tarjeta> tarjetasSuerte;
    private int jugadorActual;
    private boolean juegoTerminado;
    private Scanner scanner;
    private MonopolyGUI gui;

    public Juego(List<String> nombresJugadores, MonopolyGUI gui) {
        this.gui = gui;
        this.tablero = new Tablero();
        this.jugadores = new ArrayList<>();
        inicializarJugadores(nombresJugadores);
        this.jugadorActual = 0;
        this.juegoTerminado = false;
        this.tarjetasCajaComunidad = new ArrayList<>();
        this.tarjetasSuerte = new ArrayList<>();
        inicializarTarjetas();
        
        
        this.scanner = new Scanner(System.in);
    }
    
    private void inicializarTarjetas() {
        // Ejemplos más representativos de tarjetas Caja de Comunidad
    tarjetasCajaComunidad.add(new DineroTarjeta("El banco te paga un dividendo de $50.", 50));
    tarjetasCajaComunidad.add(new DineroTarjeta("Devolución de impuestos. Recibe $20.", 20));
    tarjetasCajaComunidad.add(new MoverTarjeta("Ve directo a la cárcel. No pases por la salida.", 10)); // Este debería mover al jugador directamente a la cárcel.

    // Ejemplos más representativos de tarjetas Suerte
    tarjetasSuerte.add(new DineroTarjeta("Has sido multado por exceso de velocidad. Paga $15.", -15));
    tarjetasSuerte.add(new MoverTarjeta("Avanza hasta la 'Salida'.", 0)); // Esto moverá al jugador a la casilla de salida.
    tarjetasSuerte.add(new DineroTarjeta("Tu préstamo de construcción madura. Recibe $150.", 150));

        // Barajar las tarjetas
        Collections.shuffle(tarjetasCajaComunidad);
        Collections.shuffle(tarjetasSuerte);
    }

    public Tarjeta sacarTarjetaCajaComunidad() {
        Tarjeta tarjeta = tarjetasCajaComunidad.remove(0);
        tarjetasCajaComunidad.add(tarjeta);  // Vuelve a colocar la tarjeta al final del mazo
        return tarjeta;
    }

    public Tarjeta sacarTarjetaSuerte() {
        Tarjeta tarjeta = tarjetasSuerte.remove(0);
        tarjetasSuerte.add(tarjeta);  // Vuelve a colocar la tarjeta al final del mazo
        return tarjeta;
    }

    private void inicializarJugadores(List<String> nombresJugadores) {
        for (String nombre : nombresJugadores) {
            this.jugadores.add(new Jugador(nombre, gui));
        }
    }
    
    private void log(String message) {
        gui.log(message);  // Método para loggear mensajes a través del GUI
    }
    
    

    public void iniciarJuego() {
        log("¡Bienvenidos al juego de Monopoly!");
        while (!juegoTerminado) {
            jugarTurno();
        }
    }

    public void jugarTurno() {
    Jugador jugador = jugadores.get(jugadorActual);
    gui.log(jugador.getNombre() + ", es tu turno.");

    int dado = lanzarDados();
    gui.log("Has lanzado un " + dado + ".");

    jugador.moverFicha(dado);
    int posicion = jugador.getPosicion();
    Casilla casillaActual = tablero.getCasilla(posicion);
    
    gui.log("Has aterrizado en " + casillaActual.getNombre() + ".");
    
    casillaActual.ejecutarAccion(jugador, this);

    if (jugador.getDinero() <= 0) {
        gui.log(jugador.getNombre() + " se ha quedado sin dinero y ha perdido.");
        finalizarJuego(jugador);
    }

    jugadorActual = (jugadorActual + 1) % jugadores.size();
}

    private int lanzarDados() {
        return (int) (Math.random() * 6) + 1; 
    }

    public void finalizarJuego(Jugador jugadorPerdedor) {
        
        log("El juego ha terminado. " + jugadorPerdedor.getNombre() + " ha perdido.");
        jugadores.remove(jugadorPerdedor);
        if (jugadores.size() == 1) {
            
            log("El ganador es " + jugadores.get(0).getNombre() + ".");
        }
        juegoTerminado = true;
    }

    public boolean isJuegoTerminado() {
        return juegoTerminado;
    }

    public void setJuegoTerminado(boolean juegoTerminado) {
        this.juegoTerminado = juegoTerminado;
    }
    
    public Tablero getTablero() {
        return tablero;
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(ArrayList<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public int getJugadorActual() {
        return jugadorActual;
    }

    public void setJugadorActual(int jugadorActual) {
        this.jugadorActual = jugadorActual;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }
    
    
}
