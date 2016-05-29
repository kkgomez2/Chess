package edu.illinois.cs.cs242.assignment11.data;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import edu.illinois.cs.cs242.assignment11.exceptions.MazePathFindingException;


/**
 * A Square is an object that holds basic information for our path finder
 * @author Charlie Meyer <cemeyer2@illinois.edu>
 * $Rev:: 516                                                                $:  Revision of last commit
 * $Author:: cemeyer2                                                        $:  Author of last commit
 * $Date:: 2010-01-31 17:05:12 -0600 (Sun, 31 Jan 2010)                      $:  Date of last commit
 */
public class Square implements Comparable<Square>{

	private double accumulatedCost, estimatedCost;
	private Square parent;
	private final Position position;
	private final Color color;
	private final Maze parentMaze;
	
	/**
	 * Constructs a Square. This constructor is protected because we only want to allow construction of
	 * Square objects from a Maze object
	 * @param position the Position that this square corresponds to
	 * @param color the color in this Maze that this square represents
	 * @param parentMaze the Maze that constructed this Square
	 */
	protected Square(Position position, Color color, Maze parentMaze)
	{
		this.position = position;
		this.color = color;
		this.parentMaze = parentMaze;
		
		accumulatedCost = 0d;
		estimatedCost = 0d;
		parent = null;
	}
	
	/**
	 * @return 1 if this square has a higher total cost than the parameter, -1
	 * if this square has a lower total cost than the parameter, and 0 if
	 * both the parameter and this square have the same cost
	 */
	public int compareTo(Square other) 
	{
		if(this.getTotalCost() > other.getTotalCost())
		{
			return 1;
		}
		else if(this.getTotalCost() < other.getTotalCost())
		{
			return -1;
		}
		else
		{
			return 0;
		}
	}

	/**
	 * sets the accumulated cost for this square
	 * @param accumulatedCost the value to set the accumulated cost to
	 */
	public void setAccumulatedCost(double accumulatedCost) 
	{
		this.accumulatedCost = accumulatedCost;
	}

	/**
	 * sets the estimated cost for this square
	 * @param estimatedCost the value to set the estimated cost to
	 */
	public void setEstimatedCost(double estimatedCost) 
	{
		this.estimatedCost = estimatedCost;
	}

	/**
	 * sets the parent of this square
	 * @param parent the square that should be the parent of this square
	 */
	public void setParent(Square parent) 
	{
		this.parent = parent;
	}

	/**
	 * returns the position in the Maze that this square represents
	 * @return the position in the Maze that this square represents
	 */
	public final Position getPosition() 
	{
		return this.position;
	}

	/**
	 * @return the accumulated cost for this square
	 */
	public double getAccumulatedCost() 
	{
		return this.accumulatedCost;
	}
	
	/**
	 * @return the estimated cost for this square
	 */
	public double getEstimatedCost() 
	{
		return this.estimatedCost;
	}
	
	/**
	 * @return the total cost for this square, which is defined to be the sum
	 * of the estimated cost and the accumulated cost
	 * @see Square#getAccumulatedCost()
	 * @see Square#getEstimatedCost()
	 */
	public double getTotalCost() 
	{
		return this.getAccumulatedCost() + this.getEstimatedCost();
	}

	/**
	 * @return the color that this square represents
	 */
	public final Color getColor() 
	{
		return this.color;
	}
	
	/**
	 * @return the parent of this square, may be null if no parent is set
	 */
	public Square getParent() 
	{
		return this.parent;
	}
	
	/**
	 * @return the Maze that this square was constructed by
	 */
	public Maze getParentMaze() 
	{
		return this.parentMaze;
	}

	/**
	 * @return a List of square objects that are adjacent in the parent Maze to this square
	 */
	public List<Square> getAdjacentSquares() 
	{
		List<Position> adjacentPositions = this.getParentMaze().getAdjacentPositions(this.getPosition()); //TODO: this is a long message chain, consider refactoring
		List<Square> adjacentSquares = new LinkedList<Square>();
		
		for(Position adjacentPosition : adjacentPositions)
		{
			try 
			{
				adjacentSquares.add(this.getParentMaze().getSquareAt(adjacentPosition));
			} 
			catch (MazePathFindingException e) //happens when accessing an out of bounds square
			{ 
				//TODO: need to do something better here
			}
		}
		return adjacentSquares;
	}

	/**
	 * @param adjacentSquare the square to check
	 * @return true if the parameter is adjacent to this square, false otherwise
	 */
	public boolean isAdjacentTo(Square adjacentSquare) 
	{
		return this.getPosition().isAdjacentTo(adjacentSquare.getPosition());
	}
	
	public String toString()
	{
		return "Square: pos:"+getPosition()+" est:"+getEstimatedCost()+" accum:"+getAccumulatedCost()+" total:"+getTotalCost()+" color:"+getColor();
	}
	
	/**
	 * gets a hash for this square, which is only guaranteed to be unique to other squares
	 * that have different coordinates than this square
	 */
	@Override
	public int hashCode()
	{
		return this.getPosition().hashCode();
	}
	
	/**
	 * @return true if the parameter is an instance of a Position object and has the same 
	 * coordinates as this Position, false otherwise
	 */
	@Override
	public boolean equals(Object other)
	{
		if(other instanceof Square)
		{
			return 	(this.position.getX() == ((Square)other).getPosition().getX()) && 
					(this.position.getY() == ((Square)other).getPosition().getY());
		}
		return false;
	}
}
