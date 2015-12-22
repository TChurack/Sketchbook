/**
 * Reads in a sudoku puzzle from STDIN as 81 integers corresponding
 * to each cell in the puzzle, starting with the top left cell and 
 * moving right along each row from top to bottom. Empty cells are denoted
 * by 0
 *
 * Backtracking is used to solve the puzzle.
 *
 * The Solution is then printed to STDOUT.
 */
import java.util.*;

public class Solution {
    static boolean solved;
    
    static int[][] board;
    
	//Helper function that prints the board to STDOUT
    static void printBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
    
	//Places the value 'move' at index 'index'
    static void makeMove(int index, int move) {
        int row = index/9;
        int col = index%9;
        
        board[row][col] = move;
        if (row == 8 && col == 8) { solved = true; }
    }
    
	//Undoes a move by resetting the value to 0
    static void unmakeMove(int index) {
        if (!solved) { //Don't want to unmake the move that solved it!
            int row = index/9;
            int col = index%9;
            
            board[row][col] = 0;
        }
    }
    
	//Determine possible moves that can be made at a particular index
    static boolean[] possMoves(int index) {
        boolean[] res = new boolean[9];
        Arrays.fill(res, true); //Assume all moves possible until proven otherwise
        int row = index/9;
        int col = index%9;
        
		//Determine the Row and Col at which the 3x3 square containing index begins
        int boxRow = 3*(row/3); 
        int boxCol = 3*(col/3);
        
        for (int i = 0; i < 9; i++) {
			//Check values in the same column
            if (i != row && board[i][col] != 0) {
                res[board[i][col] - 1] = false;
            }
			//Check values in the same row
            if (i != col && board[row][i] != 0) {
               res[board[row][i] - 1] = false;
            }
        }
        
		//Check values in the same square
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int rowInd = boxRow + i;
                int colInd = boxCol + j;
                if ((row != rowInd || col != colInd) && board[rowInd][colInd] != 0) {
                    res[board[rowInd][colInd] - 1] = false;
                }
            }
        }
        
        return res;
    }
    
    static void backtrack(int nextInd) {
        while ((nextInd/9 < 9 && nextInd%9 < 9) && board[nextInd/9][nextInd%9] != 0) { nextInd++; }
        
        if (nextInd/9 == 8 && nextInd%9 == 8 && board[8][8] != 0) {
            solved = true;
        } else if (nextInd < 81) {
            boolean validMove = false;
            boolean[] legalMoves = possMoves(nextInd);
            for (int i = 0; i < 9; i++) {
                validMove = legalMoves[i];
                if (!solved && validMove) {  
                    makeMove(nextInd, i+1);
                    backtrack(nextInd+1);
                    unmakeMove(nextInd);
                }
            }
        } else { solved = true; }
    }
    
    static void sudoku_solve(int [][] grid){
        board = grid;
        backtrack(0);
        
        if (!solved) { System.out.println("No solution found... "); }
        
        printBoard();
    }
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n;
        int grid[][] = new int[9][9];

        n = in.nextInt();

        for(int i = 0; i < n; i++) {
            solved = false;
            for(int j = 0; j < 9; j++) {
                for(int k = 0; k < 9; k++) {
                    grid[j][k] = in.nextInt();
                }
            }
            sudoku_solve(grid);
        }
    }
}
