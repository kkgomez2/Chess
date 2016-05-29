package edu.illinois.cs.cs242.assignment11.test.tests.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.illinois.cs.cs242.assignment11.data.Maze;
import edu.illinois.cs.cs242.assignment11.data.Position;
import edu.illinois.cs.cs242.assignment11.data.Square;

/**
 * $Rev:: 586                                                                $:  Revision of last commit
 * $Author:: m3rabb                                                          $:  Author of last commit
 * $Date:: 2010-02-01 16:18:25 -0600 (Mon, 01 Feb 2010)                      $:  Date of last commit
 */
public class SquareTest {
	private static Maze maze;
	private static final int MAP_WIDTH = 320;
	private static final int MAP_HEIGHT = 240;
	private static final double DELTA = 0.01;
	private static Position cornerPosition, centerPosition, edgePosition;
	private static Square cornerSquare, centerSquare, edgeSquare;
	private static final int originX = 0;
	private static final int originY = 0;
	private static final int centerX = MAP_WIDTH/2;
	private static final int centerY = MAP_HEIGHT/2;
	private static final int CORNER_NEIGHBOR_COUNT = 3;
	private static final int EDGE_NEIGHBOR_COUNT = 5;
	private static final int NORMAL_NEIGHBOR_COUNT = 8;
	
	/**
	 * This method is run once before any of the tests are run.
	 * It creates a "fixture" initializing the objects that are used repeatly in this
	 * test class.
	 * @throws Exception
	 */
	@BeforeClass
    public static void setUpBeforeClass() throws Exception {
		String pathDirectory = "input_files";
		String fileName = "maze0.bmp";
		String path = pathDirectory + File.separator + fileName;
		
		maze = new Maze(path);
		cornerPosition = new Position(originX,originY);
		centerPosition = new Position(centerX,centerY);
		edgePosition = new Position(originX,originY+1);
		cornerSquare = maze.getSquareAt(cornerPosition);
		centerSquare = maze.getSquareAt(centerPosition);
		edgeSquare = maze.getSquareAt(edgePosition);
    }
	
	/**
	 * This method is run after every one of the tests in this test class.
	 * It resets the squares used, so changes from one test do not pollute 
	 * following tests.
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		resetSquare(cornerSquare);
		resetSquare(centerSquare);
	}
	
	/**
	 * This function resets the attributes of a square.
	 * @param square the Square to be initialized
	 */
	private static void resetSquare(Square square)
	{
		square.setAccumulatedCost(0.0d);
		square.setEstimatedCost(0.0d);
		square.setParent(null);
	}
	
	/**
	 * This test checks that a Square is properly initialized: checking that 
	 * its costs, color, position, and parent attributes are set properly.
	 */
	@Test
	public void instantiateSquare()	{
		
		Position square1Position = cornerSquare.getPosition();
		assertEquals(cornerPosition, square1Position);
		
		assertEquals(cornerSquare.getAccumulatedCost(), 0, DELTA);
		assertEquals(cornerSquare.getEstimatedCost(), 0, DELTA);
		assertEquals(cornerSquare.getTotalCost(), 0, DELTA);
		
		Color square1Color = cornerSquare.getColor();
		assertTrue(square1Color.equals(Color.BLACK));
		
		assertNull(cornerSquare.getParent());
		
		Position square2Position = centerSquare.getPosition();
		assertEquals(centerPosition, square2Position);
		
		assertEquals(centerSquare.getAccumulatedCost(), 0, DELTA);
		assertEquals(centerSquare.getEstimatedCost(), 0, DELTA);
		assertEquals(centerSquare.getTotalCost(), 0, DELTA);
		
		Color square2Color = centerSquare.getColor();
		assertTrue(square2Color.equals(Color.WHITE));
		
		assertNull(centerSquare.getParent());
	}
	
	/**
	 * This test checks that different Squares are not equal to one another
	 * and that the same Square is equal to itself.
	 */
	@Test
	public void equals(){
		assertTrue(cornerSquare.equals(cornerSquare));
		assertFalse(cornerSquare.equals(centerSquare));
	}
	
	/**
	 * This test checks that a Square's costs can be set and read, and that the
	 * total cost is composed from the accumulated and estimated costs.
	 */
	@Test
	public void setCosts(){

		double estCost = 5.0d;
		double accumCost = 7.0d;
		
		cornerSquare.setEstimatedCost(estCost);
		assertEquals(cornerSquare.getEstimatedCost(), estCost, DELTA);
		
		cornerSquare.setAccumulatedCost(accumCost);
		assertEquals(cornerSquare.getAccumulatedCost(), accumCost, DELTA);
		assertEquals(cornerSquare.getTotalCost(), cornerSquare.getAccumulatedCost() + cornerSquare.getEstimatedCost(), DELTA);
	}
	
	/**
	 * This test checks that square can be ordered between another based on
	 * their costs.
	 */
	@Test
	public void compareTo(){
		
		int sameResult = cornerSquare.compareTo(centerSquare);
		
		double estCost = 5.0d;
		double accumCost = 7.0d;
		
		cornerSquare.setEstimatedCost(estCost);
		cornerSquare.setAccumulatedCost(accumCost);
		
		int largerResult = cornerSquare.compareTo(centerSquare);
		int smallerResult = centerSquare.compareTo(cornerSquare);
		
		assertEquals(sameResult, 0, DELTA);
		assertEquals(largerResult, 1, DELTA);
		assertEquals(smallerResult, -1, DELTA);
	}
	
	/**
	 * This test checks that a Square's parent can be properly set.
	 */
	@Test
	public void setParent()
	{
		cornerSquare.setParent(centerSquare);
		assertEquals(cornerSquare.getParent(), centerSquare);
	}

	/**
	 * This test checks that any Square can answer a list of all of it neighbors,
	 * whether it is a corner or edge Square, or a normal Square.
	 */
	// Square asks it maze for adjacents.
	// Maze asks the position for its neighbors.
	@Test
	public void getAdjacentPositions() {
		checkAdjacents(cornerSquare, CORNER_NEIGHBOR_COUNT);
		checkAdjacents(edgeSquare, EDGE_NEIGHBOR_COUNT);
		checkAdjacents(centerSquare, NORMAL_NEIGHBOR_COUNT);
	}
	
	/**
	 * This is a "helper" function for the getAdjacentPositions test.
	 * It checks that a Square knows alls of its neighbors.
	 * @param square the Square begin tested.
	 * @param neighborCount the square's expected number of neighbors.
	 */
	private static void checkAdjacents(Square square, int neighborCount) 
	{
		List<Square> adjacentSquares = square.getAdjacentSquares();
		assertTrue(adjacentSquares.size() == neighborCount);
		for(Square adjacentSquare : adjacentSquares)
		{
			assertTrue(square.isAdjacentTo(adjacentSquare));
		}
	}

}
