import java.util.Random;

import javax.swing.JPanel;

public class Jugador {

    private final int TOTAL_CARTAS = 10; //la palabra final es para decir que sera una especie de constante, es decir el valor de 10 no puede ser modificado.
    private final int MARGEN = 10; //marge entre la caja y el grupo de 10 cartas en pantalla
    private final int DISTANCIA = 50;

    private Carta[] cartas = new Carta[TOTAL_CARTAS]; //TOTAL_CARTAS es la longitud del array
    private Random r = new Random(); //inicializa la instancia de random para generar numeros aleatorios, es como crear la semilla

    //ciclo for-each en Java, dónde Carta es el tipo.
    //recorre un array vacio, de tamaño TOTAL_CARTAS, y cada posición vacia le asigna una carta con una semilla r para que internamente la clase cree un indice aleatorio entre 0 y 52
    public void repartir() {
        int i = 0;
        for (Carta c : cartas) {
            cartas[i++] = new Carta(r); //i se incrementa solo despues de cada asignacion de carta al array
        }
    }

    public void mostrar(JPanel pnl) {
        pnl.removeAll(); //se remueve todo, pues al darle repartir de nuevo no pueden haber elementos vacios

        //Las cartas se agregan de derecha a izquierda para que se solapen de manera correcta
        int p = 1;
        for (Carta c : cartas) {
            // el origen en X es la pare izquierda de donde empieza el panel, y el origen en Y, eria la parte de arriba del panel
            c.mostrar(pnl, MARGEN + TOTAL_CARTAS * DISTANCIA - p++ * DISTANCIA, MARGEN); //TOTAL_CARTAS * DISTANCIA: Esto seria la distancia total horizontal que ocupan las 10
            
        }
        //De esta manera se pueden colocar las cartas de izuqierda a derecha, pero al hacerlo se taparia el numero del lado izquierdo, dnado la impresión de que las cartas estan al revés
        // for (Carta c : cartas) {
        //     c.mostrar(pnl, MARGEN + p * DISTANCIA, MARGEN); // Calcula la posición horizontal en función del índice 'p'
        //     p++; // Incrementa la posición para la siguiente carta
        // }

        pnl.repaint(); 
    }

    public String getGrupos() {
        String mensaje = "No se encontraron grupos";

        int[] contadores = contarCartas();

        boolean hayGrupos = false;
        for (int i = 0; i < contadores.length; i++) {
            if (contadores[i] >= 2) {
                if (!hayGrupos) {
                    hayGrupos = true;
                    mensaje = "Se encontraron los siguientes grupos:\n";
                }
                mensaje += Grupo.values()[contadores[i]] + " de " + NombreCarta.values()[i] + "\n";
                //mensaje = contador en letras () + Nombre de la carta
            }
        }

        return mensaje;
    }

    public int[] contarCartas () {
        int[] contadores = new int[NombreCarta.values().length]; //crea un array del tamaño de el enum NombreCarta
        for (Carta c : cartas) {
            contadores[c.getNombre().ordinal()]++; //devuelve la posicion indice    del nombre de la carta. Lo de por dentro seria la posicion de la carta donde se va a contar.
            // contadores = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]

            // Después de procesar las cartas:
            //contadores = [1, 0, 0, 3, 0, 0, 0, 0, 0, 1, 1, 2, 2]
        }
        return contadores;

    }

    public String getEscalas() {
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("Escalera de tréboles:\n");
        mensaje.append(detectarEscalera(contarCartasPorPinta(Pinta.TREBOL)));
        mensaje.append("Escalera de picas:\n");
        mensaje.append(detectarEscalera(contarCartasPorPinta(Pinta.PICA)));
        mensaje.append("Escalera de corazones:\n");
        mensaje.append(detectarEscalera(contarCartasPorPinta(Pinta.CORAZON)));
        mensaje.append("Escalera de diamantes:\n");
        mensaje.append(detectarEscalera(contarCartasPorPinta(Pinta.DIAMANTE)));
        
        return mensaje.toString();
    }
    //cuenta las cartas en la matriz contadores
    private int[] contarCartasPorPinta(Pinta pinta) {
        int[] contadores = new int[NombreCarta.values().length];
        for (Carta c : cartas) {
            if (c.getPinta() == pinta) {
                contadores[c.getNombre().ordinal()]++;
            }
        }
        return contadores;
    }
    
    private String detectarEscalera(int[] contadores) {
        StringBuilder escalera = new StringBuilder();
        int consecutivas = 0;
        
        for (int i = 0; i < contadores.length; i++) {
            //evaluo la posicion para saber si hay al menos una carta
            if (contadores[i] > 0) {
                consecutivas++;
                escalera.append(NombreCarta.values()[i]).append(" ");
            } else {
                if (consecutivas >= 3) { // Consideramos una escalera válida si hay al menos 2 cartas consecutivas
                    return escalera.toString() + "\n"; //devuelve el mensaje solo si hay 2 consecutivas
                }
                escalera.setLength(0);
                consecutivas = 0;
            }
        }
        
        if (consecutivas >= 3) {
            return escalera.toString() + "\n";
        }
        
        return "No se encontró una escalera"+"\n";
    }
    
    public String getPuntaje(){
        int[] contadores = contarCartas();
        int puntaje = 0;
        
        // Calcula escaleras
        String escaleraTrebol = detectarEscalera(contarCartasPorPinta(Pinta.TREBOL));
        String escaleraPica = detectarEscalera(contarCartasPorPinta(Pinta.PICA));
        String escaleraCorazon = detectarEscalera(contarCartasPorPinta(Pinta.CORAZON));
        String escaleraDiamante = detectarEscalera(contarCartasPorPinta(Pinta.DIAMANTE));
        
        // Excluir cartas que forman escaleras o grupos
        System.out.println("Las cartas que no formaron grupos o escaleras son: ");
        for (Carta c : cartas) {
            if (contadores[c.getNombre().ordinal()] == 1 && //si la carta existe == 1, si la carta no forma grupos, osea que no sea mayor a 1 en contadores.
                !esCartaEnEscalera(c.getNombre().ordinal(), escaleraTrebol) && //si la carta no esta en ninguna de las matrices de las escaleras de cada pinta
                !esCartaEnEscalera(c.getNombre().ordinal(), escaleraPica) && 
                !esCartaEnEscalera(c.getNombre().ordinal(), escaleraCorazon) && 
                !esCartaEnEscalera(c.getNombre().ordinal(), escaleraDiamante)) {
        
                int valorCarta = (c.getNombre() == NombreCarta.AS || c.getNombre() == NombreCarta.JACK || 
                                  c.getNombre() == NombreCarta.QUEEN || c.getNombre() == NombreCarta.KING) ? 10 : (c.getNombre().ordinal() + 1);
                puntaje += valorCarta;
                System.out.println(c.getNombre() + " de " + c.getPinta() + " con valor " + valorCarta + " puntos");
            }
        }
        
    
        String mensaje = "\n\nSu puntaje total es: " + puntaje;
        return mensaje;
    }
    
    private boolean esCartaEnEscalera(int indice, String escalera) {
        // Verifica si la carta está en la escalera
        NombreCarta carta = NombreCarta.values()[indice];
        return escalera.contains(carta.name());
    }
    

    // public static void imprimirEscaleras(int[][] escaleras) {
    //     System.out.print("Treboles: ");
    //     imprimirFila(escaleras[0]);
    //     System.out.print("Picas: ");
    //     imprimirFila(escaleras[1]);
    //     System.out.print("Corazones: ");
    //     imprimirFila(escaleras[2]);
    //     System.out.print("Diamantes: ");
    //     imprimirFila(escaleras[3]);
    // }

    // // Método auxiliar para imprimir una fila de la matriz
    // public static void imprimirFila(int[] fila) {
    //     for (int i = 0; i < fila.length; i++) {
    //         System.out.print(fila[i]);
    //         if (i < fila.length - 1) {
    //             System.out.print(", ");
    //         }
    //     }
    //     System.out.println();
    // }
    

}
