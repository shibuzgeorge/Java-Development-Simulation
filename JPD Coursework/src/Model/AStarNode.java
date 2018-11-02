package Model; 


/**
 * Represents an abstract node to facilitate application of the A* algorithm
 * @author Arun Bahra
 * @version 1.0
 */
public class AStarNode {
	//A location associated with the node
	private Location loc;
	//Indicates the cost to reach a starting point from this object's location
	private int g;
	//Indicates the heuristic(estimated) cost to reach the end point from this object's location
	private int h;
	//The node that this object connected to
	private AStarNode parent;
	
	/**
	 * A constructor for an <code>AStar node</code>
	 * @param loc The location associated with this node
	 */
	public AStarNode(Location loc)
	{
		this.loc = loc;
		this.parent = null;
	}
	
	/**
	 * Finds the true cost of moving to another node from this object, as per the A* algorithm
	 * @return a <code>int</code> pertaining to the integer value calculated as to the <code>Node's</code> F cost
	 */

	public int calculateF() {
		return (getG() + getH());
	}
	
	/**
	 * Finds the cost from moving from the starting location to this <code>Node's</code> location as per the A* algorithm
	 * @return a <code>int</code> pertaining to the integer value calculated as to the <code>Node's</code> G cost 
	 */

	public int getG() {
		return g;
	}
	
	/**
	 * Sets a <code>Node's</code> G cost
	 * @param g the cost to set
	 */

	public void setG(int g) {
		this.g = g;
	}
	
	/**
	 *Finds the estimated cost from moving from this <code>Node's</code> location to the end destination as per the A* algorithm
	 * @return a <code>int</code> pertaining to the integer value calculated as to the <code>Node's</code> H cost
	 */

	public int getH() {
		return h;
	}
	
	/**
	 * Sets a <code>Node's</code> H cost
	 * @param h the cost to set
	 */
	public void setH(int h) {
		this.h = h;
	}
	
	/**
	 * An accessor for the <code>Node's</code> fields 
	 * @return a {@link Location} representing the location associated with the <code>Node</code>
	 */

	public Location getLocation() {
		return loc;
	}
	
/**
 * A mutator for the <code>Node's</code> fields
 * @param node the <code>Node</code> to be referred to as this <code>Node's</code> parent
 */

	public void setParent(AStarNode node) {
		parent = node;
	}
	
	/**
	 * An accessor for the <code>Node's</code> fields  
	 * @return the <code>Node's</code> parent
	 */

	public AStarNode getParent() {
		return parent;
	}
	
	/**
	 * Tests for equality of locations between this object and another <code>AStar Node</code>
	 * @param n A <code>Node</node> to compare to this object
	 * @return A <code>boolean</code> pertaining whether the two objects the same
	 */
	
	public boolean sameLocationAs(AStarNode n)
	{
		return this.loc.equals(n.getLocation());

	}
}
