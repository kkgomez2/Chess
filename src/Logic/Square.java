package Logic;

/*
 * A Square that can hold a Piece and used to build a ChessBoard
 *  @author Kevin Gomez kkgomez2@illinois.edu kevinkgomez.com
 */
public class Square {
	
	private Piece piece;
	private int x;
	private int y;
	
	public Square(int x, int y){
		this.x = x;
		this.y = y;
		piece = null;
	}
	
	public Square(Piece piece, int x, int y){
		this.piece = piece;
		this.x = x;
		this.y = y;
	}
	
	public Piece getPiece(){
		return this.piece;
	}
	
	public boolean setPiece(Piece piece){
		if(this.piece == null){
			this.piece = piece;
			return true;
		} 
		else {
			System.out.println("Square already has a piece and it's " + this.getPiece().getFullName() + " at "
								+ x + "," + y);
			return false;
		}
	}
	
	public void clearPiece(){
		this.piece = null;
	}
	
	public boolean isAvailable(){
		if(this.getPiece() == null){
			return true;
		}
		else return false;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
}
