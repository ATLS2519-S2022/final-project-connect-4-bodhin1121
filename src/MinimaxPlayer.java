
public class MinimaxPlayer implements Player {

	int ID;
	int opponent_id;
	int Cols;
	
    public String name() {
        return "Greedy";
    }
    
	public void init(int id, int msecPerMove, int rows, int cols)
	{
		ID = id;
		Cols = cols;
		opponent_id = 3 - id;
	}
	
	public void calcMove(Connect4Board board, int oppMoveCol, Arbitrator arb) throws TimeUpException
	{
		//Make sure there is room to make a move
		if(board.isFull())
		{
			throw new Error ("Complaint: The board is full!");
		}
		
		int maxDepth = 1;
		int move = 0;
		
		while(!arb.isTimeUp() && maxDepth <= board.numEmptyCells())
		{
			int bestScore = -1000;
			
			 for(int col = 0; col < 7; col++)
		        {
			        	if(board.isValidMove(col))
			        	{
				        	board.move(col,ID);
				        	
				        	int score = minimax(board, maxDepth - 1, false, arb);
				        	
				        	if(score > bestScore)
				        	{
				        		move = col;
				        		bestScore = score;
				        	}
				        	
				        	board.unmove(col,ID);
			        	}
		        }
			arb.setMove(move);
			maxDepth++;
		}
	}
	
	// Return the number of connect-4s that player #id has.
	public int calcScore(Connect4Board board, int id)
	{
		final int rows = board.numRows();
		final int cols = board.numCols();
		int score = 0;
		// Look for horizontal connect-4s.
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c <= cols - 4; c++) {
				if (board.get(r, c + 0) != id) continue;
				if (board.get(r, c + 1) != id) continue;
				if (board.get(r, c + 2) != id) continue;
				if (board.get(r, c + 3) != id) continue;
				score++;
			}
		}
		// Look for vertical connect-4s.
		for (int c = 0; c < cols; c++) {
			for (int r = 0; r <= rows - 4; r++) {
				if (board.get(r + 0, c) != id) continue;
				if (board.get(r + 1, c) != id) continue;
				if (board.get(r + 2, c) != id) continue;
				if (board.get(r + 3, c) != id) continue;
				score++;
			}
		}
		// Look for diagonal connect-4s.
		for (int c = 0; c <= cols - 4; c++) {
			for (int r = 0; r <= rows - 4; r++) {
				if (board.get(r + 0, c + 0) != id) continue;
				if (board.get(r + 1, c + 1) != id) continue;
				if (board.get(r + 2, c + 2) != id) continue;
				if (board.get(r + 3, c + 3) != id) continue;
				score++;
			}
		}
		for (int c = 0; c <= cols - 4; c++) {
			for (int r = rows - 1; r >= 4 - 1; r--) {
				if (board.get(r - 0, c + 0) != id) continue;
				if (board.get(r - 1, c + 1) != id) continue;
				if (board.get(r - 2, c + 2) != id) continue;
				if (board.get(r - 3, c + 3) != id) continue;
				score++;
			}
		}
		return score;
	}
	
	public int minimax(Connect4Board board, int depth, boolean isMaximizing, Arbitrator arb)
	{
		//If depth = 0 or there's no moves left or time is up return the heuristic value of the node
		if(depth == 0 || board.numEmptyCells() == 0 || arb.isTimeUp())
		{
			return calcScore(board, ID) - calcScore(board, opponent_id);
		}
		
		//If isMaximixing then:
		//best score = -1000
		//for each possible next move do
		//board.move(...)
		//bestScore = max(bestScore, miniMax(child, depth - 1, FALSE)
		//board.unmove(...)
		//return bestScore
		
		
		if(isMaximizing == true)
		{
			int bestScore = -1000;
			
			 for(int col = 0; col < 7; col++)
		        {
			        	if(board.isValidMove(col))
			        	{
				        	board.move(col,ID);
				        
				        	bestScore = Math.max(bestScore, minimax(board, depth - 1, false, arb));
				        	board.unmove(col,ID);
			        	}
		        }
			return bestScore;
		}
		
		
		//else /* minimizing player */
		//best score = 1000
		//for each possible next move do
		//board.move(...)
		//bestScore = min(bestScore, miniMax(child, depth - 1, FALSE)
		//board.unmove(...)
		//return bestScore
		
		else
		{
			int bestScore = 1000;
			
			 for(int col = 0; col < 7; col++)
		        {
			        	if(board.isValidMove(col))
			        	{
				        	board.move(col,opponent_id);
				        
				        	bestScore = Math.min(bestScore, minimax(board, depth - 1, true, arb));
				        	board.unmove(col,opponent_id);
			        	}
		        }
			return bestScore;
		}
		
		
	}
	
	
}
