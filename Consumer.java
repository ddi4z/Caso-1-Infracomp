public class Consumer extends Thread{
    private Cell cell;

    public Consumer(Cell cell) {
        this.cell = cell;
    }

    public void process() throws InterruptedException {

        Boolean i;
        for (int ind = 0; ind< cell.getNeighborMailboxes().size(); ind++) {
            try {
                i = cell.getMailbox().remove();
                while (  i == null) {
                    Thread.yield();
                    i = cell.getMailbox().remove();
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
                i = false;
            }
            if (i) {
                cell.setNeighborsAlive(cell.getNeighborsAlive() + 1);
            }
        }
    }

    public void calculateState() {
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
                Cell.getBarrier().await();
                calculateState();
                cell.setNeighborsAlive(0);

                System.out.println("Waiting at the consumer generation barrier");
                Cell.getBarrier().await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
