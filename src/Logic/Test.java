package Logic;
public class Test {
//Let's test Check/Checkmate
	public static void main(String[] args) {

		Player one = new Player("White");
		Player two = new Player("Black");
		ChessBoard ch = new ChessBoard(one, two, "debug");
		
		King blackKing = new King("Black", 0, 7, ch);
		King whiteKing = new King("White", 0, 5, ch);
		Rook whiteRook = new Rook("White", 2, 0, ch);
		
		ch.printBoard();
		
		//Should move whiteRook to 2,7 
		
		
		
		System.out.println(two.kingInCheck());
		
		whiteRook.moveTo(2, 7);

		ch.printBoard();
		System.out.println(two.kingInCheck());
		System.out.println(one.isAWinner());
		
		
/*
		Player one = new Player("White");
		Player two = new Player("Black");
		ChessBoard ch = new ChessBoard(one, two, "debug");
		King test = new King("Black", ch.getSquare(1, 1), ch);

		test.moveTo(2, 2); //up+right
		test.moveTo(3, 2); //right
		test.moveTo(4, 1); //down+right
		test.moveTo(4, 0); //down
		test.moveTo(4, 1); //up
		test.moveTo(3, 0); //down+left
		test.moveTo(2, 0); //left
		test.moveTo(2, 1); //up+left
		King dummy = new King("White", ch.getSquare(2, 2), ch);
		ch.printBoard();
		test.moveTo(2, 2); //Kill an enemy King

		ch.printBoard();*/
	}

		
}
