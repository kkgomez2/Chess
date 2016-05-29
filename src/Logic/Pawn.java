package Logic;
/*
 *  Represents a Pawn chess Piece.
 *  @author Kevin Gomez kkgomez2@illinois.edu kevinkgomez.com
 */
public class Pawn extends Piece{
	
	private boolean firstMove; //Pawns can do special things on their first move
	private int numMoves;

	public Pawn(String color, Square square, ChessBoard board) {
		super(color, "Pawn", square, board);
		firstMove = true;
		numMoves = 0;
	}
	
	public Pawn(String color, int x, int y, ChessBoard board) {
		super(color, "Pawn", x, y, board);
		firstMove = true;
		numMoves = 0;
	}

	/* 
	 * Moves a Pawn onto the Square at the corresponding coordinates.
	 * Checks to see if the movement is valid. If so...
	 * If there's an occupying enemy Piece at that square, it kills it before
	 * it takes that space.
	 */
	@Override
	public void moveTo(int destX, int destY){
		super.moveTo(destX, destY);
		if(this.moved) {
			firstMove = false;
			numMoves++;
		}
	}
	
	/*
	 * Intended for undo functionality
	 */
	public void undoMove(){
		if(numMoves > 0){
			numMoves--;
		}
		else {
			firstMove = true;
		}
	}
	
	/*
	 * Checks whether the intended coordinates are
	 * a valid move for a Pawn, and returns that value.
	 * @return the corresponding boolean.
	 */
	@Override
	public boolean validMove(int destX, int destY){

		int one, two;
		
		//Player 2's pieces move downwards. Else compare upwardsGo.
		if(color.equals(board.getPlayer(2).getColor())){
			one = -1;
			two = -2;
		} else {
			one = 1;
			two = 2;
		}

		//(Can only move diagonally if there's a piece to kill there)
		if(destX == x){
			if(destY == y+one){
				if(!squareAvailable(destX, destY)){
					//standard move 1 forward (CAN'T KILL)
					System.out.println("A pawn cannot move onto that occupied square at "
										+ destX + "," + destY + ".");
					return false;
				}
				return true;
				
			} else if(firstMove && destY == y+two){
				
				if(!squareAvailable(destX, destY)){
					//first move: move 2 forward (CAN'T KILL)
					System.out.println("A pawn cannot move onto that occupied square at "
							+ destX + "," + destY + ".");
					return false;
				}
				Piece possiblePiece = board.getPiece(destX, y+one);
				if(possiblePiece != null){
					System.out.println(getFullName() + " at "
								+ getX() + "," + getY() + " cannot jump over that "
								+ possiblePiece.getFullName() + " at "
								+ possiblePiece.getX() + "," + possiblePiece.getY() + ".");
					return false;
				}
				return true;
			}
		} else if(destX == x+1 || destX == x-1){
			//diagonal kill
			if(!squareAvailable(destX,destY) && destY == y+one){
				if(this.isAnEnemy(board.getPiece(destX, destY))){
					return true;
				}
			} 
		}
		return false;
	}

}
