package Tests;
import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Logic.Bishop;
import Logic.ChessBoard;
import Logic.Player;

/*
 *  Bishop tests.
 *  @author Kevin Gomez kkgomez2@illinois.edu kevinkgomez.com
 *
 * 	Tests: offTheBoardFails(), jumpingOverPiecesFails(),
 * 		  improperMovementFails(), movement()
 */
public class BishopTests {

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
		Bishop test = new Bishop("White", ch.getSquare(3, 3), ch);
		
		test.moveTo(8, 8);
		test.moveTo(-3, -3);

		assertEquals("Invalid move to 8,8: Out of board bounds.\n"
					+ "Invalid move to -3,-3: Out of board bounds.\n", outContent.toString());
		
		assertEquals(test, ch.getPiece(3, 3));
	}

	/*
	 * This assures that pieces cannot jump over others.
	 */
	@Test
	public void jumpingOverPiecesFails() throws Exception{
		
		Player one = new Player("White");
		Player two = new Player("Black");
		ChessBoard ch = new ChessBoard(one, two, "debug");
		Bishop test = new Bishop("White", ch.getSquare(1, 1), ch);
		Bishop test2 = new Bishop("Black", ch.getSquare(1, 3), ch);
		Bishop test3 = new Bishop("White", ch.getSquare(3, 1), ch);
		Bishop test4 = new Bishop("Black", ch.getSquare(3, 3), ch);
		
		test.moveTo(4, 4);
		test2.moveTo(4, 0);
		test3.moveTo(0, 4);
		test4.moveTo(0, 0);

		//Test up+right, down+right, up+left, down+left
		assertEquals(	"There's a Black Bishop in the way.\n"
					+ "White Bishop's up and right diagonal path is not clear.\n"
					+ "Invalid move to 4,4: Bishop cannot move there.\n"
					+ "There's a White Bishop in the way.\n"
					+ "Black Bishop's down and right diagonal path is not clear.\n"
					+ "Invalid move to 4,0: Bishop cannot move there.\n"
					+ "There's a Black Bishop in the way.\n"
					+ "White Bishop's up and left diagonal path is not clear.\n"
					+ "Invalid move to 0,4: Bishop cannot move there.\n"
					+ "There's a White Bishop in the way.\n"
					+ "Black Bishop's down and left diagonal path is not clear.\n"
					+ "Invalid move to 0,0: Bishop cannot move there.\n", outContent.toString());
		
		assertEquals(test, ch.getPiece(1, 1));
		assertEquals(test2, ch.getPiece(1, 3));
		assertEquals(test3, ch.getPiece(3, 1));
		assertEquals(test4, ch.getPiece(3, 3));
	}

	/*
	 * This assures that improper movements do not succeed.
	 */
	@Test
	public void improperMovementFails() throws Exception{
		
		Player one = new Player("White");
		Player two = new Player("Black");
		ChessBoard ch = new ChessBoard(one, two, "debug");
		Bishop test = new Bishop("White", ch.getSquare(1, 1), ch);
		Bishop test2 = new Bishop("White", ch.getSquare(3, 3), ch);
		
		test.moveTo(3, 3); //Kill your friend
		test2.moveTo(0, 3); //Go side
		test2.moveTo(3, 7); //Go up


		assertEquals("Invalid move to 3,3: Same color piece on that square. (White).\n"
					+ "Invalid move to 0,3: Bishop cannot move there.\n"
					+ "Invalid move to 3,7: Bishop cannot move there.\n", outContent.toString());
		
		assertEquals(test, ch.getPiece(1, 1));
		assertEquals(test2, ch.getPiece(3, 3));
	}

	/*
	 * This tests all proper forms of movement.
	 */
	@Test
	public void movement() throws Exception{
		
		Player one = new Player("White");
		Player two = new Player("Black");
		ChessBoard ch = new ChessBoard(one, two, "debug");
		Bishop test = new Bishop("White", ch.getSquare(1, 1), ch);
		test.moveTo(4, 4);
		test.moveTo(6, 2);
		test.moveTo(4, 0);
		Bishop dummy = new Bishop("Black", ch.getSquare(2, 2), ch);
		test.moveTo(2, 2);

		//Test up+right, down+right, down+left, up+left
		assertEquals("White Bishop moves to 4,4.\n"
				+ "White Bishop moves to 6,2.\n"
				+ "White Bishop moves to 4,0.\n"
				+ "White Bishop kills Black Bishop.\n"
				+ "White Bishop moves to 2,2.\n", outContent.toString());
		
		assertEquals(test, ch.getPiece(2, 2));
	    assertEquals(dummy.isAlive(), false); //Test to see if Bishop is dead
	    assertEquals(dummy.getSquare(), null); //....and its Square is null.
	}

	@After
	public void cleanUpStreams() {
		System.setOut(null);
		System.setErr(null);
	}
}
