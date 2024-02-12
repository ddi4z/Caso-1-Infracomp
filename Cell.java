import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;

public class Cell{

    // Atributos basicos
    private boolean estado;
    private Mailbox mailbox;

    // Vecinos
    private ArrayList<Mailbox> vecinos;
    private int vecinosVivos;
    private int vecinosMuertos;

    // Atributos estaticos para las barreras
    private static int numeroGeneraciones;
    private static CyclicBarrier barreraGeneracion;
    private static CyclicBarrier barreraEstado;

    // Productor y consumidor
    private Productor productor;
    private Consumidor consumidor;

    // Atributo estatico para saber si se ha terminado
    private static boolean fin = false;


    


    public void setMailbox(Mailbox mailbox) {
        this.mailbox = mailbox;
    }



    public ArrayList<Mailbox> getVecinos() {
        return vecinos;
    }



    public int getVecinosVivos() {
        return vecinosVivos;
    }



    public void setVecinosVivos(int vecinosVivos) {
        this.vecinosVivos = vecinosVivos;
    }



    public int getVecinosMuertos() {
        return vecinosMuertos;
    }



    public void setVecinosMuertos(int vecinosMuertos) {
        this.vecinosMuertos = vecinosMuertos;
    }



    public static int getNumeroGeneraciones() {
        return numeroGeneraciones;
    }



    public static CyclicBarrier getBarreraGeneracion() {
        return barreraGeneracion;
    }



    public static CyclicBarrier getBarreraEstado() {
        return barreraEstado;
    }



    public Productor getProductor() {
        return productor;
    }



    public void setProductor(Productor productor) {
        this.productor = productor;
    }



    public Consumidor getConsumidor() {
        return consumidor;
    }



    public void setConsumidor(Consumidor consumidor) {
        this.consumidor = consumidor;
    }



    public static void setFin(boolean fin) {
        Cell.fin = fin;
    }



    public Cell(Mailbox mailbox) {
        this.estado = false;
        this.mailbox = mailbox;
        this.vecinosVivos = 0;
        this.productor = new Productor(this);
        this.consumidor = new Consumidor(this);
    }



    public void activar()  {
        productor.start();
        consumidor.start();
    }

    // Getters y setters
    public boolean getEstado() {
        return estado;
    }

    public Mailbox getMailbox() {
        return mailbox;
    }


    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public void setVecinos(ArrayList<Mailbox> vecinos) {
        this.vecinos = vecinos;
    }

    public static void setNumeroGeneraciones(int numeroGeneraciones) {
        Cell.numeroGeneraciones = numeroGeneraciones;
    }

    public static void setBarreraGeneracion(CyclicBarrier barreraGeneracion) {
        Cell.barreraGeneracion = barreraGeneracion;
    }

    public static void setBarreraEstado(CyclicBarrier barreraEstado) {
        Cell.barreraEstado = barreraEstado;
    }

    public static boolean isFin() {
        return fin;
    }

    public static void setFin() {
        Cell.fin = true;
    }
}
