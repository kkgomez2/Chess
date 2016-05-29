package Tests;
import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Logic.ChessBoard;
import Logic.Player;
import Logic.Rook;

/*
 *  Rook tests.
 *  @author Kevin Gomez kkgomez2@illinois.edu kevinkgomez.com
 *
 * 	Tests: offTheBoardFails(), jumpingOverPiecesFails(),
 * 		  improperMovementFails(), movement()
 */
public class RookTests {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	//from:
	//http://stackoverflow.com/questions/1119385/junit-test-for-system-out-println

	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}

	/*
	 * This test assures that this piece attempting to move off the board does not do so.
	 * and that the Square on the ChessBoard still contains the that piece.
	 */
	@Test
	public void offTheBoardFails() throws Exception{
		
		Player one = new Player("White");
		Player two = new Player("Black");
		ChessBoard ch = new ChessBoard(one, two, "debug");
		Rook test = new Rook("White", ch.getSquare(0, 0), ch);
		
		test.moveTo(0, 8);
		test.moveTo(0, -2);

		assertEquals("Invalid move to 0,8: Out of board bounds.\n"
					+ "Invalid move to 0,-2: Out of board bounds.\n", outContent.toString());
		
		assertEquals(test, ch.getPiece(0, 0));
	}

	/*
	 * This assures that pieces cannot jump over others.
	 */
	@Test
	public void jumpingOverPiecesFails() throws Exception{
		
		Player one = new Player("White");
		Player two = new Player("Black");
		ChessBoard ch = new ChessBoard(one, two, "debug");
		Rook test = new Rook("White", ch.getSquare(1, 1), ch);
		Rook test2 = new Rook("Black", ch.getSquare(1, 3), ch);
		Rook test3 = new Rook("White", ch.getSquare(3, 1), ch);
		Rook test4 = new Rook("Black", ch.getSquare(3, 3), ch);
		
		test.moveTo(1, 7);
		test2.moveTo(1, 0);
		test3.moveTo(0, 1);
		test2.moveTo(7, 3);

		//Test up, down, left, right
		assertEquals("White Rook's upwards path is not clear.\n"
					+ "Invalid move to 1,7: Rook cannot move there.\n"
					+ "Black Rook's downwards path is not clear.\n"
					+ "Invalid move to 1,0: Rook cannot move there.\n"
					+ "White Rook's left path is not clear.\n"
					+ "Invalid move to 0,1: Rook cannot move there.\n"
					+ "Black Rook's right path is not clear.\n"
					+ "Invalid move to 7,3: Rook cannot move there.\n", outContent.toString());
		
		assertEquals(test, ch.getPiece(1, 1));
		assertEquals(test2, ch.getPiece(1, 3));
		assertEquals(test3, ch.getPiece(3, 1));
	}

	/*
	 * This assures that improper movements do not succeed.
	 */
	@Test
	public void improperMovementFails() throws Exception{
		
		Player one = new Player("White");
		Player two = new Player("Black");
		ChessBoard ch = new ChessBoard(one, two, "debug");
		Rook test = new Rook("Black", ch.getSquare(1, 1), ch);
		Rook dummy = new Rook("Black", ch.getSquare(1, 3), ch);

		test.moveTo(4, 4); //Diagonal
		test.moveTo(5, 4); //Sort of diagonal
		test.moveTo(1, 3); //Kill a fellow Rook

		assertEquals("Invalid move to 4,4: Rook cannot move there.\n"
					+ "Invalid move to 5,4: Rook cannot move there.\n"
					+ "Invalid move to 1,3: Same color piece on that square. (Black).\n"
					, outContent.toString());

		assertEquals(test, ch.getPiece(1,1));
		assertEquals(dummy, ch.getPiece(1,3));
	}

	/*
	 * This tests all proper forms of movement.
	 */
	@Test
	public void movement() throws Exception{
		
		Player one = new Player("White");
		Player two = new Player("Black");
		ChessBoard ch = new ChessBoard(one, two, "debug");
		Rook test = new Rook("White", ch.getSquare(1, 1), ch);
		Rook dummy = new Rook("Black", ch.getSquare(1, 6), ch);
		Rook test2 = new Rook("White", ch.getSquare(1, 7), ch);
		Rook test3 = new Rook("Black", ch.getSquare(7, 1), ch);
		Rook test4 = new Rook("White", ch.getSquare(7, 7), ch);
		
		test.moveTo(1, 6);
		test2.moveTo(6, 7);
		test3.moveTo(2, 1);
		test4.moveTo(7, 0);

		//Test up+kill, right, left, down
		assertEquals("White Rook kills Black Rook.\n"
					+ "White Rook moves to 1,6.\n"
					+ "White Rook moves to 6,7.\n"
					+ "Black Rook moves to 2,1.\n"
					+ "White Rook moves to 7,0.\n", outContent.toString());
		
		assertEquals(test, ch.getPiece(1, 6));
		assertEquals(test2, ch.getPiece(6, 7));
		assertEquals(test3, ch.getPiece(2, 1));
		assertEquals(test4, ch.getPiece(7, 0));
	}

	@After
	public void cleanUpStreams() {
		System.setOut(null);
		System.setErr(null);
	}
}
