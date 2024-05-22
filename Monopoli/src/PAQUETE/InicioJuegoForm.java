/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PAQUETE;

/**
 *
 * @author juang
 */
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class InicioJuegoForm extends JFrame {
    private List<JTextField> camposNombres;
    private JButton botonIniciar;
    private int numJugadores;

    public InicioJuegoForm() {
        super("Configuración de Monopoly");
        pedirNumeroJugadores();
    }

    private void pedirNumeroJugadores() {
        // Crear un diálogo para ingresar el número de jugadores
        String num = JOptionPane.showInputDialog(this, "Ingrese el número de jugadores (2-6):", "Número de Jugadores", JOptionPane.QUESTION_MESSAGE);
        try {
            numJugadores = Integer.parseInt(num);
            if (numJugadores < 2 || numJugadores > 6) {
                JOptionPane.showMessageDialog(this, "Número inválido. Por favor ingrese un número entre 2 y 6.", "Error", JOptionPane.ERROR_MESSAGE);
                pedirNumeroJugadores();
            } else {
                inicializarUI();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Entrada no válida. Por favor ingrese un número.", "Error", JOptionPane.ERROR_MESSAGE);
            pedirNumeroJugadores();
        }
    }

    private void inicializarUI() {
        camposNombres = new ArrayList<>(); 
        setLayout(new GridLayout(numJugadores + 1, 2)); 
        setSize(400, 30 * numJugadores + 70);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        for (int i = 0; i < numJugadores; i++) {
            add(new JLabel("Nombre del Jugador " + (i + 1) + ":"));
            JTextField campoTexto = new JTextField();
            camposNombres.add(campoTexto);
            add(campoTexto);
        }

        botonIniciar = new JButton("Iniciar Juego");
        botonIniciar.addActionListener(e -> iniciarJuego());
        add(botonIniciar);

        setLocationRelativeTo(null); 
        setVisible(true);
    }

    private void iniciarJuego() {
        List<String> nombresJugadores = new ArrayList<>();
        for (JTextField campo : camposNombres) {
            nombresJugadores.add(campo.getText().trim());
        }

       
        dispose();

        // Iniciar la interfaz principal del juego con los nombres 
        MonopolyGUI juegoGUI = new MonopolyGUI(nombresJugadores);
        juegoGUI.setVisible(true);
    }

    public static void main(String[] args) {
        new InicioJuegoForm(); 
    }
}
