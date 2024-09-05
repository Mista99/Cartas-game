import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Carta {

    private int indice;

    public Carta(Random r) {
        indice = r.nextInt(52) + 1;
    }

    public void mostrar(JPanel pnl, int x, int y) {
        String nombreImagen = "/imagenes/CARTA" + String.valueOf(indice) + ".jpg";
        ImageIcon imagen = new ImageIcon(getClass().getResource(nombreImagen)); //obtener las imagen de donde esta almacenada en el ordenador

        JLabel lbl = new JLabel(imagen);
        lbl.setBounds(x, y, imagen.getIconWidth(), imagen.getIconHeight());

        pnl.add(lbl);
    }

    public Pinta getPinta() {
        if (indice <= 13) {
            return Pinta.TREBOL;
        } else if (indice <= 26) {
            return Pinta.PICA;
        } else if (indice <= 39) {
            return Pinta.CORAZON;
        } else {
            return Pinta.DIAMANTE;
        }
    }

    // utiliza el mod para saber cual carta es, es una forma de ciclado, tenemos 13 cartas, a partir de la numero 13 se empiezan a repetir, por lo que el mod 13 me asegura que al colocar un valor superior a 13, se vuelva a empezar
    // eje: 16%13 = 3, es decir un residuo de 3, osea que ese 3 es una carta que se vuelve a repetir, en este caso es el el 3, es una forma facil de saber que carta tengo sin importar la pinta.
    // en el caso de multiplo de 13 tengo que hacer residuo = 13, pues siempre tendria la carta 13 es decir la K
    public NombreCarta getNombre() {
        int residuo = indice % 13;
        if (residuo == 0) {
            residuo = 13;
        }
        return NombreCarta.values()[residuo - 1]; //residuo - 1 porque las matrices empiezan desde 0.
    }

}
