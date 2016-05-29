package Tests;
import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Logic.ChessBoard;
import Logic.King;
import Logic.Pawn;
import Logic.Player;

//TODO: Add combinations of consecutive diagonals and plus shaped movements
/*
 *  King tests.
 *  @author Kevin Gomez kkgomez2@illinois.edu kevinkgomez.com
 *
 * 	Tests: offTheBoardFails(),
 * 		  improperMovementFails(), movement(), win()
 */

public class KingTests {

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
		King test = new King("Black", ch.getSquare(0, 0), ch);
		King test2 = new King("White", ch.getSquare(7, 7), ch);

		test.moveTo(0, -1);
		test.moveTo(-1, 0);
		test.moveTo(-1, -1);
		test2.moveTo(8, 7);
		test2.moveTo(7, 8);
		test2.moveTo(8, 8);

		assertEquals("Invalid move to 0,-1: Out of board bounds.\n"
				+ "Invalid move to -1,0: Out of board bounds.\n"
				+ "Invalid move to -1,-1: Out of board bounds.\n"
				+ "Invalid move to 8,7: Out of board bounds.\n"
				+ "Invalid move to 7,8: Out of board bounds.\n"
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
		King test = new King("Black", ch.getSquare(1, 1), ch);
		test.moveTo(1, 3); //Moving more than 1 Square forward
		test.moveTo(6, 1); //Moving more than 1 Square right
		test.moveTo(4, 4); //Moving more than 1 Square diagonally
		test.moveTo(2, 4); //Moving irregularly
		King test2 = new King("Black", ch.getSquare(1, 2), ch);
		test.moveTo(1, 2); //Killing a fellow King
		
		assertEquals("Invalid move to 1,3: King cannot move there.\n"
				+ "Invalid move to 6,1: King cannot move there.\n"
				+ "Invalid move to 4,4: King cannot move there.\n"
				+ "Invalid move to 2,4: King cannot move there.\n"
				+ "Invalid move to 1,2: Same color piece on that square. (Black).\n", outContent.toString());

		assertEquals(test, ch.getPiece(1, 1));
		assertEquals(test2, ch.getPiece(1, 2));
	}

	/*
	 * This tests all proper forms of movement.
	 */
	@Test
	public void movement() throws Exception{
		
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
		test.moveTo(2, 2); //Kill an enemy King

		//Test up+right, down+right, down+left, up+left
		assertEquals("Black King moves to 2,2.\n"
				+ "Black King moves to 3,2.\n"
				+ "Black King moves to 4,1.\n"
				+ "Black King moves to 4,0.\n"
				+ "Black King moves to 4,1.\n"
				+ "Black King moves to 3,0.\n"
				+ "Black King moves to 2,0.\n"
				+ "Black King moves to 2,1.\n"
				+ "Black King kills White King.\n"
				+ "Player Black wins!\n"
				+ "Black King moves to 2,2.\n", outContent.toString());
		
		assertEquals(test, ch.getPiece(2, 2));
	    assertEquals(dummy.isAlive(), false); //Test to see if Bishop is dead
	    assertEquals(dummy.getSquare(), null); //....and its Square is null.
	    assertEquals(two.isAWinner(), true);
	    assertEquals(one.isAWinner(), false);
	}
	
	/*
	 * Tests the win condition of a standard game.
	 */
	@Test
	public void win() throws Exception{
		
		Player one = new Player("White");
		Player two = new Player("Black");
		ChessBoard ch = new ChessBoard(one, two, "debug");
		Pawn test = new Pawn("White", ch.getSquare(0, 1), ch);
		King test2 = new King("Black", ch.getSquare(1, 2), ch);
		
		test.moveTo(1, 2);

		assertEquals(test2.isAlive(), false);
		assertEquals(one.isAWinner(), true);
		assertEquals(two.isAWinner(), false);
		
	}
	
	@After
	public void cleanUpStreams() {
		System.setOut(null);
		System.setErr(null);
	}
}
