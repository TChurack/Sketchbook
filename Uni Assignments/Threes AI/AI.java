
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class AI {
	
	public static int BUFFER_SIZE = 10000;
	public static String[] nextNum; //used to store input of moves
	public static String inputText = "input.txt"; //This is the name of the input file specifying the game input
	public static String outputText = "output.txt";
	public static int ply =6;
	
	
	public static void main(final String[] args) throws IOException{
		String line;
		nextNum = new String[BUFFER_SIZE];
		char[] inrow = new char[8];
		int[][] board = new int[4][4];
		String[] input = new String[BUFFER_SIZE];
		char[] buffer = new char[BUFFER_SIZE];
		FileReader fr = new FileReader(inputText); //input filename
		BufferedReader br = new BufferedReader(fr);
		
	
		int rownum = 1;
	
		br.readLine();//skip first two lines
		br.readLine();
		while (rownum <= 4) {//fetch initial board
			line = br.readLine();
			inrow = line.toCharArray();
			int col = 1;
			for (int i = 0;i<7;i++){
				if (inrow[i] > 47) {
					board[rownum-1][col-1] =  inrow[i] - 48; // converts from Chars to ints
					col++;
				}
			}
			rownum++;
		}
		
		
		
		
		
		br.readLine(); //skip line
		br.read(buffer, 0, buffer.length);
		input = String.valueOf(buffer).split("\\D");//removes non-digits (eg. whitespace) from array and uses them to determine each individual input tile
		br.close();
		int k = 0;
		for (int i = 0;i<input.length;i++){ //loop removes empty string(s) caused by end of line
			if(input[i].length() !=0){
				nextNum[k] = input[i];
				k++;
			}
		}
		System.out.println();
		state root = new state(null,'0',board,false);//initialise the inital game state
		
		state optState = doMM(root,ply); //calls minimax function to desired ply
		FileWriter fw = new FileWriter(outputText);
		BufferedWriter bw = new BufferedWriter(fw);
        bw.write("Moves completed: " + optState.depth + " Final Score: " + optState.getScore());
        bw.newLine();
        bw.newLine();
        System.out.println(optState.getPath());
        for(int i = 0; i< optState.depth;i++){
        	bw.write(optState.path[i]);
        	if((i+1)%50 == 0){bw.newLine();}//ensures max line length of 50 characters
        }
        bw.close();
		fw.close();
	}
	
	public static state moveLeft(final state node) {//performs a left move
		boolean successmove = false;
		int[][] board = new int[4][4];
		for (int i=0;i<4;i++) {	
			for (int j=0;j<4;j++){
					board[i][j] = node.board[i][j];
			}
		}
		int nextInt = node.nextNum;
		boolean[] rowshift = new boolean[4];
		for (int i = 0;i<4;i++){
			boolean rowsucc = false;
			for (int j = 0;j<4;j++){
				if (rowsucc) {
					board[i][j-1] = board[i][j]; //will never happen for first column, no need to check i,j-1 are valid coordinates
					
				} else if (!rowsucc){
					if (j!= 0  && (board[i][j] != 0)){ //ensures not checking value of non-existent column
						if ((board[i][j-1]) == 0){
							rowsucc = true;
							board[i][j-1] = board[i][j];
						} else if ((board[i][j-1] + board[i][j]) == 3 && (board[i][j] == 1 || board[i][j] == 2)){
							rowsucc = true;
							board[i][j-1] = 3;
						} else if ((board[i][j-1] == board[i][j]) && (board[i][j] != 1) && (board[i][j] != 2)) {
							board[i][j-1] = 2*board[i][j];
							rowsucc = true;
						}
					}
				}
				if (j == 3 && rowsucc){ // determines which rows were shifted
					board[i][j] = 0;
					successmove = true;
					rowshift[i] = true;
					
				}
			}
				
		}
		
		if (successmove){//inserts new tile due to successful move
			int leastArray = -1;
			int[] leastValue = new int[4];
			for (int i = 0; i<4; i++){
				if (rowshift[i]){
					if (leastArray == -1){
						leastArray = i;
						for (int j = 0; j<4;j++){
							leastValue[j] = board[i][3-j];
						}
					} else {
						boolean lessthanequal = false;
						for (int k = 0; k<4;k++) {
							if(board[i][3-k] < leastValue[k]){
								lessthanequal = true;
								k=4;
							} else if (board[i][3-k] > leastValue[k]){
								k=4;
							} else if (k == 3 && (board[i][3-k] == leastValue[k])){ //ensures if two rows are equal, most clockwise one is returned as least row.
								lessthanequal = true;
							}
						}
						if(lessthanequal){
							leastArray = i;
							for (int j = 0; j<4;j++){
								leastValue[j] = board[i][3-j];
							}
						}
					}
				}
			}
			board[leastArray][3] = nextInt;
		} else{return node;}
		state nextState = new state(node,'L',board, !successmove);
		return nextState;
	}
	
	public static state moveRight(final state node) {//performs a right move as similar to the left move
		boolean successmove = false;
		int[][] board = new int[4][4];
		for (int i=0;i<4;i++) {	
			for (int j=0;j<4;j++){
					board[i][j] = node.board[i][j];
			}
		}
		int nextInt = node.nextNum;
		boolean[] rowshift = new boolean[4];
		for (int i = 3;i>=0;i--){
			boolean rowsucc = false;
			for (int j = 3;j>=0;j--){
				if (rowsucc) {
					board[i][j+1] = board[i][j]; //will never happen for first column, no need to check i,j-1 are valid coordinates
					
				} else if (!rowsucc){
					if (j!= 3  && (board[i][j] != 0)){ //ensures not checking value of non-existent column
						if ((board[i][j+1]) == 0){
							rowsucc = true;
							board[i][j+1] = board[i][j];
						} else if ((board[i][j+1] + board[i][j]) == 3  && (board[i][j] == 1 || board[i][j] == 2)){
							rowsucc = true;
							board[i][j+1] = 3;
						} else if ((board[i][j+1] == board[i][j]) && (board[i][j] != 1) && (board[i][j] != 2)) {
							board[i][j+1] = 2*board[i][j];
							rowsucc = true;
						}
					}
				}
				if (j == 0 && rowsucc){
					board[i][j] = 0;
					successmove = true;
					rowshift[i] = true;
					
				}
			}
				
		}
		
		if (successmove){
			int leastArray = -1;
			int[] leastValue = new int[4];
			for (int i = 3; i>=0; i--){
				if (rowshift[i]){
					if (leastArray == -1){
						leastArray = i;
						leastValue = board[i];
					} else {
						boolean lessthan = false;
						for (int k = 0; k<4;k++) {
							if(board[i][k] < leastValue[k]){
								lessthan = true;
								k=4;
							} else if (board[i][k] > leastValue[k]){
								k=4;
							} else if (k == 0 && (board[i][k] == leastValue[k])){ //ensures if two rows are equal, most clockwise one is returned as least row, in this case the highest one/first found.
								lessthan = true;
							}
						}
						if(lessthan){
							leastArray = i;
							leastValue = board[i];
						}
					}
				}
			}
			board[leastArray][0] = nextInt;
			
		} else{return node;}
		
		state nextState = new state(node,'R',board, !successmove);
		return nextState;
	}
	
	public static state moveDown(final state node) {//performs a down move as similar to the left move
		boolean successmove = false;
		int[][] board = new int[4][4];
		for (int i=0;i<4;i++) {	
			for (int j=0;j<4;j++){
					board[i][j] = node.board[i][j];
			}
		}
		int nextInt = node.nextNum;
		boolean[] colshift = new boolean[4];
		for (int j = 0;j<4; j++){
			boolean colsucc = false;
			for (int i = 3;i>=0;i--){
				if (colsucc) {
					board[i+1][j] = board[i][j]; //will never happen for first column, no need to check i,j-1 are valid coordinates
					
				} else if (!colsucc){
					if (i!= 3  && (board[i][j] != 0)){ //ensures not checking value of non-existent column
						if ((board[i+1][j]) == 0){
							colsucc = true;
							board[i+1][j] = board[i][j];
						} else if ((board[i+1][j] + board[i][j]) == 3  && (board[i][j] == 1 || board[i][j] == 2)){
							colsucc = true;
							board[i+1][j] = 3;
						} else if ((board[i+1][j] == board[i][j]) && (board[i][j] != 1) && (board[i][j] != 2)) {
							board[i+1][j] = 2*board[i][j];
							colsucc = true;
						}
					}
				}
				if (i == 0 && colsucc){
					board[i][j] = 0;
					successmove = true;
					colshift[j] = true;
					
				}
			}
				
		}
		
		if (successmove){
			int leastArray = -1;
			int[] leastValue = new int[4];
			for (int j = 0; j<4; j++){
				if (colshift[j]){
					if (leastArray == -1){
						leastArray = j;
						for (int m = 0; m<4;m++){
							leastValue[m] = board[m][j]; //extracts column
						}
					} else {
						boolean lessthanequal = false;
						for (int k = 0; k<4;k++) {
							if(board[k][j] < leastValue[k]){
								lessthanequal = true;
								k=4;
							} else if (board[k][j] > leastValue[k]){
								k=4;
							} else if (k == 3 && (board[k][j] == leastValue[k])){ //ensures if two columns are equal, most clockwise one is returned as least column, this would be the last found of any equal columns as we check left to right.
								lessthanequal = true;
							}
						}
						if(lessthanequal){
							leastArray = j;
							for (int m = 0; m<4;m++){
								leastValue[m] = board[m][j]; //extracts column
							}
						}
					}
				}
			}
			board[0][leastArray] = nextInt;
			
		} else{return node;}
		
		
		state nextState = new state(node,'D',board, !successmove);
		return nextState;
	}
	
	public static state moveUp(final state node) {//performs a move up, similair to the left move above
		boolean successmove = false;
		int[][] board = new int[4][4];
		for (int i=0;i<4;i++) {	
			for (int j=0;j<4;j++){
					board[i][j] = node.board[i][j];
			}
		}
		int nextInt = node.nextNum;
		boolean[] colshift = new boolean[4];
		for (int j = 0;j<4; j++){
			boolean colsucc = false;
			for (int i = 0;i<4;i++){
				if (colsucc) {
					board[i-1][j] = board[i][j]; //will never happen for first column, no need to check if valid coordinates
					
				} else if (!colsucc){
					if (i!= 0  && (board[i][j] != 0)){ //ensures not checking value of non-existent row
						if ((board[i-1][j]) == 0){
							colsucc = true;

							board[i-1][j] = board[i][j];
						} else if ((board[i-1][j] + board[i][j]) == 3  && (board[i][j] == 1 || board[i][j] == 2)){
							colsucc = true;
							board[i-1][j] = 3;
						} else if ((board[i-1][j] == board[i][j]) && (board[i][j] != 1) && (board[i][j] != 2)) {
							board[i-1][j] = 2*board[i][j];
							colsucc = true;
						}
					}
				}
				if (i == 3 && colsucc){
					board[i][j] = 0;
					successmove = true;
					colshift[j] = true;
					
				}
			}
				
		}
		
		if (successmove){
			int leastArray = -1;
			int[] leastValue = new int[4];
			for (int j = 0; j<4; j++){
				if (colshift[j]){
					if (leastArray == -1){
						leastArray = j;
						for (int m = 0; m<4;m++){
							leastValue[m] = board[m][j]; //extracts column
						}
					} else {
						boolean lessthan = false;
						for (int k = 0; k<4;k++) {
							if(board[k][3-j] < leastValue[k]){
								lessthan = true;
								k=4;
							} else if (board[k][3-j] > leastValue[k]){
								k=4;
							} else if (k == 3 && (board[k][3-j] == leastValue[k])){ //ensures if two columns are equal, most clockwise one is returned as least column, this would be the first found of any equal columns as we check left to right.
								lessthan = false;
							}
						}
						if(lessthan){
							leastArray = j;
							for (int m = 0; m<4;m++){
								leastValue[m] = board[m][j]; //extracts column
							}
						}
					}
				}
			}
			board[3][leastArray] = nextInt;
			
		} else{return node;}
		
		state nextState = new state(node,'U',board, !successmove);
		return nextState;
	}
	
	
	public static state doMM(state root, int ply){//performs a minimax like algorithm to depth ply to find a 'good' sequence of moves
		state best = root;
		state expa = root;
		
		while(expa != null && expa.getChildren() != null){//repeatedly loops until the same node is returned twice which should only happen if a terminal node is the only node left to expand
			state[] ret = miniMax(expa,best,ply);
			if (ret[0] == expa){
					expa = null;
				}else {
					expa = ret[0];
				}
			best = ret[1];//returns best terminal node found
		}
		return best;//returns 'best' found terminal node
	}
	
	public static state[] miniMax(state root, state best, int ply){//performs a minimax like algorithm
		int startDepth = root.depth;
		int endDepth = startDepth + ply;
		ArrayList<state> nodes = new ArrayList<state>();
		nodes.add(root);
		int i = 0;
		while(i<nodes.size()){
			state move = nodes.get(i);
			if (move != null){
				move.getChildren();
				if (move.children == null){//if terminal node: compare to current best found terminal
					if(move.getScore() > best.getScore()){
						best = move;
					}
					nodes.remove(move);//removes node from list once expanded

				} else{
					if((move.depth + 1) <= endDepth && move.depth >= startDepth){//adds nodes children that are less deep than that the starting depth + ply
						for (int j = 0;j<move.children.length;j++){
							if(!nodes.contains(move.children[j])){
								nodes.add(move.children[j]);
							}
						}
						nodes.remove(move);	
					} else {i++;}
				}
			} else {i++;}

		}
		nodes.removeAll(Collections.singleton(null));//removes all null states from the list
		Collections.sort(nodes);
		state[] ret = new state[2];
		if (!nodes.isEmpty()){
			ret[0] = nodes.get(0);
			nodes.clear();
		} else {
			nodes.clear();
			ret[0] = null;//returned if no non-terminals remain
		}
		ret[1] = best;
		return ret;
	}
}
