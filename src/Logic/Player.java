package Logic;
import java.util.Vector;

/*
 *  A Player representing one of two opposing chess colors. 
 *  They can win or not.
 *  @author Kevin Gomez kkgomez2@illinois.edu kevinkgomez.com
 *
 */
public class Player {
	
	private String color;
	private boolean winner;
	private Player opponent;
	private ChessBoard board;
	private Vector<Piece> pieces;
	private King playerKing;
	private boolean kingInCheck;
	private boolean kingInCheckmate;
	
	public Player(String color){
		this.color = color;
		winner = false;
		pieces = new Vector<Piece>();
		playerKing = null;
	}
	
	/*
	 * Various 'get' methods for Player's private instance variables
	 * and their respective set methods
	 */
	public String getColor(){
		return color;
	}

	public boolean isAWinner(){
		return winner;
	}

	public Player getOpponent(){
		return opponent;
	}
	
	public boolean setOpponent(Player other){
		if(this == other){
			return false;
		}
		else {
			opponent = other;
			return true;
		}
	}
	
	public void setBoard(ChessBoard ch){
		board = ch;
	}
	
	public Vector<Piece> getPieces(){
		return pieces;
	}

	/*
	 * Adds a piece to the pieces vector
	 */
	public void addPiece(Piece p){
		pieces.add(p);
		if(p.getName().equals("King")){
			playerKing = (King) p;
		}
	}

	/*
	 * Removes a piece to the pieces vector
	 */
	public void removePiece(Piece p){
		pieces.remove(p);
	}

	public King getKing(){
		return playerKing;
	}
	
	public void setKing(King k){
		playerKing = k;
	}

	public boolean kingInCheck(){
		return kingInCheck;
	}
	
	public boolean kingInCheckmate(){
		return kingInCheckmate;
	}

	//Checks if own king is in check.
	//Intended to be called after every turn.
	//For loop checks:
	//	"If this piece can move to the our King's location
	//	as a valid movement, and the square is OK to move to.
	//	We have our king in check. :("
	public void checkCheck(){
		
		int kx = playerKing.getX();
		int ky = playerKing.getY();

		Vector<Piece> otherPieces = opponent.getPieces();
		int size = otherPieces.size();
		
		for(int i = 0 ; i < size ; i++){
			if(otherPieces.elementAt(i).destinationIsValidForCheck(kx,ky)
					&& otherPieces.elementAt(i).squareIsMovableForCheck(board.getSquare(kx,ky))){
				kingInCheck = true;
				System.out.println(color + " in check by " + otherPieces.elementAt(i).getFullName());
			}
		}
		if(kingInCheck){
			checkCheckmate();
		}
		else {
			kingInCheck = false;
		}
	}
	
	//Only check for checkmate if the player's king is in check
	//AKA kingInCheck == true
	//Check own king's possible x and y destinations
	//See if the opponent's pieces cover all of these destinations
	//This is expensive.
	//potentially O(9*n) time. We should only run this when king is in check.
	public void checkCheckmate(){
		
		if (!kingInCheck){
			return;
		}
				
		Vector<Integer> xCoords = new Vector<Integer>();
		Vector<Integer> yCoords = new Vector<Integer>();

		int x = playerKing.getX();
		int y = playerKing.getY();
		
		//Iterate through x-1 -> y-1, y, y+1
		//				   x  -> y-1, y, y+1
		//                x+1 -> y-1, y, y+1
		//If the otherKing can move there,
		//add those coordinates to Coords vector
		//This loop checks all possible King destinations
		for(int i = x-1; i < x+2 ; i++){
			for(int j = y-1 ; j < y+2 ; j++){
				if(playerKing.destinationIsValidForCheck(i,j)
						&& playerKing.squareIsMovableForCheck(board.getSquare(i,j))){
					xCoords.add(i);
					yCoords.add(j);
				}
			}
		}
		
		Vector<Piece> otherPieces = opponent.getPieces();
		int size = otherPieces.size();
		
		Boolean[] truthArray = new Boolean[size];
		
		for(int i = 0 ; i < size ; i++){
			for(int j = 0 ; j < xCoords.size() ; j++){
				Piece test = otherPieces.elementAt(i);
				if(test.destinationIsValidForCheck(xCoords.elementAt(j), yCoords.elementAt(j))
						&& test.squareIsMovableForCheck(board.getSquare(xCoords.elementAt(j), yCoords.elementAt(j)))){
					truthArray[i] = true;
				}
			}
		}
		
		boolean checkMate = true;
		for(int i = 0 ; i < size ; i++){
			checkMate = checkMate && truthArray[i];
		}
		
		if(checkMate) {
			kingInCheckmate = true;

			System.out.println(color + " in checkmate!");
			//Might as well crown opponent the winner
			//Though it might be more sane to put this in the Game loop
			opponent.wins();
		}
		return;
	}
	
	public void wins(){
		winner = true;
		System.out.println("Player " + color + " wins!");
	}
	


}
