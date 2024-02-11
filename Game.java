import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.CyclicBarrier;

public class Game{
    private static int n;
    private static Cell[][] board;

    public static void makeEmptyBoard(){
        board = new Cell[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = new Cell(new Mailbox(i+1));
            }
        }
    }

    public static void setBoard(String filename) throws FileNotFoundException {
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
            }
        }
        scanner.close();
    }

    public static void imprimir() {
        for (int i = 0; i < n; i++) {
            System.out.print("+");
            for (int j = 0; j < n; j++) {
                System.out.print("---+");
            }
            System.out.println();

            for (int j = 0; j < n; j++) {
                System.out.print("| ");
                if (board[i][j].getEstado()) {
                    System.out.print("* ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println("|");
        }
        System.out.print("+");
        for (int j = 0; j < n; j++) {
            System.out.print("---+");
        }
        System.out.println();
    }

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el nombre del archivo: \n");
        String nombreArchivo = scanner.nextLine();

        System.out.print("Ingrese la cantidad de generaciones (entero): \n");
        int generaciones = scanner.nextInt();

        setBoard(nombreArchivo);

        Cell.setNumeroGeneraciones(generaciones);
        Cell.setBarreraEstado(new CyclicBarrier(n*n));
        Cell.setBarreraGeneracion(new CyclicBarrier(n*n));

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j].start();
            }
        }

        while (! Cell.isFin()) {
            //System.out.println("Esperando finalización");
            Thread.yield();
        }

        imprimir();
        scanner.close();
	}
}
