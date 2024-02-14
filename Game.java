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
            String[] elements = scanner.nextLine().split(",");
            for (int j = 0; j < n; j++) {
                ArrayList<Mailbox> neighborMailboxes = new ArrayList<Mailbox>();
                board[i][j].setState(Boolean.parseBoolean(elements[j]));

                for (int k = -1; k <= 1 ; k++) {
                    for (int l = -1; l <= 1; l++) {
                        if (k != 0 || l != 0) {
                            if (i+k >= 0 && i+k < n && j+l >= 0 && j+l < n)  neighborMailboxes.add(board[i+k][j+l].getMailbox());
                        }
                    }
                }
                board[i][j].setNeighborMailboxes(neighborMailboxes);
            }
        }
        scanner.close();
    }

    public static void printAnswer() {
        for (int i = 0; i < n; i++) {
            System.out.print("+");
            for (int j = 0; j < n; j++) {
                System.out.print("---+");
            }
            System.out.println();

            for (int j = 0; j < n; j++) {
                System.out.print("| ");
                if (board[i][j].getState()) {
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
        System.out.println("Enter the file name:");
        String fileName = scanner.nextLine();

        System.out.println("Enter the number of generations (integer): ");
        int generations = scanner.nextInt();

        setBoard(fileName);

        Cell.setGenerationsNum (generations);
        Cell.setStateBarrier(new CyclicBarrier(2*(n*n)));
        Cell.setGenerationBarrier(new CyclicBarrier(2*(n*n)));

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j].activate();
            }
        }

        while (! Cell.isEnd()) {
            //System.out.println("Waiting for completion");
            Thread.yield();
        }

        printAnswer();
        scanner.close();
	}
}
