package Tests;
import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Logic.ChessBoard;
import Logic.Player;
import Logic.Scout;

/*
 *  Scout tests.
 *  @author Kevin Gomez kkgomez2@illinois.edu kevinkgomez.com
 *
 * 	Tests: offTheBoardFails(), 
 * 		  improperMovementFails(), movement()
 */

import org.junit.Test;

public class ScoutTests {

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
		Scout test = new Scout("White", ch.getSquare(0, 0), ch);
		Scout test2 = new Scout("Black", ch.getSquare(7, 7), ch);
		
		test.moveTo(0, -3);
		test2.moveTo(7, 10);
		test2.moveTo(7, 4);	//Valid move
		test2.moveTo(8, 4);	//Test failing with horizontal

		assertEquals("Invalid move to 0,-3: Out of board bounds.\n"
					+ "Invalid move to 7,10: Out of board bounds.\n"
					+ "Black Scout moves to 7,4.\n"
					+ "Invalid move to 8,4: Out of board bounds.\n", outContent.toString());

		assertEquals(test, ch.getPiece(0, 0));
		assertEquals(test2, ch.getPiece(7, 4));
	}


	/*
	 * This assures that improper movements do not succeed.
	 */
	@Test
	public void improperMovementFails() throws Exception{
		
		Player one = new Player("White");
		Player two = new Player("Black");
		ChessBoard ch = new ChessBoard(one, two, "debug");
		Scout test = new Scout("Black", ch.getSquare(1, 1), ch);

		test.moveTo(2, 1); //Moving side first
		test.moveTo(0, 1); //Moving side again
		test.moveTo(1, 4); 
		test.moveTo(1, 7); //Moving up
		test.moveTo(2, 2); //Diagonal
		test.moveTo(3, 2); //Knight movement

		Scout dummy = new Scout("Black", ch.getSquare(2, 4), ch);
		test.moveTo(2, 4); //Kill a fellow Scout

		assertEquals("Invalid move to 2,1: Scout cannot move there.\n"
					+ "Invalid move to 0,1: Scout cannot move there.\n"
					+ "Black Scout moves to 1,4.\n"
					+ "Invalid move to 1,7: Scout cannot move there.\n"
					+ "Invalid move to 2,2: Scout cannot move there.\n"
					+ "Invalid move to 3,2: Scout cannot move there.\n"
					+ "Invalid move to 2,4: Same color piece on that square. (Black).\n"
					, outContent.toString());

		assertEquals(test, ch.getPiece(1,4));
		assertEquals(dummy, ch.getPiece(2,4));
	}


	/*
	 * This tests all proper forms of movement.
	 */
	@Test
	public void movement() throws Exception{
		
		Player one = new Player("White");
		Player two = new Player("Black");
		ChessBoard ch = new ChessBoard(one, two, "debug");
		Scout test = new Scout("White", ch.getSquare(0, 0), ch);
		Scout dummy = new Scout("Black", ch.getSquare(2, 3), ch);
		Scout dummy2 = new Scout("Black", ch.getSquare(2, 6), ch);

		//Test up, side, side + kill, up + kill, side, side, down
		test.moveTo(0, 3);
		test.moveTo(1, 3);
		test.moveTo(2, 3);
		test.moveTo(2, 6);
		test.moveTo(1, 6);
		test.moveTo(0, 6);
		test.moveTo(0, 3);

		assertEquals("White Scout moves to 0,3.\n"
					+ "White Scout moves to 1,3.\n"
					+ "White Scout kills Black Scout.\n"
					+ "White Scout moves to 2,3.\n"
					+ "White Scout kills Black Scout.\n"
					+ "White Scout moves to 2,6.\n"
					+ "White Scout moves to 1,6.\n"
					+ "White Scout moves to 0,6.\n"
					+ "White Scout moves to 0,3.\n", outContent.toString());

		assertEquals(test, ch.getPiece(0, 3));
		assertEquals(dummy.isAlive(), false);
		assertEquals(dummy2.isAlive(), false);
	}

	@After
	public void cleanUpStreams() {
		System.setOut(null);
		System.setErr(null);
	}
}
