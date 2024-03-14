import java.util.Random;
import java.util.Scanner;

public class Minesweeper {
    public static void main(String[] args) {
        // Size of gameboard and no. of bomb.
        int size = 15;
        int bombs = 30;

        // Character use to represent empty, bomb and revealed.
        char empty = '-';
        char bomb = 'X';
        char revealed = ' ';

        // Initialize the gameboard and the bomb count array
        char[][] board = new char[size][size];
        int[][] bombCount = new int[size][size];

        // Fill the gameboard with empty cells
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = empty;
            }
        }

        // Put the bombs on the gameboard randomly
        Random rand = new Random();
        for (int i = 0; i < bombs; i++) {
            int x, y;
            do {
                x = rand.nextInt(size);
                y = rand.nextInt(size);
            } while (board[x][y] == bomb);
            board[x][y] = bomb;

            // Update the bomb count for the 8 block of cell surrounding the chosen coordinate.
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    int nx = x + dx, ny = y + dy;
                    if (nx >= 0 && nx < size && ny >= 0 && ny < size) {
                        bombCount[nx][ny]++;
                    }
                }
            }
        }

        // Start the game
        Scanner scanner = new Scanner(System.in);
        boolean gameOver = false;

        while (!gameOver) {
            // Print the game board
        	System.out.println("Legends: X = Bomb, - = empty.");
            System.out.println("Total bombs on the board: " + bombs);
            System.out.print("    ");
            for (int i = 1; i <= size; i++) {
                if (i < 10) {
                    System.out.print(" " + i + " ");
                } else {
                    System.out.print(i + " ");
                }
            }
            System.out.println();
            for (int i = 0; i < size; i++) {
                if (i < 9) {
                    System.out.print(" " + (i+1) + " ");
                } else {
                    System.out.print((i+1) + " ");
                }
                for (int j = 0; j < size; j++) {
                    if (board[i][j] == revealed) {
                        System.out.print(" " + bombCount[i][j] + " ");
                    } else {
                        System.out.print(" " + empty + " ");
                    }
                }
                System.out.println();
            }

            // Ask for the next move
            System.out.println("Enter the coordinates of the cell you want to reveal (x y):");
            String xInput = scanner.next();
            String yInput = scanner.next();

            int x = -1;
            int y = -1;

            // Convert the input to integers
            try {
                x = Integer.parseInt(xInput) - 1;
                y = Integer.parseInt(yInput) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter numbers only.");
                continue;
            }

            // Check if the coordinates are correct
            if (x < 0 || x >= size || y < 0 || y >= size) {
                System.out.println("Invalid coordinates. Please enter numbers between 1 and " + size);
                continue;
            }

            // Check if the chosen cell is a bomb
            if (board[x][y] == bomb) {
                gameOver = true;
                System.out.println("Game Over! You hit a bomb.");
                System.out.print("    ");
                for (int i = 1; i <= size; i++) {
                    if (i < 10) {
                        System.out.print(" " + i + " ");
                    } else {
                        System.out.print(i + " ");
                    }
                }
                System.out.println();
                for (int i = 0; i < size; i++) {
                    if (i < 9) {
                        System.out.print(" " + (i+1) + " ");
                    } else {
                        System.out.print((i+1) + " ");
                    }
                    for (int j = 0; j < size; j++) {
                        if (board[i][j] == bomb) {
                            System.out.print(" " + bomb + " ");
                        } else if (board[i][j] == revealed) {
                            System.out.print(" " + bombCount[i][j] + " ");
                        } else {
                            System.out.print(" " + empty + " ");
                        }
                    }
                    System.out.println();
                }
            } else {
                // Reveal the chosen cell and its area
                board[x][y] = revealed;
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        int nx = x + dx, ny = y + dy;
                        if (nx >= 0 && nx < size && ny >= 0 && ny < size) {
                            board[nx][ny] = revealed;
                        }
                    }
                }
            }
        }
    }
}
