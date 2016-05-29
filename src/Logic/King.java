package Logic;
/*
 *  Represents a King chess Piece.
 *  @author Kevin Gomez kkgomez2@illinois.edu kevinkgomez.com
 */
public class King extends Piece{

	public King(String color, Square square, ChessBoard ch) {
		super(color, "King", square, ch);
		board.getPlayerByColor(color).setKing(this);
	}

	public King(String color, int x, int y, ChessBoard ch) {
		super(color, "King", x, y, ch);
		board.getPlayerByColor(color).setKing(this);
	}
	
	/*
	 * Checks whether the intended coordinates are
	 * a valid move for a King, and returns that value.
	 * @return the corresponding boolean.
	 */
	
	public boolean validMove(int destX, int destY){
		if(destX == x || destX == x+1 || destX == x-1){
			if(destY == y+1 || destY == y-1 || destY == y){
				return true;
			}
		} 
		return false;
	}

}
