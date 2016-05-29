package Logic;
/*
 *  Represents a custom chess Piece called Scouts.
 *  Scouts can move three Squares vertically, but then only one Square horizontally
 *  for the next two moves. They can also jump over pieces.
 *  @author Kevin Gomez kkgomez2@illinois.edu kevinkgomez.com
 */
public class Scout extends Piece {

	public int movementCounter;
	
	public Scout(String color, Square square, ChessBoard board) {
		super(color, "Scout", square, board);
		movementCounter = 0;
	}

	public Scout(String color, int x, int y, ChessBoard board) {
		super(color, "Scout", x, y, board);
	}
	
	/* 
	 * Moves a Scout onto the Square at the corresponding coordinates.
	 * Checks to see if the movement is valid. If so...
	 * If there's an occupying enemy Piece at that square, it kills it before
	 * it takes that space.
	 */
	@Override
	public void moveTo(int destX, int destY){
		super.moveTo(destX, destY);
		if(this.hasMoved()) movementCounter++;
	}

	/*
	 * Checks whether the intended coordinates are
	 * a valid move for a Queen, and returns that value.
	 * @return the corresponding boolean.
	 */
	@Override
	public boolean validMove(int destX, int destY){
		if(movementCounter % 3 == 0){
			if(destX == x && (destY == y+3 || destY == y-3)){
				return true;
			}
		} else if((destX == x+1 || destX == x-1) && destY == y){
				return true;
			}
		return false;
		
	}

}
