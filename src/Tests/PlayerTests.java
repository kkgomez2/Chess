package Tests;
import static org.junit.Assert.*;

import org.junit.Test;

import Logic.ChessBoard;
import Logic.King;
import Logic.Pawn;
import Logic.Player;

public class PlayerTests {

	@Test
	public void playersHaveTheirStuffStandard() {
		Player one = new Player("White");
		Player two = new Player("Black");
		ChessBoard ch = new ChessBoard(one, two);

		assertEquals(one.getPieces().size(), 16);
		assertEquals(two.getPieces().size(), 16);
		
		ch.getPiece(3, 1).killSelf();
		assertEquals(one.getPieces().size(), 15);
		

	}
	@Test
	public void playersHaveTheirStuffDebug() {
		Player one = new Player("White");
		Player two = new Player("Black");
		ChessBoard ch = new ChessBoard(one, two, "debug");

		Pawn test1 = new Pawn("White", 3, 1, ch);
		King test2 = new King("White", 4, 1, ch);
		
		Pawn test3 = new Pawn("Black", 3, 6, ch);
		King test4 = new King("Black", 4, 6, ch);
		Pawn test5 = new Pawn("Black", 5, 6, ch);

		assertEquals(one.getPieces().size(), 2);
		assertEquals(two.getPieces().size(), 3);

		assertEquals(test2, one.getKing());
		assertEquals(test4, two.getKing());
		
		ch.getPiece(3, 1).killSelf();
		assertEquals(one.getPieces().size(), 1);
		

	}

}
