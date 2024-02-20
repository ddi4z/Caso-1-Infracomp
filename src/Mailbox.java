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
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        messages.add (i) ;
        notify();
    }

    public synchronized Boolean remove () throws InterruptedException {
        Boolean i;
        if (messages.size () == 0) {
            i = null;
        }
        else {
            i = (Boolean) messages.pop();
            notify();
        }
        return i;
    }
}