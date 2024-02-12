public class Consumer extends Thread{
    private Cell celda;

    public Consumer(Cell celda) {
        this.celda = celda;
    }

    public void process() throws InterruptedException {

        Boolean i;
        while (celda.getVecinosVivos()+ celda.getVecinosMuertos() < celda.getVecinos().size()) {
            i = celda.getMailbox().retirar();
            if (i) {
                celda.setVecinosVivos(celda.getVecinosVivos() + 1);
            }
            else {
                celda.setVecinosMuertos(celda.getVecinosMuertos() + 1);
            }
        } 
    }

    public void calculateStatus() throws InterruptedException {
        if (celda.getEstado()) {
            if (celda.getVecinosVivos() == 0 || celda.getVecinosVivos() > 3) {
                celda.setEstado(false); 
            }
        }
        else if (celda.getVecinosVivos() == 3) {
            celda.setEstado(true); 
        }
    }

    @Override
    public void run()  {
        try {
            for (int i = 0; i < Cell.getNumeroGeneraciones(); i++) {
                process();

                System.out.println("Esperando en la barrera de estado del consumidor");
                Cell.getBarreraEstado().await();
                calculateStatus();
                celda.setVecinosVivos(0);
                celda.setVecinosMuertos(0);

                System.out.println("Esperando en la barrera de generacion del consumidor");
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
