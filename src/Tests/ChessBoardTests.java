package Tests;
import static org.junit.Assert.*;

import org.junit.Test;

import Logic.ChessBoard;
import Logic.Pawn;
import Logic.Player;

public class ChessBoardTests {

	@Test
	public void correctAttributes() {
		Player one = new Player("White");
		Player two = new Player("Black");
		ChessBoard ch = new ChessBoard(one, two);

		assertEquals(ch.getX(), 8);
		assertEquals(ch.getY(), 8);
		assertEquals(ch.getPlayer(1), one);
		assertEquals(ch.getPlayer(2), two);
		assertEquals(ch.isValid(44,2), false);
		
		Pawn test = new Pawn("White", 3, 3, ch);
		ch.debugSetPiece(test);
		
		assertEquals(test, ch.getPiece(3, 3));		
		
	}

}
