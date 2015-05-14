@curr_player = "X"

def printBoard(board)
	puts ""
	for i in 0..2
		for j in 0..2
			print board[i][j]
			if j != 2
				print "|"
			else
				puts ""
			end
		end
		if i != 2
			puts "- - -"
		end
	end
end

def validMove(x_coord, y_coord, board)
	return (0..2) === x_coord && (0..2) === y_coord && board[x_coord][y_coord] == " "
end

def makeMove(player_char, x_coord, y_coord, board)
	board[x_coord][y_coord] = player_char
	return board
end

def checkRows(board)
	for i in 0..2
		if board[i][0] != " " && board[i][0] == board[i][1] && board[i][0] == board[i][2]
			return board[i][0]
		end
	end
	return false
end

def checkCols(board)
	for i in 0..2
		if board[0][i] != " " && board[0][i] == board[1][i] && board[0][i] == board[2][i]
			return board[0][i]
		end
	end
	return false
end

def checkDiags(board)
	centre = board[1][1]
	if centre != " "
		if centre == board[0][0] && centre == board[2][2]
			return centre
		end
		if centre == board[0][2] && centre == board[2][0]
			return centre
		end
	end
	return false
end

def checkForWinner(board)
	result = checkRows(board)
	if result
		puts "#{result} wins horizontally!"
		return result
	end
	
	result = checkCols(board)
	if result 
		puts "#{result} wins vertically!"
		return result
	end
	
	result = checkDiags(board)
	if result
		puts "#{result} wins diagonally!"
		return result
	end
	
	result = checkMovesPossible(board)
	if !result
		puts "No more valid moves, it's a tie!"
		return true
	end
	
	return false
end

def checkMovesPossible(board)
	for i in 0..2
		for j in 0..2
			if board[i][j] == " "
				return true
			end
		end
	end
	
	return false
end

def changePlayer
	if @curr_player == "X"
		@curr_player = "O"
	else
		@curr_player = "X"
	end
end

def resetBoard(board)
	board = [[" ", " ", " "],[" ", " ", " "],[" ", " ", " "]]
	return board
end

exit = false
board = resetBoard(board)
while !exit
	
	while !checkForWinner(board)
		puts ""
		puts "#{@curr_player} make a move:"
		puts ""
		print "row? (0,1,2): "
		x = gets.chomp.to_i
		print "column? (0,1,2): "
		y = gets.chomp.to_i
		puts ""
		if validMove(x,y,board)
			board = makeMove(@curr_player,x,y,board)
			changePlayer # Having this here is good, as it means for multiple games the last player to lose goes first
		else
			puts "Invalid move! Try again."
		end
		printBoard(board)
	end
	
	ans = " "
	while ans != "y" && ans != "n"
		puts "Play again? y/n"
		ans = gets.chomp.downcase
		if ans == "n"
			exit = true
		end
	end
	board = resetBoard(board)
end