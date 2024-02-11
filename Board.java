import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Board{
    private int n;
    private int generaciones;
    private Cell[][] board;

    public void makeEmptyBoard(){
        board = new Cell[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = new Cell(generaciones, n);
                board[i][j].setMailbox(new Mailbox(i+1));
                board[i][j].setMailbox(new Mailbox(20));
            }
        }
    }


    public void setBoard(String filename) throws FileNotFoundException {

        Scanner scanner = new Scanner(new File(filename));
        n  = Integer.parseInt(scanner.nextLine());

        makeEmptyBoard();

        for (int i = 0; i < n; i++) {
            String[] elementos = scanner.nextLine().split(",");
            for (int j = 0; j < n; j++) {
                ArrayList<Mailbox> vecinos = new ArrayList<Mailbox>();
                board[i][j].setEstado(Boolean.parseBoolean(elementos[j]));

                for (int k = -1; k <= 1 ; k++) {
                    for (int l = -1; l <= 1; l++) {
                        if (k != 0 || l != 0) {
                            if (i+k >= 0 && i+k < n && j+l >= 0 && j+l < n)  vecinos.add(board[i+k][j+l].getMailbox());
                        }
                    }
                }
                board[i][j].setVecinos(vecinos);
                board[i][j].getMailbox().setEsperado(vecinos.size());
            }
        }
        scanner.close();
    }

    public void imprimir() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(board[i][j].getEstado() + " ");
            }
            System.out.println();
        }
    }

    public Board(String filename, int generaciones) throws FileNotFoundException  {
        this.generaciones = generaciones;
        setBoard(filename);
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j].start();
            }
        }

        while (!board[0][0].getAcabado()) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Ingrese el nombre del archivo: ");
        String nombreArchivo = scanner.nextLine();
        
        System.out.print("Ingrese la cantidad de generaciones (entero): ");
        int generaciones = scanner.nextInt();

        Board board = new Board(nombreArchivo, generaciones);

        board.imprimir();
        scanner.close();
	}
}
