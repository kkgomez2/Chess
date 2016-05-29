package Tests;
import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Logic.ChessBoard;
import Logic.Pawn;
import Logic.Player;

/*
 *  Pawn tests.
 *  @author Kevin Gomez kkgomez2@illinois.edu kevinkgomez.com
 *
 * 	Tests: offTheBoardFails(), jumpingOverPiecesFails(),
 * 		  improperMovementFails(), movement()
 */
public class PawnTests {
	
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
	 * This test assures that a Pawn attempting to move off the board does not do so.
	 * and that the Square on the ChessBoard still contains the Pawn.
	 */
	@Test
	public void offTheBoardFails() throws Exception{

		Player one = new Player("White");
		Player two = new Player("Black");
		ChessBoard ch = new ChessBoard(one, two, "debug");
		Pawn test1 = new Pawn("White", ch.getSquare(7, 7), ch);
		ch.identifyPiece(7, 7);
		test1.moveTo(7, 8);
		Pawn test2 = new Pawn("Black", ch.getSquare(7, 0), ch);
		ch.identifyPiece(7, 0);
		test2.moveTo(7, -1);

	    assertEquals("The piece at 7,7 is a White Pawn.\n"
	    		+ "Invalid move to 7,8: Out of board bounds.\n"
	    		+ "The piece at 7,0 is a Black Pawn.\n"
	    		+ "Invalid move to 7,-1: Out of board bounds.\n", outContent.toString());

	    assertEquals(test1, ch.getPiece(7, 7));
	    assertEquals(ch.getPiece(7, 8), null);
	    assertEquals(test2, ch.getPiece(7, 0));
	    assertEquals(ch.getPiece(7, -1), null);
	}

	/*
	 * This assures that pieces cannot jump over others.
	 */
	@Test
	public void jumpingOverPiecesFails() throws Exception{
		
		Player one = new Player("White");
		Player two = new Player("Black");
		ChessBoard ch = new ChessBoard(one, two, "debug");
		Pawn test = new Pawn("White", ch.getSquare(1, 1), ch);
		Pawn dummy = new Pawn("White", ch.getSquare(1, 2), ch);
		
		ch.getPiece(1,1).moveTo(1, 3);

	    assertEquals("White Pawn at 1,1 cannot jump over that White Pawn at 1,2.\n"
	    		+ "Invalid move to 1,3: Pawn cannot move there.\n", outContent.toString());
	    
	    assertEquals(test, ch.getPiece(1, 1));

	}
	
	/*
	 * This assures that improper movements do not succeed.
	 */
	@Test
	public void improperMovementFails() throws Exception{
		
		Player one = new Player("White");
		Player two = new Player("Black");
		ChessBoard ch = new ChessBoard(one, two, "debug");
		Pawn test1 = new Pawn("White", ch.getSquare(1, 1), ch);
		Pawn test2 = new Pawn("White", ch.getSquare(2, 1), ch);
		Pawn test3 = new Pawn("White", ch.getSquare(3, 1), ch);
		Pawn test4 = new Pawn("White", ch.getSquare(4, 1), ch);
		Pawn test5 = new Pawn("White", ch.getSquare(5, 1), ch);
		Pawn dummy = new Pawn("White", ch.getSquare(5, 2), ch);
		Pawn dummy2 = new Pawn("Black", ch.getSquare(5, 3), ch);

		ch.getPiece(1,1).moveTo(1, 1); //Move to same space.
		ch.getPiece(1,1).moveTo(2, 2); //Pawn's Diagonal without piece to kill.
		ch.getPiece(2,1).moveTo(2, 6); //Moving too far up
		ch.getPiece(3,1).moveTo(3, 0); //Moving backwards
		ch.getPiece(4,1).moveTo(4, 2); 
		ch.getPiece(4,2).moveTo(4, 4); //Doing a two step movement after this Pawn's 1st move
		ch.getPiece(5,1).moveTo(5, 2); //Moving onto a Square with same color Piece on it
		ch.getPiece(5, 1).moveTo(5, 3);//Moving onto a Square with an enemy on it, yet not diagonally

	    assertEquals("Invalid move to 1,1: Pawn is already on 1,1.\n"
	    		+ "Invalid move to 2,2: Pawn cannot move there.\n"
	    		+ "Invalid move to 2,6: Pawn cannot move there.\n"
	    		+ "Invalid move to 3,0: Pawn cannot move there.\n"
	    		+ "White Pawn moves to 4,2.\n"
	    		+ "Invalid move to 4,4: Pawn cannot move there.\n"
	    		+ "A pawn cannot move onto that occupied square at 5,2.\n"
	    		+ "Invalid move to 5,2: Pawn cannot move there.\n"
	    		+ "A pawn cannot move onto that occupied square at 5,3.\n"
	    		+ "Invalid move to 5,3: Pawn cannot move there.\n", outContent.toString());
	    
	    assertEquals(test1, ch.getPiece(1, 1));
	    assertEquals(test2, ch.getPiece(2, 1));
	    assertEquals(test3, ch.getPiece(3, 1));
	    assertEquals(null, ch.getPiece(4, 1)); //Test if the Square is properly empty.
	    assertEquals(test4, ch.getPiece(4, 2));
	    

	}

	/*
	 * This tests all proper forms of movement.
	 */
	@Test
	public void movement() throws Exception{
		
		Player one = new Player("White");
		Player two = new Player("Black");
		ChessBoard ch = new ChessBoard(one, two, "debug");
		Pawn test0 = new Pawn("White", ch.getSquare(0, 1), ch);
		Pawn test1 = new Pawn("White", ch.getSquare(1, 2), ch);
		Pawn dummy1 = new Pawn("Black", ch.getSquare(0, 3), ch);
		Pawn test2 = new Pawn("White", ch.getSquare(2, 1), ch);
		Pawn test3 = new Pawn("White", ch.getSquare(4, 1), ch);
		Pawn dummy2 = new Pawn("Black", ch.getSquare(5, 2), ch);
		
		ch.getPiece(0,1).moveTo(0, 2); //Normal movement
		ch.getPiece(1,2).moveTo(0, 3); //Diagonal killing
		ch.getPiece(0,3).moveTo(0, 4); //Consecutive movement
		ch.getPiece(2,1).moveTo(2, 3); //First turn, two step
		ch.getPiece(4,1).moveTo(5, 2); //Diagonal only when killing

	    assertEquals("White Pawn moves to 0,2.\n"
	    		+ "White Pawn kills Black Pawn.\n"
	    		+ "White Pawn moves to 0,3.\n"
	    		+ "White Pawn moves to 0,4.\n"
	    		+ "White Pawn moves to 2,3.\n"
	    		+ "White Pawn kills Black Pawn.\n"
	    		+ "White Pawn moves to 5,2.\n", outContent.toString());

	    assertEquals(test0, ch.getPiece(0, 2));
	    assertEquals(test1, ch.getPiece(0, 4));
	    assertEquals(null, ch.getPiece(1, 2)); //Test that the previous Square has no reference to test1.
	    assertEquals(dummy1.isAlive(), false); //Test to see if Pawn is dead
	    assertEquals(dummy1.getSquare(), null); //....and its Square is null.
	    assertEquals(test2, ch.getPiece(2, 3));
	    assertEquals(test3, ch.getPiece(5, 2));
	    

	}

	@After
	public void cleanUpStreams() {
		System.setOut(null);
		System.setErr(null);
	}
}
