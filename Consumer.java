public class Consumer extends Thread{
    private Cell cell;

    public Consumer(Cell cell) {
        this.cell = cell;
    }

    public void process() throws InterruptedException {

        Boolean i;
        while (cell.getNeighborsAlive()+ cell.getNeighborsDead() < cell.getNeighborMailboxes().size()) {
            i = cell.getMailbox().remove();
            if (i) {
                cell.setNeighborsAlive(cell.getNeighborsAlive() + 1);
            }
            else {
                cell.setNeighborsDead(cell.getNeighborsDead() + 1);
            }
        } 
    }

    public void calculateState() throws InterruptedException {
        if (cell.getState()) {
            if (cell.getNeighborsAlive() == 0 || cell.getNeighborsAlive() > 3) {
                cell.setState(false); 
            }
        }
        else if (cell.getNeighborsAlive() == 3) {
            cell.setState(true); 
        }
    }

    @Override
    public void run()  {
        try {
            for (int i = 0; i < Cell.getGenerationsNum (); i++) {
                process();

                System.out.println("Waiting at the consumer status barrier");
                Cell.getStateBarrier().await();
                calculateState();
                cell.setNeighborsAlive(0);
                cell.setNeighborsDead(0);

                System.out.println("Waiting at the consumer generation barrier");
                Cell.getGenerationBarrier().await();
            }
            Cell.setEnd();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
