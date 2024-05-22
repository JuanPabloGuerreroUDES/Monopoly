/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PAQUETE;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 *
 * @author juang
 */
public class MonopolyGUI extends JFrame {
    private Juego juego;
    private JPanel panelTablero;
    private JPanel panelInfo;
    private JLabel labelInfo;
    private ArrayList<JLabel> labelsJugadores;
    
    private JTextArea areaLog;
    public JLabel[][] casillasLabels = new JLabel[11][11];

    public MonopolyGUI(List<String> nombresJugadores) {
        setTitle("Monopoly en Java");
        setSize(1300, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crear el juego con los nombres de los jugadores
        juego = new Juego(nombresJugadores, this);  // Aquí pasamos 'this' a Juego

        labelsJugadores = new ArrayList<>();
        
        setupTablero();
        setupPanelInfo();
        setupLogArea();

        setVisible(true);
    }

   
    private void setupTablero() {
    panelTablero = new JPanel();
    panelTablero.setLayout(new GridLayout(11, 11));  // Ajustamos a 11x11 para el tablero completo

    // Inicializa casillasLabels para almacenar las etiquetas de las casillas
    casillasLabels = new JLabel[11][11]; 

    // Llenar el array con JLabels vacíos para manejar las posiciones no usadas
    for (int i = 0; i < 11; i++) {
        for (int j = 0; j < 11; j++) {
            casillasLabels[i][j] = new JLabel();
            casillasLabels[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
            casillasLabels[i][j].setHorizontalAlignment(JLabel.CENTER);
            casillasLabels[i][j].setVerticalAlignment(JLabel.CENTER);
            casillasLabels[i][j].setOpaque(true);
            panelTablero.add(casillasLabels[i][j]);
        }
    }

    // Posicionar las casillas reales
    List<Casilla> casillasDelJuego = juego.getTablero().getCasillas();
    if (casillasDelJuego.size() != 40) {
        throw new IllegalStateException("Debe haber exactamente 40 casillas definidas.");
    }

    // Ubicación de las casillas en el tablero:
    for (int i = 0; i < casillasDelJuego.size(); i++) {
        int[] coords = convertirPosicionACoordenadas(i);
        JLabel labelCasilla = casillasLabels[coords[0]][coords[1]];
        labelCasilla.setText(casillasDelJuego.get(i).getNombre());
        labelCasilla.setBackground(determinarColorCasilla(casillasDelJuego.get(i)));
    }

    add(panelTablero, BorderLayout.CENTER);
}

    private void setupPanelInfo() {
        JPanel panelInfo = new JPanel(new BorderLayout());
        JPanel panelJugadores = new JPanel(new GridLayout(juego.getJugadores().size(), 1)); // Un jugador por fila
        
        // Añadir labels para cada jugador con su nombre
        for (Jugador jugador : juego.getJugadores()) {
        JLabel labelJugador = new JLabel(formatoInfoJugador(jugador));
        labelJugador.setBorder(BorderFactory.createLineBorder(Color.black));
        panelJugadores.add(labelJugador);
        labelsJugadores.add(labelJugador); // Guarda la referencia al JLabel
    }

        panelInfo.add(new JScrollPane(panelJugadores), BorderLayout.CENTER); // Permite scroll si hay muchos jugadores

    JButton botonLanzarDados = new JButton("Lanzar Dados");
    botonLanzarDados.addActionListener(e -> {
        juego.jugarTurno();
        actualizarUI();
    });
    panelInfo.add(botonLanzarDados, BorderLayout.SOUTH);

    add(panelInfo, BorderLayout.EAST);
}
    
    

private String formatoInfoJugador(Jugador jugador) {
    
    return "<html>Jugador: " + jugador.getNombre() +
           "<br/>Dinero: $" + jugador.getDinero() +
           "<br/>Propiedades: " + jugador.getPropiedades().size() + "</html>";
}
    
    private void actualizarUI() {
    SwingUtilities.invokeLater(() -> {
        // Actualización basada en el arreglo de casillasLabels
        for (int i = 0; i < 40; i++) {
            int[] coords = convertirPosicionACoordenadas(i);
            JLabel labelCasilla = casillasLabels[coords[0]][coords[1]];
            Casilla casilla = juego.getTablero().getCasillas().get(i);
            labelCasilla.setText("<html>" + casilla.getNombre() + "<br/></html>");
            labelCasilla.setBackground(determinarColorCasilla(casilla));

            if (jugadorEnCasilla(i)) {
                Jugador jugador = obtenerJugadorEnCasilla(i);
                labelCasilla.setText("<html>" + casilla.getNombre() + "<br/>" + jugador.getNombre() + " aquí</html>");
                labelCasilla.setBackground(Color.CYAN);  // Destaca la casilla del jugador actual
            }
        }

        // Actualiza información de los jugadores
        for (int i = 0; i < juego.getJugadores().size(); i++) {
            Jugador jugador = juego.getJugadores().get(i);
            JLabel labelJugador = labelsJugadores.get(i);
            labelJugador.setText(formatoInfoJugador(jugador));
        }
    });
}

    
    private int[] convertirPosicionACoordenadas(int posicion) {
    // Ajustar las coordenadas para un tablero de 11x11
    if (posicion < 10) {
        // Borde inferior, desde 'Salida' hasta 'Just Visiting'
        return new int[] {10, 10 - posicion};  // fila, columna (moviéndose de derecha a izquierda)
    } else if (posicion < 20) {
        // Borde izquierdo, desde 'Just Visiting' hasta 'Free Parking'
        return new int[] {10 - (posicion - 10), 0};  // fila, columna (moviéndose de abajo hacia arriba)
    } else if (posicion < 30) {
        // Borde superior, desde 'Free Parking' hasta 'Go to Jail'
        return new int[] {0, posicion - 20};  // fila, columna (moviéndose de izquierda a derecha)
    } else {
        // Borde derecho, desde 'Go to Jail' hasta 'Go'
        return new int[] {posicion - 30, 10};  // fila, columna (moviéndose de arriba hacia abajo)
    }
}
    
    private int convertirCoordenadasAIndex(int fila, int columna) {
    // Recorrido antihorario desde 'Go'
    if (fila == 10) {
        // Borde inferior, de izquierda a derecha desde 'Go' hasta 'Just Visiting'
        return columna; // 0 a 9
    } else if (columna == 0) {
        // Borde izquierdo, de abajo hacia arriba desde 'Just Visiting' hasta 'Free Parking'
        return 10 + (10 - fila); // 10 a 19
    } else if (fila == 0) {
        // Borde superior, de izquierda a derecha desde 'Free Parking' hasta 'Go to Jail'
        return 20 + columna; // 20 a 29
    } else if (columna == 10) {
        // Borde derecho, de arriba hacia abajo desde 'Go to Jail' hasta 'Go'
        return 30 + (10 - fila); // 30 a 39
    }
    return -1;  // Coordenada no válida para casillas jugables
}

private int[] posicionACoordenadas(int posicion) {
    if (posicion < 10) {  // Borde inferior
        return new int[] {10, posicion};
    } else if (posicion < 20) {  // Borde derecho
        return new int[] {20 - posicion, 10};
    } else if (posicion < 30) {  // Borde superior
        return new int[] {0, 30 - posicion};
    } else if (posicion < 40) {  // Borde izquierdo
        return new int[] {posicion - 30, 0};
    }
    return null;  // Por si la posición no es válida
}
    
    

private Color determinarColorCasilla(Casilla casilla) {
    if (casilla instanceof Propiedad) {
        return Color.lightGray;
    } else if (casilla instanceof Carcel) {
        return Color.red;
    } else if (casilla instanceof Impuestos) {
        return Color.orange;
    } else if (casilla instanceof Estacion) {
        return Color.blue;
    } else if (casilla instanceof ServicioPublico) {
        return Color.green;
    } else if (casilla instanceof CajaComunidad || casilla instanceof Suerte) {
        return Color.yellow;
    } else if (casilla instanceof Salida) {
        return Color.pink;
    } else if (casilla instanceof Libre) {
        return Color.white;
    } else {
        return Color.gray;
    }
}


    private void actualizarLabelJugador(Jugador jugador, JLabel labelJugador) {
        String texto = "<html>Jugador: " + jugador.getNombre() +
                       "<br/>Dinero: $" + jugador.getDinero() +
                       "<br/>Propiedades: " + jugador.getPropiedades().size() + "</html>";
        labelJugador.setText(texto);
    }
    private boolean jugadorEnCasilla(int index) {
    for (Jugador jugador : juego.getJugadores()) {
        if (jugador.getPosicion() == index) {
            return true;  // Un jugador se encuentra en la casilla indicada
        }
    }
    return false;  // Ningún jugador está en esa casilla
}
    
    private Jugador obtenerJugadorEnCasilla(int index) {
    for (Jugador jugador : juego.getJugadores()) {
        if (jugador.getPosicion() == index) {
            return jugador;  // Retorna el jugador que se encuentra en la casilla indicada
        }
    }
    return null;  // Retorna null si ningún jugador está en esa casilla
}
    
    //txtArea para la informacion del juego
    private void setupLogArea() {
        areaLog = new JTextArea(10, 50);
        areaLog.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaLog);
        add(scrollPane, BorderLayout.SOUTH);
    }
    
    public void log(String message) {
        areaLog.append(message + "\n");
        areaLog.setCaretPosition(areaLog.getDocument().getLength()); // Auto-scroll
    }
    
    public static void main(String[] args) {
        // El main ahora debería estar en otra clase o ajustarse para abrir el formulario inicial
        List<String> nombresJugadores = Arrays.asList("Alice", "Bob", "Charlie", "Diana");
        new MonopolyGUI(nombresJugadores);
    }
}
