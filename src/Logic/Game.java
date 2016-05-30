package Logic;
/*
 *  Runs a Chess Game loop with two Players until one Player is declared the winner.
 *  @author Kevin Gomez kkgomez2@illinois.edu kevinkgomez.com
 */

import java.util.Scanner;
//TODO: Checkmate currently doesn't work.
//faster way to debug checkmate
//set up a testGame.java
//with checkmate conditions 1 step away
public class Game {

	public static void main(String[] args) {
		//Start by asking which colors will play.
		Scanner playerColors = new Scanner(System.in); 
		System.out.println("What's Player 1's Color?: ");
		String p1Color = playerColors.nextLine();
		System.out.println("And Player 2's?: ");
		String p2Color = playerColors.nextLine();
		
		//Make sure the opposing colors don't match.
		while(p1Color.toLowerCase().equals(p2Color.toLowerCase())){
			System.out.println("Two different colors, please.");
			System.out.println("What's Player 1's Color?: ");
			p1Color = playerColors.nextLine();
			System.out.println("And Player 2's?: ");
			p2Color = playerColors.nextLine();
		}
		
		System.out.println("Then let the " + p1Color + " Side"
							+ "\n" + "face the " + p2Color + " Side!");
		
		//Set up the players and board
		//Turn switches from 1 and -1
		Player one = new Player(p1Color);
		Player two = new Player(p2Color);
		ChessBoard ch = new ChessBoard(one, two);
		int turn = 1;
		
		while(true){
			ch.printBoard();
			Player turnPlayer;
			if(turn == 1){
				System.out.println("It's Player " + p1Color + "'s turn.");
				turnPlayer = one;
			} else {
				System.out.println("It's Player " + p2Color + "'s turn.");
				turnPlayer = two;
			}
			
			//Get input from the user of the intended piece to move.
			Piece pieceToMove = null;
			while(pieceToMove == null){
				Scanner reader = new Scanner(System.in); 
				System.out.println("Enter an x: ");
				int desired_x = reader.nextInt(); 
				System.out.println("Enter a y: ");
				int desired_y = reader.nextInt(); 
			
				if(!ch.isValid(desired_x, desired_y)){
					System.out.println("Invalid piece"); //Piece is not within appropriate bounds
				}
				else if(ch.getPiece(desired_x, desired_y) != null){
					pieceToMove = ch.getPiece(desired_x, desired_y);
					if(!pieceToMove.getColor().equals(turnPlayer.getColor())){
						System.out.println("That piece isn't yours.");
						pieceToMove = null;
					}
				} else {
					System.out.println("There's no piece there at " + desired_x + "," + desired_y);
				}
			}
			
			/*
			 * Asks the Player for a set of destination coordinates
			 */
			
			while(!pieceToMove.hasMoved()){
				Scanner reader = new Scanner(System.in); 
				System.out.println("Enter a destination x for "
								+ pieceToMove.getColor() + " " + pieceToMove.getName() + ": ");
				int dest_x = reader.nextInt(); 
				System.out.println("Enter a destination y for "
						+ pieceToMove.getColor() + " " + pieceToMove.getName() + ": ");
				int dest_y = reader.nextInt(); 
			
				pieceToMove.moveTo(dest_x, dest_y);
			}
			
			if(one.isAWinner()){
				System.out.println("Player " + p1Color + " wins!");
				break;
			} else if (two.isAWinner()){
				System.out.println("Player " + p2Color + " wins!");
				break;
			}
			turn = -turn;
		}
		
	}

}
