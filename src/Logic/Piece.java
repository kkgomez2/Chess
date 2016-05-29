package Logic;

/*
 * Represents a potential Piece in chess, extend from this
 *  @author Kevin Gomez kkgomez2@illinois.edu kevinkgomez.com
 */
//TODO: Rework so that there are less dependencies.
public class Piece {

	private String name; 
	protected String color;
	protected ChessBoard board; 
	protected int x;
	protected int y;
	protected boolean alive;
	protected boolean moved;
	
	/*
	 * Piece constructor.
	 * @param name: The kind of Piece i.e. "king", "pawn".
	 * @param color: The color of this Piece, either "black" or "white".
	 * @param x: The Piece's x position.
	 * @param y: The Piece's y position.
	 */
	public Piece(String color, String name, Square square, ChessBoard board){
		this(color, name, square.getX(), square.getY(), board);
	}	
	
	public Piece(String color, String name, int x, int y, ChessBoard board){
		this.name = name;
		this.color = color;
		this.board = board;
		this.x = x;
		this.y = y;
		alive = true;
		moved = false;

		if(!board.getSquare(x, y).setPiece(this)){
			this.x = -99;
			this.y = -99;
		}
		
		board.getPlayerByColor(color).addPiece(this);
	}

	public String getName(){
		return name;
	}
	
	public String getColor(){
		return color;
	}

	public String getFullName(){
		return color + " " + name;
	}
	
	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}
	
	public String getCoordinates(){
		return x + "," + y;
	}
	public Square getSquare(){
		return board.getSquare(x, y);
	}

	public boolean isAlive(){
		return alive;
	}
	
	/*
	 * Returns whether a piece (that has just moved), did so successfully
	 * If so, reset it to false, and return that it has indeed moved. 
	 */
	public boolean hasMoved(){
		if (moved){
			moved = false;
			return true;
		}
		else return false;
	}

	/* 
	 * Moves a piece onto the Square at the corresponding coordinates.
	 * Checks to see if the movement is valid. If so...
	 * If there's an occupying enemy Piece at that square, it kills it before
	 * it takes that space.
	 */
	public void moveTo(int destX, int destY){

		moved = false; 
		
		if(!destinationIsValid(destX, destY)){
			return;
		}
		
		Square destSquare = board.getSquare(destX, destY);
		
		if(!squareIsMovable(destSquare)){
			return;
		}
		
		moveOnto(destSquare);

		moved = true;
		
		if(board.getOtherKing(color) != null){
			board.getOtherPlayerByColor(color).checkCheck();
		}
	}

	protected boolean destinationIsValid(int destX, int destY){
		
		if(destX == x && destY == y){
			System.out.println("Invalid move to " + destX + "," + destY +
								": " + getName() +" is already on " + destX + "," + destY + ".");
			return false;
		} else if(!validOnBoard(destX,destY)){
			System.out.println("Invalid move to " + destX + "," + destY + ": Out of board bounds.");
			return false;
		} else if(!validMove(destX, destY)){
			System.out.println("Invalid move to " + destX + "," + destY + ": " 
					+ getName() +" cannot move there.");
			return false;
		}
		else return true;
		
	}
	
	//Intended to be called by the checkCheck/checkCheckmate calls.
	protected boolean destinationIsValidForCheck(int destX, int destY){
		
		if(destX == x && destY == y){
			return false;
		} else if(!validOnBoard(destX,destY)){
			return false;
		} else if(!validMove(destX, destY)){
			return false;
		}
		else return true;
		
	}
	
	//TODO: MAKE INTO AN INSTANCE OF SQUARE.
	//		ex. if(!thatSquare.isMovable()) is more readable than
	//			if(!squareIsMovable(thatSquare);
	/*
	 * Helper method for above.
	 * Decides whether the piece can move onto the empty square
	 * or if occupied, kill its occupant
	 * @return: whether the Piece can move freely
	 * 			to the destination Square or kill its occupant
	 */
	protected boolean squareIsMovable(Square thatSquare){
		
		int destX = thatSquare.getX();
		int destY = thatSquare.getY();
		
		if(!squareAvailable(destX,destY)){
			//If the occupying piece at the destination square
			//is an enemy, kill it before taking that square
			Piece other = thatSquare.getPiece();
			if(!other.getColor().equals(color)){
				this.kills(other);
				return true;
			} else {
				System.out.println("Invalid move to " + destX + "," + destY
									+ ": Same color piece on that square. "
									+ "(" + other.getColor() + ")." );
				return false;
			}
		}
		
		return true;
	}
	
	protected boolean squareIsMovableForCheck(Square thatSquare){
		
		int destX = thatSquare.getX();
		int destY = thatSquare.getY();
		
		if(!squareAvailable(destX,destY)){
			//If the occupying piece at the destination square
			//is an enemy, kill it before taking that square
			Piece other = thatSquare.getPiece();
			if(!other.getColor().equals(color)){
				return true;
			} else {
				return false;
			}
		}
		
		return true;
	}
	
	protected void moveOnto(Square thatSquare){

		int destX = thatSquare.getX();
		int destY = thatSquare.getY();
		
		//Take the Square that this Piece stands on and clear it.
		//Set this Piece's new Square to be the moveTo destination's Square.
		//Set x and y accordingly.
		
		board.getSquare(x, y).clearPiece();
		board.getSquare(destX, destY).setPiece(this);
		
		x = destX;
		y = destY;
		
		System.out.println(getFullName() + " moves to " + destX + "," + destY + ".");
		moved = true;
	}
	
	/*
	 * Protected method that is called only when one piece takes another in movement.
	 * Sets the other Piece's alive value to false, clears the Square it was on,
	 * clears the Piece's reference to the Square,
	 * and sends the Piece to limbo (-99,-99).
	 * If a king is killed, the other player is deemed the winner.
	 */
	protected void kills(Piece other){
		
		System.out.println(getFullName() + " kills " + other.getFullName() + ".");
		other.alive = false;
		board.getSquare(other.x, other.y).clearPiece();
		other.x = -99;
		other.y = -99;
		
		if(other.getName().equals("King")){
			if(other.getColor().equals(board.getPlayer(1).getColor()))
				board.getPlayer(2).wins();
			else board.getPlayer(1).wins();
		}
		
		board.getPlayerByColor(other.color).removePiece(other);
		
	}
	
	/*
	 * Used mainly in debugging. This does what it says.
	 */
	public void killSelf(){
		this.kills(this);
	}
	
	protected boolean validMove(int a, int b){
		return true;
	}
	
	protected boolean isAnEnemy(Piece other){
		return !this.color.equals(other.color);
	}
	
	protected boolean validOnBoard(int a, int b) {
		return this.board.isValid(a,b);
	}

	protected boolean squareAvailable(int a, int b) {
		return this.board.getSquare(a,b).isAvailable();
	}
	
	/*
	 * Assures that a diagonal path is either clear or not and reports the value
	 * @param dest_x: Intended x destination
	 * @param dest_y: Intended y destination
	 * @return: A boolean corresponding to clear diagonal path being present.
	 */
	protected boolean diagonalPathClear(int dest_x, int dest_y){
		int distance = Math.abs(dest_x - x);
		String coord;
		int testX, testY;
		if(dest_x > x){
			if(dest_y > y){
				//Right + Up
				coord = "up and right";
				for(int i = 1; i < distance ; i++){
					testX = x+i;
					testY = y+i;
					if(!diagonalPathAvailable(coord, testX, testY)){
						return false;
					}
					
				}
			} else {
				//Right + Down
				coord = "down and right";
				for(int i = 1; i < distance ; i++){
					testX = x+i;
					testY = y-i;
					if(!diagonalPathAvailable(coord, testX, testY)){
						return false;
					}
				}
			}
		} else {
			if(dest_y > y){
				//Left + Up
				coord = "up and left";
				for(int i = 1; i < distance ; i++){
					testX = x-i;
					testY = y+i;
					if(!diagonalPathAvailable(coord, testX, testY)){
						return false;
					}
				}
			} else {
				//Left + Down
				coord = "down and left";
				for(int i = 1; i < distance ; i++){
					testX = x-i;
					testY = y-i;
					if(!diagonalPathAvailable(coord, testX, testY)){
						return false;
					}
				}
			}
		}
		return true;
	}

	protected boolean diagonalPathAvailable(String coord, int testX, int testY) {
		if(!squareAvailable(testX,testY)){
			System.out.println("There's a " + board.getPiece(testX, testY).getColor() + " " + 
					 board.getPiece(testX, testY).getName() + " in the way.");
			System.out.println(getColor() + " " + getName()
								+ "'s " + coord + " diagonal path is not clear.");
			return false;
		}
		else return true;
	}

	/*
	 * Assumes it's called on a not-same destination.
	 * Indiscriminate of color 
	 * (i.e. won't check to see if the piece is going the right way)
	 * Checks if the adjacent side path is clear or not.
	 */
	protected boolean sidePathClear(int dest_x){
		if(dest_x < x){
			for(int i = x-1 ; i > dest_x ; i--){
				if(!squareAvailable(i,y)){
					System.out.println(getFullName() + "'s left path is not clear.");
					return false;
				}
			}
		} else {
			for(int i = x+1 ; i < dest_x ; i++){
				if(!squareAvailable(i,y)){
					System.out.println(getFullName() + "'s right path is not clear.");
					return false;
				}
			}
		}
		return true;
	}

	/*
	 * Assumes it's called on a not-same destination. 
	 * Indiscriminate of color 
	 * (i.e. won't check to see if the piece is going the right way)
	 * Checks if the adjacent upwards path is clear or not.
	 */
	protected boolean upPathClear(int dest_y){
		if(dest_y > y){
			for(int j = y+1 ; j < dest_y ; j++){
				if(!squareAvailable(x,j)){
					System.out.println(getFullName() + "'s upwards path is not clear.");
					return false;
				}
			}
		} else {
			for(int j = y-1 ; j > dest_y ; j--){
				if(!squareAvailable(x,j)){
					System.out.println(getFullName() + "'s downwards path is not clear.");
					return false;
				}
			}
		}
		return true;
	}
	
}
