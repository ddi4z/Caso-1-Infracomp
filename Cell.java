import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;

public class Cell extends Thread{


    private boolean estado;
    private Mailbox mailbox;

    private ArrayList<Mailbox> vecinos;
    private int vecinosVivos;
    private int vecinosMuertos;

    private Boolean estadoFuturo = null;

    private static int numeroGeneraciones;
    private static CyclicBarrier barreraGeneracion;
    private static CyclicBarrier barreraEstado;

    private static boolean fin = false;



    public Cell(int n) {
        this.estado = false;
        this.vecinosVivos = 0;
        this.vecinosMuertos = 0;
    }




    public void enviarMensajes() throws InterruptedException{
        for (Mailbox vecino : vecinos) {
            vecino.almacenar(estado);
        }
    }

    public void procesarMensajes() throws InterruptedException {

        while (vecinosMuertos + vecinosVivos < vecinos.size()) {
            Boolean i = mailbox.retirar();
            if (i) {
                vecinosVivos++;
            }
            else {
                vecinosMuertos++;
            }
        }
    }

    public void calcularEstado() throws InterruptedException {
        if (estado) {
            if (vecinosVivos == 0 || vecinosVivos > 3) {
                estadoFuturo = false;
            }
            else {
                estadoFuturo = true;
            }
        }
        else {
            if (vecinosVivos == 3) {
                estadoFuturo = true;
            }
            else {
                estadoFuturo = false;
            }
        }

    }

    public void reiniciarAtributos(){
        vecinosVivos = 0;
        vecinosMuertos = 0;
        estado = estadoFuturo;
        estadoFuturo = null;
    }


    @Override
    public void run()  {
        try {
            for (int i = 0; i < numeroGeneraciones; i++) {
                enviarMensajes();
                procesarMensajes();


                System.out.println("Esperando en la barrera de estado");
                barreraEstado.await();
                calcularEstado();
                reiniciarAtributos();
                System.out.println("Esperando en la barrera de generacion");
                barreraGeneracion.await();

            }
            Cell.fin = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Getters y setters
    public boolean getEstado() {
        return estado;
    }

    public Mailbox getMailbox() {
        return mailbox;
    }

    public void setMailbox(Mailbox mailbox) {
        this.mailbox = mailbox;
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
}
