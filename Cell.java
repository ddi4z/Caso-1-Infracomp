import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;

public class Cell extends Thread{


    private boolean estado;
    private Mailbox mailbox;

    private ArrayList<Mailbox> vecinos;
    private int vecinosVivos;

    private static int numeroGeneraciones;
    private static CyclicBarrier barreraGeneracion;
    private static CyclicBarrier barreraEstado;

    private static boolean fin = false;
    



    public Cell(Mailbox mailbox) {
        this.estado = false;
        this.mailbox = mailbox;
        this.vecinosVivos = 0;

    }

    public void procesarMensajes() throws InterruptedException {
        Boolean i = mailbox.retirar();
        if (i) {
            vecinosVivos++;
        }
    }

    public void calcularEstado() throws InterruptedException {
        if (estado) {
            if (vecinosVivos == 0 || vecinosVivos > 3) {
                estado = false;
            }
        }
        else if (vecinosVivos == 3) {
            estado = true;
        }
    }

    @Override
    public void run()  {
        try {
            for (int i = 0; i < numeroGeneraciones; i++) {
                for (Mailbox vecino : vecinos) {
                    vecino.almacenar(estado);
                    procesarMensajes();
                }
                System.out.println("Esperando en la barrera de estado " + estado + " " + vecinosVivos);
                barreraEstado.await();
                calcularEstado();
                vecinosVivos = 0;

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
