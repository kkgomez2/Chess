package Tests;
import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Logic.ChessBoard;
import Logic.Player;
import Logic.Tactician;

/*
 *  Tactician tests.
 *  @author Kevin Gomez kkgomez2@illinois.edu kevinkgomez.com
 *
 * 	Tests: offTheBoardFails(),
 * 		  improperMovementFails(), movement()
 */

import org.junit.Test;

public class TacticianTests {

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
		Tactician test = new Tactician("White", ch.getSquare(0, 0), ch);
		Tactician test2 = new Tactician("Black", ch.getSquare(7, 7), ch);
		
		test.moveTo(-1, -1);
		test2.moveTo(8, 8);

		assertEquals("Invalid move to -1,-1: Out of board bounds.\n"
					+ "Invalid move to 8,8: Out of board bounds.\n", outContent.toString());

		assertEquals(test, ch.getPiece(0, 0));
		assertEquals(test2, ch.getPiece(7, 7));
	}
	

	/*
	 * This assures that improper movements do not succeed.
	 */
	@Test
	public void improperMovementFails() throws Exception{
		
		Player one = new Player("White");
		Player two = new Player("Black");
		ChessBoard ch = new ChessBoard(one, two, "debug");
		Tactician test = new Tactician("Black", ch.getSquare(1, 1), ch);
		Tactician dummy = new Tactician("Black", ch.getSquare(2, 2), ch);

		test.moveTo(1, 2); //North
		test.moveTo(1, 0); //South
		test.moveTo(2, 1); //East
		test.moveTo(0, 1); //West
		test.moveTo(2, 2); //Kill a fellow Tactician

		assertEquals("Invalid move to 1,2: Tactician cannot move there.\n"
					+ "Invalid move to 1,0: Tactician cannot move there.\n"
					+ "Invalid move to 2,1: Tactician cannot move there.\n"
					+ "Invalid move to 0,1: Tactician cannot move there.\n"
					+ "Invalid move to 2,2: Same color piece on that square. (Black).\n"
					, outContent.toString());

		assertEquals(test, ch.getPiece(1,1));
		assertEquals(dummy, ch.getPiece(2,2));
	}

	/*
	 * This tests all proper forms of movement.
	 */
	@Test
	public void movement() throws Exception{
		
		Player one = new Player("White");
		Player two = new Player("Black");
		ChessBoard ch = new ChessBoard(one, two, "debug");
		Tactician test = new Tactician("White", ch.getSquare(2, 2), ch);
		Tactician dummy = new Tactician("Black", ch.getSquare(3, 3), ch);

		//Clockwise diamond starting from the top into a diagonal up kill
		test.moveTo(3, 1);
		test.moveTo(2, 0);
		test.moveTo(1, 1);
		test.moveTo(2, 2);
		test.moveTo(3, 3);

		assertEquals("White Tactician moves to 3,1.\n"
					+ "White Tactician moves to 2,0.\n"
					+ "White Tactician moves to 1,1.\n"
					+ "White Tactician moves to 2,2.\n"
					+ "White Tactician kills Black Tactician.\n"
					+ "White Tactician moves to 3,3.\n", outContent.toString());

		assertEquals(test, ch.getPiece(3, 3));
		assertEquals(dummy.isAlive(), false);
	}

	@After
	public void cleanUpStreams() {
		System.setOut(null);
		System.setErr(null);
	}
}
