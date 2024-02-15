import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;

public class Cell{

    // Basic attributes
    private boolean state;
    private Mailbox mailbox;


    // Neighbors
    private ArrayList<Mailbox> neighborMailboxes;
    private int neighborsAlive;

    // Static attributes to control the game turns
    private static int generationsNum;
    private static CyclicBarrier turnBarrier;

    // Static attribute to control the end of the game
    private static CyclicBarrier endBarrier;


    // Producer and Consumer
    private Producer producer;
    private Consumer consumer;

    public Cell(Mailbox mailbox) {
        this.state = false;
        this.mailbox = mailbox;
        this.neighborsAlive = 0;
        this.producer = new Producer(this);
        this.consumer = new Consumer(this);
    }

    public void activate()  {
        consumer.start();
        producer.start();
    }

    // Getters and setters
    public boolean getState() {
        return state;
    }

    public Mailbox getMailbox() {
        return mailbox;
    }


    public void setState(boolean state) {
        this.state = state;
    }

    public void setNeighborMailboxes(ArrayList<Mailbox> neighborMailboxes) {
        this.neighborMailboxes = neighborMailboxes;
    }

    public static void setGenerationsNum (int generationsNum ) {
        Cell.generationsNum  = generationsNum ;
    }

    public static void setTurnBarrier(CyclicBarrier turnBarrier) {
        Cell.turnBarrier = turnBarrier;
    }



    public ArrayList<Mailbox> getNeighborMailboxes() {
        return neighborMailboxes;
    }

    public int getNeighborsAlive() {
        return neighborsAlive;
    }

    public void setNeighborsAlive(int neighborsAlive) {
        this.neighborsAlive = neighborsAlive;
    }

    public static int getGenerationsNum () {
        return generationsNum ;
    }


    public static CyclicBarrier getTurnBarrier() {
        return turnBarrier;
    }



    public static CyclicBarrier getEndBarrier() {
        return endBarrier;
    }

    public static void setEndBarrier(CyclicBarrier endBarrier) {
        Cell.endBarrier = endBarrier;
    }

}
