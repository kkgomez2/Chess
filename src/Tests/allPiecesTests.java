package Tests;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.Test;
/*
 *  Tests every Piece
 *  @author Kevin Gomez kkgomez2@illinois.edu kevinkgomez.com
 */
//TODO: 
@RunWith(Suite.class)
@Suite.SuiteClasses({SquareTests.class, ChessBoardTests.class,
					PawnTests.class, RookTests.class, BishopTests.class,
					KnightTests.class, QueenTests.class, KingTests.class,
					TacticianTests.class, ScoutTests.class})
public class allPiecesTests {

}
