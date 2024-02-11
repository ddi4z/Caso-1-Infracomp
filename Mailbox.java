import java.util.ArrayList;

public class Mailbox {
    private ArrayList<Boolean> messages;
    private int capacidad;
    private int count;
    private int esperado;

    public Mailbox (int capacidad) {
        this.capacidad = capacidad;
        this.messages = new ArrayList<Boolean> ();
        this.count = 0;

    }

    public void setEsperado(int esperado){
        this.esperado = esperado;
    }

    public synchronized void almacenar (Boolean i) throws InterruptedException {
        while (messages.size() == capacidad) wait();
        
        messages.add (i) ;
        notify () ;
    }

    public synchronized Boolean retirar () throws InterruptedException {
        Boolean i;
        if (count < esperado){
            while (messages.size () == 0) wait();
            i = (Boolean) messages.remove (0) ;
            count++;
            notify () ;
        }
        else {
            i = null;
        }
        return i;
    }

}

