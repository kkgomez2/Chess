package Logic;
/*
 *  Represents a Bishop chess Piece.
 *  @author Kevin Gomez kkgomez2@illinois.edu kevinkgomez.com
 */
public class Bishop extends Piece{

	public Bishop(String color, Square square, ChessBoard board) {
		super(color, "Bishop", square, board);
	}

	public Bishop(String color, int x, int y, ChessBoard board) {
		super(color, "Bishop", x, y, board);
	}
	
	/*
	 * Checks whether the intended coordinates are
	 * a valid move for a Bishop, and returns that value.
	 * @return the corresponding boolean.
	 */
	@Override
	public boolean validMove(int destX, int destY){
		int equalDistance = Math.abs(destX - x);
		if(Math.abs(destY - y) != equalDistance){
			return false;
		}
		return diagonalPathClear(destX, destY);
	}
}
