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
public class Tablero {
    private ArrayList<Casilla> casillas;

    public Tablero() {
        casillas = new ArrayList<>();
        inicializarCasillas();
    }

    private void inicializarCasillas() {
        // Casilla de salida
        casillas.add(new Salida("Salida", 200)); // 

        // Primer grupo de propiedades y otras casillas
        casillas.add(new Propiedad("Avenida Mediterráneo", 60));
        casillas.add(new CajaComunidad("Caja de comunidad"));
        casillas.add(new Propiedad("Avenida Báltica", 60));
        casillas.add(new Impuestos("Impuesto sobre ingresos", 200));
        casillas.add(new Estacion("Estación de Ferrocarril de Reading", 200));
        casillas.add(new Propiedad("Avenida Oriental", 100));
        casillas.add(new Suerte("Suerte"));
        casillas.add(new Propiedad("Avenida Vermont", 100));
        casillas.add(new Propiedad("Avenida Connecticut", 120));

        // Cárcel
        casillas.add(new Carcel("Cárcel/Visita", 0));

        // Segundo grupo de propiedades
        casillas.add(new Propiedad("Plaza San Carlos", 140));
        casillas.add(new ServicioPublico("Compañía Eléctrica", 150));
        casillas.add(new Propiedad("Avenida de los Estados", 140));
        casillas.add(new Propiedad("Avenida Virginia", 160));
        casillas.add(new Estacion("Estación de Ferrocarril de Pensilvania", 200));
        casillas.add(new Propiedad("Plaza St. James", 180));
        casillas.add(new CajaComunidad("Caja de comunidad"));
        casillas.add(new Propiedad("Avenida Tennessee", 180));
        casillas.add(new Propiedad("Avenida Nueva York", 200));

        // Estacionamiento libre
        casillas.add(new Libre("Estacionamiento libre"));

        // Tercer grupo de propiedades
        casillas.add(new Propiedad("Avenida Kentucky", 220));
        casillas.add(new Suerte("Suerte"));
        casillas.add(new Propiedad("Avenida Indiana", 220));
        casillas.add(new Propiedad("Avenida Illinois", 240));
        casillas.add(new Estacion("Estación de Ferrocarril de B. & O.", 200));
        casillas.add(new Propiedad("Avenida Atlántico", 260));
        casillas.add(new Propiedad("Avenida Ventnor", 260));
        casillas.add(new ServicioPublico("Compañía de Agua", 150));
        casillas.add(new Propiedad("Jardines Marvin", 280));

        // Vamos a la cárcel
        casillas.add(new Carcel("Ve a la cárcel", -1)); // -1 como bandera para enviar al jugador a la cárcel

        // Cuarto grupo de propiedades
        casillas.add(new Propiedad("Avenida Pacífico", 300));
        casillas.add(new Propiedad("Avenida Carolina del Norte", 300));
        casillas.add(new CajaComunidad("Caja de comunidad"));
        casillas.add(new Propiedad("Avenida Pensilvania", 320));
        casillas.add(new Estacion("Estación de Ferrocarril de Short Line", 200));
        casillas.add(new Suerte("Suerte"));
        casillas.add(new Propiedad("Plaza Park", 350));
        casillas.add(new Impuestos("Impuesto de lujo", 100));
        casillas.add(new Propiedad("El Paseo", 400));
    }
    
    public ArrayList<Casilla> getCasillas() {
        return casillas;
    }

    public Casilla getCasilla(int posicion) {
        return casillas.get(posicion % casillas.size());
    }
}
