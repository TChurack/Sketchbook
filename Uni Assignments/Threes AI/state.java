


public class state implements Comparable<state> {

	public int[][] board;
	public state[] children;
	public char[] path;
	int nextNum;
	boolean validMove;
	boolean terminal;
	state par;
	char lastMove;
	int depth;
	boolean exit = false; //used to exit getChildren() to prevent rechecking

	public state (state parent, char move, int[][] inBoard, boolean isTerminal) {//initialises the game state
		board = inBoard;
		par = parent;
		validMove = (lastMove == 'L' || lastMove == 'R' || lastMove == 'U' || lastMove == 'D'); //will only be false for root.
		terminal = isTerminal;
		lastMove = move;
		depth = 0;
		if(parent != null){depth = parent.depth + 1;}
	}
	
	public char[] getPath(){
		/*used to recursively fetch the path (sequence of moves to reach this state)
		by returning the path of the previous game state with the addition of the last move*/
		if (par != null){
			par.getPath();
			path = new char[par.path.length + 1];
			for(int i = 0;i<par.path.length;i++){
				path[i] = par.path[i];
			}
			path[path.length-1] = lastMove;
		} else if (lastMove != '0'){
			path = new char[] {lastMove};
		} else{
			path = new char[0];
		}
		return path;
	}
	public state[] getChildren(){ // fills the array representing the possible game states for each more or null if this game state is a terminal
		if (exit){return children;}
		exit = true;
		if (!terminal && AI.nextNum[depth] != null) {
			nextNum = Integer.parseInt(AI.nextNum[depth]);
			children = new state[] {AI.moveUp(this), AI.moveDown(this), AI.moveRight(this), AI.moveLeft(this)};
			for (int i = 0; i < 4; i++) {
				if (children[i] == this){children[i] = null;}
			}
			if(children[0] == null && children[1] == null && children[2] == null && children[3] == null){children = null;}
		} else{
			children = null;
		}
		return children;
	}
	
	public int getScore(){ // returns the score as defined in the project assignment
		int score = 0;
		for (int i =0;i<4;i++){
			for (int j=0;j<4;j++){
				int tile = board[i][j];
				if(tile == 1 || tile == 2){
					score++;
				} else if(tile!= 0){
					score = (int) (score + Math.pow(3,Math.log(tile/3)/Math.log(2) + 1 )); // indirectly calculates old score + 3^(log_2(tile/3) +1), as there is no function to calculate log_2 directly
				}
			}
		}
		return score;
	}
	
	public int heurEquAdj(){ //gets number of pairs of equal adjacent tiles
		int count = 0;
		
		for (int i = 0;i<4;i++){
			for (int j = 0;j<3;j++){
				if (board[i][j] == board[i][j+1] && board[i][j] != 1 && board[i][j] != 2){count++;}
				if (board[j][i] == board[j+1][i] && board[i][j] != 1 && board[i][j] != 2){count++;}
			}
		}
		
		return count;
	}
	
	public int heurEmpty(){ // returns number of empty tiles
		int count = 0;
		for (int i = 0;i<4;i++){
			for (int j = 0; j<4;j++){
				if(board[i][j] == 0){count++;}
			}
		}
		return count;
	}
	
	public int heurMonoRowCol(){ //gets the number of rows and colums that are either always increasing or always decreasing (ie are monotonic)
		int count = 0;
		for (int i = 0;i<4;i++){
			if(board[i][0] < board[i][1] && board[i][1] < board[i][2] && board[i][2] < board[i][3]){
				count++;
			} else if (board[i][0] > board[i][1] && board[i][1] > board[i][2] && board[i][2] > board[i][3]){
				count++;
			}
			if(board[0][i] < board[1][i] && board[1][i] < board[2][i] && board[2][i] < board[3][i]){
				count++;
			} else if(board[0][i] > board[1][i] && board[1][i] > board[2][i] && board[2][i] > board[3][i]){
				count++;
			}
		}
		return count;	
	}
	
	public int heurTrap(){//counts the tiles trapped between two higher values either vertically, horizontally or twice if both
		int count = 0;
		for (int i =0;i<4;i++){
			for (int j = 1;j<3;j++){
				if(board[i][j] < board[i][j-1] && board[i][j] < board[i][j+1]){count++;}
				if(board[j][i] < board[j-1][i] && board[j][i] < board[j+1][i]){count++;}
			}
		}
		return count;
	}
	
	public int heurMonoChange(){//counts the number of times the row deviates from ascending to descending (if usually ascending) and vice versa
		int sum = 0;
		for (int i =0;i<4;i++){
			int countRow = 0;
			int countCol = 0;
			for (int j = 1; j<4;j++){
				if(board[i][j] > board[i][j-1]){countCol++;}
				if(board[j][i] > board[j-1][i]){countRow++;}
			}
			if(countCol>4){countCol = 4 - countCol;} // Ensures a value of less than two is returned for any one row or column, as the number of changes in one direction away from the main direction asc/desc can not be more than two or the other direction is actually the main direction
			if(countRow>4){countRow = 4 - countRow;}
			sum = sum + countCol + countRow;
		}
		return sum;
	}
	
	public int heurFlat(){ //Returns the total absolute difference of every tile from its neighbours
		int sum = 0;
		for (int i =0;i<4;i++){
			int sumRow = 0;
			int sumCol = 0;
			for (int j = 0; j<3;j++){
				sumRow = sumRow + Math.abs(board[i][j] - board[i][j+1]);
				sumCol = sumCol + Math.abs(board[j][i] - board[j+1][i]);
			}
			sum = sum + sumCol + sumRow;
		}
		return sum;
	}
	
	public int heurMaxTile(){ // returns the value of the largest tile
		int max = 0;
		for (int i = 0; i<4; i++){
			for (int j = 0; j<4; j++){
				if (board[i][j] > max){
					max = board[i][j];
				}
			}
		}
		return max;
	}
	
	public int heurMaxCorner(){//returns one if the largest tile is in a corner of the board
		int max = heurMaxTile();
		if (board[0][0] == max || board[3][3] == max || board[0][3] == max|| board[3][0] == max){return 1;}
		return 0;
	}
	
	public int heurAdjSucc(){ //gets number of pairs of adjacent successive tiles
		int count = 0;
		
		for (int i = 0;i<4;i++){
			for (int j = 0;j<3;j++){
				if (board[i][j] == 2*board[i][j+1] || board[i][j] == board[i][j+1]/2 ){count++;}
				if (board[j][i] == 2*board[j+1][i] || board[j][i] == board[j+1][i]/2){count++;}
			}
		}
		return count;
	}
	
	public int heur2ndMaxWall(){// returns one if the second largest tile is adjacent to a wall
		int max = heurMaxTile();
		int secMax = 0;
		boolean adj2Wall = false;
		for (int i = 0; i<4; i++){
			for (int j = 0; j<4; j++){
				if (board[i][j] > secMax && board[i][j] < max){
					secMax = board[i][j];
					if(i == 0 || i == 3 || j==0 || j == 3){
						adj2Wall = true;
					} else{adj2Wall = false;}
				}
			}
		}
		if (adj2Wall){return 1;}
		return 0;
	}
	
	public int eval(){ // returns a function evaluating the desirability of any possible non-terminal game state, or the score of a terminal.
		int score = getScore();
		//int h1 = heurEquAdj();
		this.getChildren();
		if (children == null){return score;}
		int h2 = heurEmpty();
		int h3 = heurMonoRowCol();
		//int h4 = heurTrap();
		//int h5 = heurMonoChange();
		int h6 = heurFlat();
		//int h7 = heurMaxTile();
		//int h8 = heurMaxCorner();
		int h9 = heurAdjSucc();
		//int h10 = heur2ndMaxWall();
		score = (3*((35*h2-h6)+h9) + 4*h3); //combination of heuristics
		return score;
	}

	public int compareTo(state node) { // used to sort a list of games so the most desirable can be chosen
		if(node == null){return -1000000000;}
		return node.eval() - this.eval();
	}

}
