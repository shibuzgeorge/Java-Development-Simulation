package Model;

import java.util.ArrayList;

/**
 * Represents a moving element of a simulation
 * 
 * @author Arun Bahra
 * @version 1.0
 */

public interface Actor {
	/**
	 * Allows an actor to move to a new {@link Location}
	 * @param destinations an {@link ArrayList} containing the {@link Location} of where the <code>Actor</Actor> must visit
	 * @param obstacles destinations an {@link ArrayList} containing the {@link Location} of where the <code>Actor</Actor> must avoid
	 * @param xDim the horizontal dimension of the space the <code>Actor</code> is operating in
	 * @param yDim the vertical dimension of the space the <code>Actor</code> is operating in
	 */
	
	public abstract void move(ArrayList<Location> destinations, ArrayList<Location> obstacles, int xDim, int yDim);
	
	/**
	 * Checks whether an actor is available to take up an order
	 * @param xDim the horizontal dimensions of the grid
	 * @param yDim the vertical dimensions of the grid
	 * @return a <code>boolean</code> pertaining whether or not the actor is available to take up an order 
	 */
	
	public abstract boolean isAvailable(int xDim, int yDim);
	
	
}