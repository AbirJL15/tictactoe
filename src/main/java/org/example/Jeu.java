package org.example;


// Classe principale Jeu
public class Jeu {
    private static final int GRID_SIZE = 3;
    private char[][] grid;
    private char currentPlayer;
    private int turn;

    public Jeu() {
        initializeGrid();
    }

    private void initializeGrid() {
        grid = new char[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                grid[i][j] = ' ';
            }
        }
        currentPlayer = 'X';
        turn = 1;
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public char[][] getGrid() {
        return grid;
    }

    public void placePiece(int x, int y) {
        if (x < 0 || x >= GRID_SIZE) {
            throw new RuntimeException("Invalid X position");
        }
        if (y < 0 || y >= GRID_SIZE) {
            throw new RuntimeException("Invalid Y position");
        }
        if (grid[x][y] != ' ') {
            throw new RuntimeException("Space already occupied");
        }

        grid[x][y] = currentPlayer;

        if (checkWin(x, y)) {
            System.out.println("Player " + currentPlayer + " wins!");
            initializeGrid();
        } else if (turn == GRID_SIZE * GRID_SIZE) {
            System.out.println("It's a draw!");
            initializeGrid();
        } else {
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            turn++;
        }
    }

    private boolean checkWin(int x, int y) {
        // Check row
        if (grid[x][0] == currentPlayer && grid[x][1] == currentPlayer && grid[x][2] == currentPlayer) {
            return true;
        }
        // Check column
        if (grid[0][y] == currentPlayer && grid[1][y] == currentPlayer && grid[2][y] == currentPlayer) {
            return true;
        }
        // Check diagonal
        if (x == y && grid[0][0] == currentPlayer && grid[1][1] == currentPlayer && grid[2][2] == currentPlayer) {
            return true;
        }
        // Check anti-diagonal
        if (x + y == 2 && grid[0][2] == currentPlayer && grid[1][1] == currentPlayer && grid[2][0] == currentPlayer) {
            return true;
        }
        return false;
    }
}