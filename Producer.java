public class Producer extends Thread{
    private Cell cell;

    public Producer(Cell cell) {
        this.cell = cell;
    }

    public void sendMessage() throws InterruptedException {
        for (Mailbox neighbor : cell.getNeighborMailboxes()) {
            neighbor.store(cell.getState());
        }
    }

    @Override
    public void run()  {
        try {
            for (int i = 0; i < Cell.getGenerationsNum (); i++) {
                sendMessage();

                System.out.println("Waiting at the producer status barrier");
                Cell.getStateBarrier().await();

                System.out.println("Waiting at the producer generation barrier");
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