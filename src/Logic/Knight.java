package Logic;
/*
 *  Represents a Knight chess Piece.
 *  @author Kevin Gomez kkgomez2@illinois.edu kevinkgomez.com
 */
public class Knight extends Piece{

	public Knight(String color, Square square, ChessBoard board) {
		super(color, "Knight", square, board);
	}

	public Knight(String color, int x, int y, ChessBoard board) {
		super(color, "Knight", x, y, board);
	}
	
	/*
	 * Checks whether the intended coordinates are
	 * a valid move for a Knight, and returns that value.
	 * @return the corresponding boolean.
	 */
	@Override
	public boolean validMove(int destX, int destY){
		if(Math.abs(destY - y) == 2){
			if(Math.abs(destX - x) == 1){
				return true;
			}
		} else if (Math.abs(destY - y) == 1){
			if(Math.abs(destX - x) == 2){
				return true;
			}
		}
		return false;
	}
}
