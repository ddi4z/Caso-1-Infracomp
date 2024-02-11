
import java.util.Stack;

public class Mailbox {
    private Stack<Boolean> messages;
    private int capacidad;


    public Mailbox (int capacidad) {
        this.capacidad = capacidad;
        this.messages = new Stack<Boolean> ();
    }


    public synchronized void almacenar (Boolean i) throws InterruptedException {
        while (messages.size() == capacidad) {
            wait();
        }
        messages.add (i) ;
        notify ();
    }

    public synchronized Boolean retirar () throws InterruptedException {
        while (messages.size () == 0) wait();
        Boolean i = (Boolean) messages.remove (0) ;
        notify () ;
        return i;
    }
}

