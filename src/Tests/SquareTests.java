package Tests;
import static org.junit.Assert.*;

import org.junit.Test;

import Logic.ChessBoard;
import Logic.Pawn;
import Logic.Player;

public class SquareTests {

	@Test
	public void correctAttributes() {
		Player one = new Player("Black");
		Player two = new Player("White");
		ChessBoard ch = new ChessBoard(one, two, "debug");
		
		assertEquals(ch.getSquare(4, 5).getX(), 4);
		assertEquals(ch.getSquare(3, 6).getY(), 6);
		assertEquals(ch.getSquare(2, 2).getPiece(), null);
		
		Pawn test = new Pawn("Black", 2, 2, ch);
		
		assertEquals(ch.getSquare(2, 2).getPiece(), test);
		
	}

	@Test
	public void correctBehavior() {
		Player one = new Player("White");
		Player two = new Player("Black");
		ChessBoard ch = new ChessBoard(one, two, "debug");

		Pawn test = new Pawn("Black", 2, 2, ch);
		Pawn dummy = new Pawn("White", 1, 1, ch);
		
		test.moveTo(1, 1);

		assertEquals(ch.getSquare(2, 2).getPiece(), null);
		assertEquals(ch.getSquare(1, 1).getPiece(), test);
		
	}
	
	//What happens when I try to set two Pieces on the same Square?
	@Test
	public void twoOnSquare() {

		Player one = new Player("Black");
		Player two = new Player("White");
		ChessBoard ch = new ChessBoard(one, two, "debug");

		Pawn test = new Pawn("Black", 2, 2, ch);
		Pawn test2 = new Pawn("White", 2, 2, ch);
		
		assertEquals(ch.getPiece(2, 2), test);
		assertEquals(test2.getX(), -99);
		assertEquals(test2.getSquare(), null);
		
	}

}
