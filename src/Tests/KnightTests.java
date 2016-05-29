package Tests;
import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Logic.ChessBoard;
import Logic.Knight;
import Logic.Player;

/*
 * 	King tests.
 *  @author Kevin Gomez kkgomez2@illinois.edu kevinkgomez.com
 *
 * 	Tests: offTheBoardFails(), jumpingOverPieces(),
 * 		  improperMovementFails(), movement()
 */
public class KnightTests {

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
		Knight test = new Knight("Black", ch.getSquare(0, 0), ch);
		Knight test2 = new Knight("White", ch.getSquare(7, 7), ch);

		test.moveTo(-2, -1);
		test2.moveTo(8, 9);
		
		assertEquals("Invalid move to -2,-1: Out of board bounds.\n"
				+ "Invalid move to 8,9: Out of board bounds.\n", outContent.toString());

		assertEquals(test, ch.getPiece(0, 0));
		assertEquals(test2, ch.getPiece(7, 7));
	}

	/*
	 * This assures that Knights succeed in jumping over others.
	 */
	@Test
	public void jumpingOverPieces() throws Exception{
		Player one = new Player("White");
		Player two = new Player("Black");
		ChessBoard ch = new ChessBoard(one, two, "debug");
		Knight test = new Knight("Black", ch.getSquare(3, 3), ch);
		Knight dummy1 = new Knight("White", ch.getSquare(4, 3), ch);
		Knight dummy2 = new Knight("White", ch.getSquare(4, 4), ch);
		Knight dummy3 = new Knight("White", ch.getSquare(3, 4), ch);

		test.moveTo(5, 4);
		test.moveTo(3, 3);
		test.moveTo(4, 5);

		assertEquals("Black Knight moves to 5,4.\n"
				+ "Black Knight moves to 3,3.\n"
				+ "Black Knight moves to 4,5.\n", outContent.toString());

		assertEquals(test, ch.getPiece(4, 5));
		assertEquals(dummy1, ch.getPiece(4, 3));
		assertEquals(dummy2, ch.getPiece(4, 4));
		assertEquals(dummy3, ch.getPiece(3, 4));
	}

	/*
	 * This assures that improper movements do not succeed.
	 */
	@Test
	public void improperMovementFails() throws Exception{
		
		Player one = new Player("White");
		Player two = new Player("Black");
		ChessBoard ch = new ChessBoard(one, two, "debug");
		Knight test = new Knight("Black", ch.getSquare(3, 3), ch);
		Knight dummy1 = new Knight("Black", ch.getSquare(4, 5), ch);
		Knight dummy2 = new Knight("White", ch.getSquare(4, 4), ch);
		Knight dummy3 = new Knight("White", ch.getSquare(3, 4), ch);

		test.moveTo(4, 5); //Kill a fellow Knight
		test.moveTo(3, 4); //Move straight forward
		test.moveTo(6, 6); //Move diagonally + more than one unit

		assertEquals("Invalid move to 4,5: Same color piece on that square. (Black).\n"
				+ "Invalid move to 3,4: Knight cannot move there.\n"
				+ "Invalid move to 6,6: Knight cannot move there.\n", outContent.toString());

		assertEquals(test, ch.getPiece(3, 3));
		assertEquals(dummy1, ch.getPiece(4, 5));
		assertEquals(dummy2, ch.getPiece(4, 4));
		assertEquals(dummy3, ch.getPiece(3, 4));
	}

	/*
	 * This tests all proper forms of movement.
	 */
	@Test
	public void movement() throws Exception{
		
		Player one = new Player("White");
		Player two = new Player("Black");
		ChessBoard ch = new ChessBoard(one, two, "debug");
		Knight test = new Knight("Black", ch.getSquare(3, 3), ch);
		Knight dummy1 = new Knight("White", ch.getSquare(4, 5), ch);

		test.moveTo(4, 5); //Kill an enemy Knight <1,2>
		test.moveTo(6, 6); //<2,1>
		test.moveTo(4, 7); //<-2,1>
		test.moveTo(5, 5); //<1,-2>
		test.moveTo(4, 3); //<-1,-2>
		test.moveTo(3, 5); //<-1,2>
		test.moveTo(5, 4); //<2,-1>
		test.moveTo(4, 2); //<-1,-2>

		assertEquals("Black Knight kills White Knight.\n"
				+ "Black Knight moves to 4,5.\n"
				+ "Black Knight moves to 6,6.\n"
				+ "Black Knight moves to 4,7.\n"
				+ "Black Knight moves to 5,5.\n"
				+ "Black Knight moves to 4,3.\n"
				+ "Black Knight moves to 3,5.\n"
				+ "Black Knight moves to 5,4.\n"
				+ "Black Knight moves to 4,2.\n", outContent.toString());

		assertEquals(test, ch.getPiece(4, 2));
		assertEquals(dummy1.isAlive(), false);
	}

	@After
	public void cleanUpStreams() {
		System.setOut(null);
		System.setErr(null);
	}
}
