import java.util.Stack;

public class Mailbox {
    private Stack<Boolean> messages;
    private int capacity;

    public Mailbox (int capacity) {
        this.capacity = capacity;
        this.messages = new Stack<Boolean> ();
    }

    public synchronized void store (Boolean i) throws InterruptedException {
        while (messages.size() == capacity) {
            wait();
        }
        messages.add (i) ;
        notify();
    }

    public synchronized Boolean remove () throws InterruptedException {
        while (messages.size () == 0) {
            wait();
        }
        Boolean i = (Boolean) messages.pop();
        notifyAll();
        return i;
    }
}