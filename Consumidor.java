public class Consumidor extends Thread{
    private Cell celda;

    public Consumidor(Cell celda) {
        this.celda = celda;
    }

    public void procesarMensaje() throws InterruptedException {

        Boolean i;
        while (i=celda.getMailbox().retirar() != null){
            if (i) {
                celda.setVecinosVivos(celda.getVecinosVivos() + 1);
            }
        } 
    }

    public void calcularEstado() throws InterruptedException {
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
                procesarMensaje();

                System.out.println("Esperando en la barrera de estado del consumidor");
                Cell.getBarreraEstado().await();
                calcularEstado();
                celda.setVecinosVivos(0);

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
