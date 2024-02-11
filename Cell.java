import java.util.ArrayList;

public class Cell extends Thread{
    private boolean estado;
    private Mailbox mailbox;

    private ArrayList<Mailbox> vecinos;
    private int vecinosVivos;
    private int vecinosMuertos;

    private static Integer terminados = 0;
    private Boolean estadoFuturo = null;
    private int generaciones;
    private int n;
    private boolean acabado = false;

    public Cell(int generaciones, int n) {
        this.estado = false;
        this.vecinosVivos = 0;
        this.vecinosMuertos = 0;
        this.generaciones = generaciones;
        this.n = n;
    }

    public void enviarMensajes() throws InterruptedException{
        for (Mailbox vecino : vecinos) {
            vecino.almacenar(estado);
        }
    }

    public void procesarMensajes() throws InterruptedException {
        Boolean i;
        while (i = mailbox.retirar() != null) {
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
        terminados++;
    }

    public void reiniciarAtributos(){
        vecinosVivos = 0;
        vecinosMuertos = 0;
        estado = estadoFuturo;
        estadoFuturo = null;

    } 


    @Override
    public void run()  {

        while (generaciones > 0) {


                

            try {
                procesarMensajes();
                enviarMensajes();
                calcularEstado();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
           
            

            while (terminados != n*n) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            terminados = 0;


            reiniciarAtributos();
            generaciones--;
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

    public boolean getAcabado() {
        return acabado;
    }

    public void setVecinos(ArrayList<Mailbox> vecinos) {
        this.vecinos = vecinos;
    }

}
