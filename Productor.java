public class Productor extends Thread{
    private Cell celda;

    public Productor(Cell celda) {
        this.celda = celda;
    }

    public void enviarMensaje() throws InterruptedException {
        for (Mailbox vecino : celda.getVecinos()) {
            vecino.almacenar(celda.getEstado());
        }
    }

    @Override
    public void run()  {
        try {
            for (int i = 0; i < Cell.getNumeroGeneraciones(); i++) {
                enviarMensaje();

                System.out.println("Esperando en la barrera de estado del productor");
                Cell.getBarreraEstado().await();
                
                celda.setVecinosVivos(0);

                System.out.println("Esperando en la barrera de generacion del productor");
                Cell.getBarreraGeneracion().await();
            }
            Cell.setFin();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}