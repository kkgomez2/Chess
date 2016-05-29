package Logic;
import java.util.Vector;

/*
 * 	A ChessBoard is a two dimensional array of Square objects.
 *  @author Kevin Gomez kkgomez2@illinois.edu kevinkgomez.com
 */

public class ChessBoard {

	private final int X = 8;
	private final int Y = 8;
	private Square[][] board;
	private Player one;
	private Player two;
	boolean debug;
	
	/*
	 * Creates a ChessBoard of dimension determined by the
	 * instance variables and fills its 2d Square array with
	 * new Squares. Sets up a standard game of chess.
	 * There are no active players on this board.
	 */
	public ChessBoard(){
		this.board = new Square[X][Y];
		
		for(int i = 0 ; i < X ; i++){
			for(int j = 0 ; j < Y ; j++){
				this.board[i][j] = new Square(i,j);
			}
		}

		this.one = null;
		this.two = null;
		debug = false;
		setUpStandard();
	}
	
	/*
	 * Creates a ChessBoard of dimension determined by the
	 * instance variables and fills its 2d Square array with
	 * new Squares. Sets up a standard game of chess
	 * Sets up two players for the game.
	 * @param one: Player 1
	 * @param two: Player 2
	 */
	public ChessBoard(Player one, Player two){
		this.board = new Square[X][Y];
		
		for(int i = 0 ; i < X ; i++){
			for(int j = 0 ; j < Y ; j++){
				this.board[i][j] = new Square(i,j);
			}
		}
		
		this.one = one;
		this.two = two;
		
		setPlayers(one,two);
		
		debug = false;
		setUpStandard(one.getColor(), two.getColor());
	}
	
	/*
	 * Creates a debug ChessBoard, used for testing.
	 * without Players for expedited testing
	 */
	public ChessBoard(String test){
		this.board = new Square[X][Y];
		
		for(int i = 0 ; i < X ; i++){
			for(int j = 0 ; j < Y ; j++){
				this.board[i][j] = new Square(i,j);
			}
		}
		
		this.one = null;
		this.two = null;
		
		setPlayers(one,two);
		
		if(test.equals("debug") || test.equals("clear")){
			debug = true;
		}
	}
	
	/*
	 * Creates a debug ChessBoard, used for testing.
	 * with Players, in the case Players are needed for testing.
	 */
	public ChessBoard(Player one, Player two, String test){
		this.board = new Square[X][Y];
		
		for(int i = 0 ; i < X ; i++){
			for(int j = 0 ; j < Y ; j++){
				this.board[i][j] = new Square(i,j);
			}
		}

		this.one = one;
		this.two = two;
		
		setPlayers(one,two);
		
		if(test.equals("debug") || test.equals("clear")){
			debug = true;
		}

	}

	public int getX(){
		return X;
	}
	
	public int getY(){
		return Y;	
	}

	private void setPlayers(Player one, Player two){
		one.setOpponent(two);
		two.setOpponent(one);
		one.setBoard(this);
		two.setBoard(this);
	}
	
	public Player getPlayer(String num){
		if (num.equals("one") || num.equals("1")){
			return one;
		} else return two;
	}
	public Player getPlayer(int num){
		if (num == 1){
			return one;
		} else return two;
	}

	public Player getPlayerByColor(String color){
		
		if (one.getColor().toLowerCase().equals(color.toLowerCase())){
			return one;
		} else if (two.getColor().toLowerCase().equals(color.toLowerCase())){
			return two;
		} else {
			System.out.println("A player of color " + color + "is not on this board.");
			return null;
		}
	}
	
	public Player getOtherPlayer(String num){
		if (num.equals("one") || num.equals("1")){
			return two;
		} else return one;
	}
	
	public Player getOtherPlayer(int num){
		if (num == 1){
			return two;
		} else return one;
	}

	public Player getOtherPlayerByColor(String color){
		
		if (one.getColor().toLowerCase().equals(color.toLowerCase())){
			return two;
		} else if (two.getColor().toLowerCase().equals(color.toLowerCase())){
			return one;
		} else {
			System.out.println("A player of color " + color + "is not on this board.");
			return null;
		}
		
	}
	public King getOtherKing(String color){
		return getOtherPlayerByColor(color).getKing();
	}
	
	/*
	 * Determines whether the given x and y
	 * are within the bounds of the board
	 * @return: The corresponding boolean
	 */
	public boolean isValid(int x, int y) {
		return !(x >= X || y >= Y || x < 0 || y < 0);
	}

	public Square getSquare(int x, int y){
		if(!isValid(x, y)){
			return null;
		}
		
		return board[x][y];
	}

	public Piece getPiece(int x, int y){
		if(!isValid(x, y)){
			return null;
		}
		
		return board[x][y].getPiece();
	}

	public Square[][] getBoard(){
		
		return board;
	}

	/*
	 * Prints to console the the status of square's piece on the board
	 */
	public void identifyPiece(int x, int y){
		if(!isValid(x,y)){
			System.out.println("That's not a valid coordinate");
			return;
		}
		
		Piece p = this.getPiece(x, y);
		
		if(p == null){
			System.out.println(x + "," + y + " doesn't have a piece on it.");
		} else {
			System.out.println("The piece at "+ x + "," + y +" is a " + 
					p.getFullName() + ".");
			
		}
	}
	
	private void setPiece(Piece piece){
		if(!isValid(piece.getX(), piece.getY())){
			return;
		}
		board[piece.getX()][piece.getY()].setPiece(piece);
	}
	
	/*
	 * A public method for testing used to set pieces on the board
	 * Can only be used on a debug ChessBoard
	 */
	public void debugSetPiece(Piece piece){
		if (!debug){
			return;
		}
		
		setPiece(piece);
	}

	/*
	 * Sets up the chess board with
	 * the configuration for a standard chess board.
	 */
	private void setUpStandard(){
		
		//Set up Black Pieces starting with Pawns
		for(int i = 0 ; i < X ; i++){
			new Pawn("Black", board[i][6], this);
		}
		new Rook("Black", board[0][7], this);
		new Rook("Black", board[7][7], this);
		new Knight("Black", board[1][7], this);
		new Knight("Black", board[6][7], this);
		new Bishop("Black", board[2][7], this);
		new Bishop("Black", board[5][7], this);
		new Queen("Black", board[3][7], this);
		new King("Black", board[4][7], this);
		
		//Set up White Pieces starting with Pawns
		for(int i = 0 ; i < X ; i++){
			new Pawn("White", board[i][1], this);
		}
		new Rook("White", board[0][0], this);
		new Rook("White", board[7][0], this);
		new Knight("White", board[1][0], this);
		new Knight("White", board[6][0], this);
		new Bishop("White", board[2][0], this);
		new Bishop("White", board[5][0], this);
		new King("White", board[3][0], this);
		new Queen("White", board[4][0], this);
		
		return;
	}

	/*
	 * Sets up the chess board with
	 * the configuration for a standard chess board.
	 */
	private void setUpStandard(String p1Color, String p2Color){
		
		//Set up Player 1's Pieces starting with Pawns
		for(int i = 0 ; i < X ; i++){
			new Pawn(p1Color, board[i][1], this);
		}
		new Rook(p1Color, board[0][0], this);
		new Rook(p1Color, board[7][0], this);
		new Knight(p1Color, board[1][0], this);
		new Knight(p1Color, board[6][0], this);
		new Bishop(p1Color, board[2][0], this);
		new Bishop(p1Color, board[5][0], this);
		new Queen(p1Color, board[3][0], this);
		new King(p1Color, board[4][0], this);

		//Set up Player 2's Pieces starting with Pawns
		for(int i = 0 ; i < X ; i++){
			new Pawn(p2Color, board[i][6], this);
		}
		new Rook(p2Color, board[0][7], this);
		new Rook(p2Color, board[7][7], this);
		new Knight(p2Color, board[1][7], this);
		new Knight(p2Color, board[6][7], this);
		new Bishop(p2Color, board[2][7], this);
		new Bishop(p2Color, board[5][7], this);
		new Queen(p2Color, board[3][7], this);
		new King(p2Color, board[4][7], this);
		
		return;
	}
//STILL AT WORK.
	//start from top down
	public void printBoard(){
	
		for(int j = Y-1 ; j >= 0 ; j--){
			for(int i = 0 ; i < X ; i++){
				if(!board[i][j].isAvailable()){
					System.out.print(" " + board[i][j].getPiece().getColor().substring(0,1)
							+ board[i][j].getPiece().getName().substring(0,2) + " |");
					}
					else {
						System.out.print("     |");
					}
				if(i == X-1){
					System.out.print("\n");
				}
			}
		}
	}
}
