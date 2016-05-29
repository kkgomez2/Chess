package Logic;
/*
 *  Represents a Queen chess Piece.
 *  @author Kevin Gomez kkgomez2@illinois.edu kevinkgomez.com
 */
public class Queen extends Piece{

	public Queen(String color, Square square, ChessBoard board) {
		super(color, "Queen", square, board);
	}

	public Queen(String color, int x, int y, ChessBoard board) {
		super(color, "Queen", x, y, board);
	}
	
	/*
	 * Checks whether the intended coordinates are
	 * a valid move for a Queen, and returns that value.
	 * @return the corresponding boolean.
	 */
	@Override
	public boolean validMove(int destX, int destY){
		//Diagonal test
		int equalDistance = Math.abs(destX - x);
		if(Math.abs(destY - y) == equalDistance){
			return diagonalPathClear(destX, destY);
		} else {
			//Side/Up/Down test
			if(destX == x){
				return upPathClear(destY);
			} else if (destY == y){
				return sidePathClear(destX);
			}
			//The move is neither diagonal nor + shaped.
			return false;
		}
	}
}
