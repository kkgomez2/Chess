# Chess

These are a collection of Java classes that run Chess.

The program itself is interfaced entirely though console.

A player enters their color, then their opponent does the same. The game starts, and players then select their pieces by entering the x and y coordinates and the desired destination coordinates. If the movement is valid, the program will print out a text display of the board. This continues until a king is captured. (Checkmate isn't implemented.)

1.	Within *src/Logic*:

	* **Game.java** is the game loop that runs chess.

	* **Square.java** represents a square on a chessboard.

	* **Piece.java** represents a generic chess piece. All other chess piece objects inherit from this class. (Scout and Tactician are two custom pieces.)

	* **Player.java** represents a player.

	* **ChessBoard.java** is an object where most of the game logic ties together (Two Players, 8x8 Square object, etc.)

2. Within *src/Tests*:
	
	* Various tests that cover a range of cases and specific edge cases for each class.

The Doxyfile is an automatically generated file that documents each individual function

*(ChessManualTestPlan.docx refers to the intended functionality to an non-functional GUI portion)*
