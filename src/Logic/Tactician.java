package Logic;
/*
 *  Represents a custom chess Piece called Tactician.
 *  Tacticians move in one unit diagonal movements.
 *  @author Kevin Gomez kkgomez2@illinois.edu kevinkgomez.com
 */
public class Tactician extends Piece {

	public Tactician(String color, Square square, ChessBoard board) {
		super(color, "Tactician", square, board);
	}

	public Tactician(String color, int x, int y, ChessBoard board) {
		super(color, "Tactician", x, y, board);
	}
	
	/*
	 * Checks whether the intended coordinates are
	 * a valid move for a Tactician, and returns that value.
	 * @return the corresponding boolean.
	 */
	@Override
	public boolean validMove(int destX, int destY){
		if((destX == x + 1 || destX == x - 1) &&
				(destY == y + 1 || destY == y - 1)){
			return true;
		} else return false;
	}
}
