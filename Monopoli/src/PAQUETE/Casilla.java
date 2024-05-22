/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PAQUETE;

import javax.swing.JOptionPane;

/**
 *
 * @author juang
 */
public abstract class Casilla {
    protected String nombre;
    public MonopolyGUI gui;

    public Casilla(String nombre) {
        this.nombre = nombre;
        this.gui = gui;
    }

    public abstract void ejecutarAccion(Jugador jugador, Juego juego);

    public String getNombre() {
        return nombre;
    }
}

class Propiedad extends Casilla {
    private int costo;
    private Jugador propietario;

    public Propiedad(String nombre, int costo) {
        super(nombre);
        this.costo = costo;
        this.propietario = null;
    }

    @Override
    public void ejecutarAccion(Jugador jugador, Juego juego) {
        if (propietario == null) {
            int respuesta = JOptionPane.showConfirmDialog(null, 
                "¿Deseas comprar " + getNombre() + " por $" + costo + "?", 
                "Compra de propiedad", 
                JOptionPane.YES_NO_OPTION);

            if (respuesta == JOptionPane.YES_OPTION) {
                if (jugador.getDinero() >= costo) {
                    jugador.pagar(costo);
                    propietario = jugador;
                    jugador.agregarPropiedad(this);  // Asegurémonos de que esta línea está en tu código
                    JOptionPane.showMessageDialog(null, "Has comprado " + getNombre() + ".");
                } else {
                    JOptionPane.showMessageDialog(null, "No tienes suficiente dinero para comprar " + getNombre() + ".");
                }
            }
        } else if (!propietario.equals(jugador)) {
            int alquiler = calcularAlquiler();
            jugador.pagar(alquiler);
            propietario.recibirDinero(alquiler);
            JOptionPane.showMessageDialog(null, "Has pagado $" + alquiler + " de alquiler a " + propietario.getNombre() + ".");
        }
    }

    public int getCosto() {
        return costo;
    }

    public Jugador getPropietario() {
        return propietario;
    }

    public void setPropietario(Jugador propietario) {
        this.propietario = propietario;
    }

    private int calcularAlquiler() {
        
        return (int) (costo * 0.2); 
    }
}

class Impuestos extends Casilla {
    private int monto;

    public Impuestos(String nombre, int monto) {
        super(nombre);
        this.monto = monto;
    }

    @Override
    public void ejecutarAccion(Jugador jugador, Juego juego) {
        
        String mensaje= jugador.getNombre() + " paga impuestos de " + monto;
        JOptionPane.showConfirmDialog(gui, mensaje);
        jugador.pagar(monto);
    }
}

class Carcel extends Casilla {
    public Carcel(String nombre, int posicion) {
        super(nombre);
    }

    @Override
    public void ejecutarAccion(Jugador jugador, Juego juego) {
        
        String mensaje=jugador.getNombre() + " está de visita en la cárcel";
        JOptionPane.showConfirmDialog(gui, mensaje);
    }
}

class Salida extends Casilla {
    private int bonificacion;

    public Salida(String nombre, int bonificacion) {
        super(nombre);
        this.bonificacion = bonificacion;
    }

    @Override
    public void ejecutarAccion(Jugador jugador, Juego juego) {
        jugador.recibirDinero(bonificacion);
       
        String mensaje=jugador.getNombre() + " pasa por " + nombre + " y recibe " + bonificacion;
        JOptionPane.showMessageDialog(gui, mensaje);
    
    }
}

class Estacion extends Casilla {
    private int costo;

    public Estacion(String nombre, int costo) {
        super(nombre);
        this.costo = costo;
    }

    @Override
    public void ejecutarAccion(Jugador jugador, Juego juego) {
        String mensaje=jugador.getNombre() + " ha aterrizado en " + nombre;
        JOptionPane.showMessageDialog(gui, mensaje);
        
    }
}

class ServicioPublico extends Casilla {
    private int costo;

    public ServicioPublico(String nombre, int costo) {
        super(nombre);
        this.costo = costo;
    }

    @Override
    public void ejecutarAccion(Jugador jugador, Juego juego) {
        String mensaje=jugador.getNombre() + " ha aterrizado en " + nombre;
        JOptionPane.showMessageDialog(gui, mensaje);
        
    }
}

class CajaComunidad extends Casilla {
    public CajaComunidad(String nombre) {
        super(nombre);
    }

    @Override
    public void ejecutarAccion(Jugador jugador, Juego juego) {
        Tarjeta tarjeta = juego.sacarTarjetaCajaComunidad();
        tarjeta.ejecutar(jugador, juego);
        String mensaje = "Caja de Comunidad: " + tarjeta.getDescripcion();
        JOptionPane.showMessageDialog(gui, mensaje);
        
    }
}

class Suerte extends Casilla {
    public Suerte(String nombre) {
        super(nombre);
    }

    @Override
    public void ejecutarAccion(Jugador jugador, Juego juego) {
        Tarjeta tarjeta = juego.sacarTarjetaSuerte();
        tarjeta.ejecutar(jugador, juego);
        String mensaje= "Suerte: " + tarjeta.getDescripcion();
        JOptionPane.showMessageDialog(gui, mensaje);
    }
}

class Libre extends Casilla {
    public Libre(String nombre) {
        super(nombre);
    }

    @Override
    public void ejecutarAccion(Jugador jugador, Juego juego) {
        String mensaje = jugador.getNombre() + " está en el Estacionamiento Libre. No pasa nada.";
        JOptionPane.showMessageDialog(gui, mensaje);
    }
}
