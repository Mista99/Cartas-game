import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import javax.swing.JOptionPane;

public class FrmJuego extends JFrame {

    private JButton btnRepartir;
    private JButton btnVerificar;
    private JPanel pnlJugador1;
    private JPanel pnlJugador2;
    private JTabbedPane tpJugadores;

    Jugador jugador1, jugador2;

    //constructor de la clase. el constructor siempre tiene el mismo nombre de la clase y no devuelve nun ningun tipo de valor.
    public FrmJuego() {
        btnRepartir = new JButton();
        btnVerificar = new JButton();
        tpJugadores = new JTabbedPane();
        pnlJugador1 = new JPanel();
        pnlJugador2 = new JPanel();

        setSize(600, 300);
        setTitle("Juego de Cartas");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        pnlJugador1.setBackground(new Color(153, 255, 51));
        pnlJugador1.setLayout(null);
        pnlJugador2.setBackground(new Color(0, 255, 255));
        pnlJugador2.setLayout(null);

        tpJugadores.setBounds(10, 40, 550, 170);
        tpJugadores.addTab("Martín Estrada Contreras", pnlJugador1);
        tpJugadores.addTab("Raul Vidal", pnlJugador2);

        //--------------- Listeners bonotones ---------------//
        btnRepartir.setBounds(10, 10, 100, 25);
        btnRepartir.setText("Repartir");
        btnRepartir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnRepartirClick(evt);
            }
        });

        btnVerificar.setBounds(120, 10, 100, 25);
        btnVerificar.setText("Verificar");
        btnVerificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnVerificarClick(evt);
            }
        });

        getContentPane().setLayout(null);
        getContentPane().add(tpJugadores);
        getContentPane().add(btnRepartir);
        getContentPane().add(btnVerificar);

        jugador1 = new Jugador();
        jugador2 = new Jugador();

    }

    private void btnRepartirClick(ActionEvent evt) {
        jugador1.repartir();
        jugador2.repartir();

        jugador1.mostrar(pnlJugador1);
        jugador2.mostrar(pnlJugador2);
    }

    private void btnVerificarClick(ActionEvent evt) {
        switch (tpJugadores.getSelectedIndex()) { //selecciona el indice del tablero donde se esta parado apra mostrar el mensaje con la informacion
            case 0:
                String m1 = jugador1.getEscalas();
                String puntaje = jugador1.getPuntaje();
                JOptionPane.showMessageDialog(null, jugador1.getGrupos() + m1 + puntaje); //el null es para que l cuadro de dialogo se muestre en el centro de la pantalla
                System.out.println("Las cartas que formaron grupos o escaleras para el jugador1 son: ");
                System.out.println(m1);

                break;
            case 1:
                String m2 = jugador2.getEscalas(); 
                String puntaje2 = jugador2.getPuntaje();
                JOptionPane.showMessageDialog(null, jugador2.getGrupos() + m2 + puntaje2);
                System.out.println("Las cartas que formaron grupos o escaleras para el jugador2 son: ");
                System.out.println(m2);

                break;
        }
    }

}
