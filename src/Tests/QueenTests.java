package Tests;
import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Logic.ChessBoard;
import Logic.Player;
import Logic.Queen;

//TODO: Add combinations of consecutive diagonals and plus shaped movements
/*
 *  Queen tests.
 *  @author Kevin Gomez kkgomez2@illinois.edu kevinkgomez.com
 *
 * 	Tests: diagonallyOffTheBoardFails(), upDownSideOffTheBoardFails(),
 *		  jumpingDiagonallyOverPiecesFails(), jumpingUpDownSideOverPiecesFails(),
 * 		  improperMovementFails(), movementDiagonal(), movementUpDownSide()
 */

public class QueenTests {

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
	 * and that the Square on the ChessBoard still contains the that piece. (Diagonally)
	 */
	@Test
	public void diagonallyOffTheBoardFails() throws Exception{
		
		Player one = new Player("White");
		Player two = new Player("Black");
		ChessBoard ch = new ChessBoard(one, two, "debug");
		Queen test = new Queen("Black", ch.getSquare(3, 3), ch);
		
		test.moveTo(8, 8);
		test.moveTo(-3, -3);

		assertEquals("Invalid move to 8,8: Out of board bounds.\n"
					+ "Invalid move to -3,-3: Out of board bounds.\n", outContent.toString());
		
		assertEquals(test, ch.getPiece(3, 3));
	}
	
	/*
	 * This test assures that this piece attempting to move off the board does not do so.
	 * and that the Square on the ChessBoard still contains the that piece. (Plus shaped)
	 */
	@Test
	public void upDownSideOffTheBoardFails() throws Exception{
		
		Player one = new Player("White");
		Player two = new Player("Black");
		ChessBoard ch = new ChessBoard(one, two, "debug");
		Queen test = new Queen("Black", ch.getSquare(0, 0), ch);
		
		test.moveTo(0, 8);
		test.moveTo(0, -2);

		assertEquals("Invalid move to 0,8: Out of board bounds.\n"
					+ "Invalid move to 0,-2: Out of board bounds.\n", outContent.toString());
		
		assertEquals(test, ch.getPiece(0, 0));
	}

	/*
	 * This assures that pieces cannot jump (diagonally) over others.
	 */
	@Test
	public void jumpingDiagonallyOverPiecesFails() throws Exception{
		
		Player one = new Player("White");
		Player two = new Player("Black");
		ChessBoard ch = new ChessBoard(one, two, "debug");
		Queen test = new Queen("White", ch.getSquare(1, 1), ch);
		Queen test2 = new Queen("Black", ch.getSquare(1, 3), ch);
		Queen test3 = new Queen("White", ch.getSquare(3, 1), ch);
		Queen test4 = new Queen("Black", ch.getSquare(3, 3), ch);
		
		test.moveTo(4, 4);
		test2.moveTo(4, 0);
		test3.moveTo(0, 4);
		test4.moveTo(0, 0);

		//Test up+right, down+right, up+left, down+left
		assertEquals(	"There's a Black Queen in the way.\n"
					+ "White Queen's up and right diagonal path is not clear.\n"
					+ "Invalid move to 4,4: Queen cannot move there.\n"
					+ "There's a White Queen in the way.\n"
					+ "Black Queen's down and right diagonal path is not clear.\n"
					+ "Invalid move to 4,0: Queen cannot move there.\n"
					+ "There's a Black Queen in the way.\n"
					+ "White Queen's up and left diagonal path is not clear.\n"
					+ "Invalid move to 0,4: Queen cannot move there.\n"
					+ "There's a White Queen in the way.\n"
					+ "Black Queen's down and left diagonal path is not clear.\n"
					+ "Invalid move to 0,0: Queen cannot move there.\n", outContent.toString());
		
		assertEquals(test, ch.getPiece(1, 1));
		assertEquals(test2, ch.getPiece(1, 3));
		assertEquals(test3, ch.getPiece(3, 1));
		assertEquals(test4, ch.getPiece(3, 3));
	}

	/*
	 * This assures that pieces cannot jump (plus shaped) over others.
	 */
	@Test
	public void jumpingUpDownSideOverPiecesFails() throws Exception{
		
		Player one = new Player("White");
		Player two = new Player("Black");
		ChessBoard ch = new ChessBoard(one, two, "debug");
		Queen test = new Queen("White", ch.getSquare(1, 1), ch);
		Queen test2 = new Queen("Black", ch.getSquare(1, 3), ch);
		Queen test3 = new Queen("White", ch.getSquare(3, 1), ch);
		Queen test4 = new Queen("Black", ch.getSquare(3, 3), ch);
		
		test.moveTo(1, 7);
		test2.moveTo(1, 0);
		test3.moveTo(0, 1);
		test2.moveTo(7, 3);

		//Test up, down, left, right
		assertEquals("White Queen's upwards path is not clear.\n"
					+ "Invalid move to 1,7: Queen cannot move there.\n"
					+ "Black Queen's downwards path is not clear.\n"
					+ "Invalid move to 1,0: Queen cannot move there.\n"
					+ "White Queen's left path is not clear.\n"
					+ "Invalid move to 0,1: Queen cannot move there.\n"
					+ "Black Queen's right path is not clear.\n"
					+ "Invalid move to 7,3: Queen cannot move there.\n", outContent.toString());
		
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
		Queen test = new Queen("Black", ch.getSquare(1, 1), ch);
		Queen test2 = new Queen("Black", ch.getSquare(0, 2), ch);

		test.moveTo(4, 3); //irregular diagonal
		test.moveTo(0, 2); //Killing a fellow Queen
		
		assertEquals("Invalid move to 4,3: Queen cannot move there.\n"
				+ "Invalid move to 0,2: Same color piece on that square. (Black).\n", outContent.toString());

		assertEquals(test, ch.getPiece(1, 1));
		assertEquals(test2, ch.getPiece(0, 2));
	}

	/*
	 * This tests diagonal forms of movement.
	 */
	@Test
	public void movementDiagonal() throws Exception{
		
		Player one = new Player("White");
		Player two = new Player("Black");
		ChessBoard ch = new ChessBoard(one, two, "debug");
		Queen test = new Queen("Black", ch.getSquare(1, 1), ch);
		
		test.moveTo(4, 4);
		test.moveTo(6, 2);
		test.moveTo(4, 0);
		Queen dummy = new Queen("White", ch.getSquare(2, 2), ch);
		test.moveTo(2, 2);

		//Test up+right, down+right, down+left, up+left
		assertEquals("Black Queen moves to 4,4.\n"
				+ "Black Queen moves to 6,2.\n"
				+ "Black Queen moves to 4,0.\n"
				+ "Black Queen kills White Queen.\n"
				+ "Black Queen moves to 2,2.\n", outContent.toString());
		
		assertEquals(test, ch.getPiece(2, 2));
	    assertEquals(dummy.isAlive(), false); //Test to see if Bishop is dead
	    assertEquals(dummy.getSquare(), null); //....and its Square is null.
	}
	
	/*
	 * This tests plus shaped forms of movement.
	 */
	@Test
	public void movementUpDownSide() throws Exception{
		
		Player one = new Player("White");
		Player two = new Player("Black");
		ChessBoard ch = new ChessBoard(one, two, "debug");
		Queen test = new Queen("Black", ch.getSquare(1, 1), ch);
		Queen dummy = new Queen("White", ch.getSquare(1, 6), ch);
		Queen test2 = new Queen("Black", ch.getSquare(1, 7), ch);
		Queen test3 = new Queen("White", ch.getSquare(7, 1), ch);
		Queen test4 = new Queen("Black", ch.getSquare(7, 7), ch);
		
		test.moveTo(1, 6);
		test2.moveTo(6, 7);
		test3.moveTo(2, 1);
		test4.moveTo(7, 0);

		//Test up+kill, right, left, down
		assertEquals("Black Queen kills White Queen.\n"
					+ "Black Queen moves to 1,6.\n"
					+ "Black Queen moves to 6,7.\n"
					+ "White Queen moves to 2,1.\n"
					+ "Black Queen moves to 7,0.\n", outContent.toString());
		
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
