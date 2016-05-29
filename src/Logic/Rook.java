package Logic;
/*
 *  Represents a Rook chess Piece.
 *  @author Kevin Gomez kkgomez2@illinois.edu kevinkgomez.com
 */
public class Rook extends Piece{

	public Rook(String color, Square square, ChessBoard board) {
		super(color, "Rook", square, board);
	}

	public Rook(String color, int x, int y, ChessBoard board) {
		super(color, "Rook", x, y, board);
	}
	
	/*
	 * Checks whether the intended coordinates are
	 * a valid move for a Rook, and returns that value.
	 * @return the corresponding boolean.
	 */
	@Override
	public boolean validMove(int destX, int destY){
		if(destX == x){
			return upPathClear(destY);
		} else if (destY == y){
			return sidePathClear(destX);
		}
		return false;
	}
}
