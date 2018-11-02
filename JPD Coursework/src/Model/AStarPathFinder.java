package Model;
import java.util.*;

/**
 * Finds a path between a start and end point using the A* path finding algorithm
 * @author Arun Bahra
 * @version 1.0
 */

public class AStarPathFinder {
	//A list of locations that need to be visited
    private ArrayList<Location> locationsToVisit;
    //The starting location of the object to be moved
    private Location initialLoc;
    //A list of locations that must be avoided
    private ArrayList<Location >obstacles;
    //The cost to make one move
    private final int DEFAULT_MOVE_COST = 1;
    //A list of all the locations representing the path the mover takes to visit all compulsory locations
    private ArrayList<Location> totalPath;
    //The x dimension of the space in which the path is to be found
    private int xDim;
    //The y dimension of the space in which the path is to be found
    private int yDim;
    
    /**
     * The constructor for a <code>PathFinder</code> object
     * @param current The current {@link Location} of the moving object
     * @param shelfLocs The locations that must be visited
     * @param obstacles The locations that must be avoided
     * @param xDim An <code>int</code> storing x dimension of the path finding space
     * @param yDim An <code>int</code> storing y dimension of the path finding space
     */
    public AStarPathFinder(Location current, ArrayList<Location> shelfLocs, ArrayList<Location> obstacles, int xDim, int yDim) {
        this.locationsToVisit = shelfLocs;
        this.initialLoc = current;
        this.obstacles = obstacles;
        this.xDim = xDim;
        this.yDim = yDim;
        this.totalPath = new ArrayList<Location>();
    }
    /**
     * Splits the path to be found into sub-paths to which the A* algorithm is applied
     */
    
	public void start() {
        AStarNode startNode = new AStarNode(initialLoc);
        for(Location l : locationsToVisit)
        {
            AStarNode endNode = new AStarNode(l);
            findPath(startNode, endNode);
            startNode = endNode;
        }
	}
    
	/**
	 * Finds a path between two points using the A* algorithm
	 * @param startNode A {@link AStarNode} depicting a starting point for the path to be found
	 * @param endNode A @link Node} depicting the end point for the path to be found
	 */
    private void findPath(AStarNode startNode, AStarNode endNode)
    {
    	ArrayList<AStarNode> nodesToEvaluate = new ArrayList<AStarNode>();
    	ArrayList<AStarNode> evaluatedNodes = new ArrayList<AStarNode>();
    	
    	
    	startNode.setG(0);
    	startNode.setH(Distance.manhattanDist(startNode.getLocation(), endNode.getLocation()));
    	boolean endNodeReached = false;
    	nodesToEvaluate.add(startNode);
    	while(!(nodesToEvaluate.isEmpty() || endNodeReached))
    	{
    		AStarNode currentNode = getLowestFCostNode(nodesToEvaluate);
    		
    		if(!currentNode.sameLocationAs(endNode))
    		{    		
    			ArrayList<AStarNode> neighbours = getNeighbours(currentNode);
    			nodesToEvaluate.remove(currentNode);
    			evaluatedNodes.add(currentNode);
    		
    			for(AStarNode neighbour : neighbours)
    			{
    				neighbour.setH(Distance.manhattanDist(neighbour.getLocation(), endNode.getLocation()));
    				if(!(evaluatedNodes.contains(neighbour) || nodesToEvaluate.contains(neighbour)))
    				{
    					nodesToEvaluate.add(neighbour);
    				}
    			}
    		}
    		else
    		{
    			endNodeReached = true;
    			endNode = currentNode;
    			tracePath(startNode, endNode);
    		}
    	}
    }
    
    /**
     * Finds orthogonally adjacent nodes to a given {@link AStarNode}
     * @param currentNode the <code>Node</code> to find adjacents to
     * @return An {@link ArrayList} of <code>Nodes</code> that are adjacent to the given <code>Node</code>
     */
    private ArrayList<AStarNode> getNeighbours(AStarNode currentNode) {
    	ArrayList<AStarNode> neighbours = new ArrayList<AStarNode>();
    	for(int x = -1; x <= 1; x++)
    	{
    		for(int y = -1; y <= 1; y++)
    		{
    			if(Math.abs(x) != Math.abs(y))
    			{
    				int xAdj = x + currentNode.getLocation().getX();
    				int yAdj = y + currentNode.getLocation().getY();
    				
    				Location adj  = new Location(xAdj, yAdj);
    				
    				if(xAdj >= 0 && xAdj < xDim && yAdj < yDim && yAdj >= 0 && !checkObstacle(adj))
    				{
    					AStarNode adjNode = new AStarNode(adj);
    					neighbours.add(adjNode);
    					adjNode.setG(currentNode.getG() + DEFAULT_MOVE_COST);
    					adjNode.setParent(currentNode);
    				}
    			}
    		}
    	}
		return neighbours;
	}
    
    /**
     * Checks whether the {@link Location} parameter is an obstacle that must not be visited
     * @param l A {@link Location} to be checked
     * @return A <code>boolean</code> pertaining whether or not the parameter is to avoided
     */

	private boolean checkObstacle(Location l) {
        for(Location obstacle : obstacles)
        {
            if(obstacle.equals(l))
            {
                return true;
            }
        }
		return false;
	}
	/**
	 * 
	 * @param nodesToEvaluate 
	 * @return
	 */

	private AStarNode getLowestFCostNode(ArrayList<AStarNode> nodesToEvaluate) {
    	AStarNode lowCostNode = nodesToEvaluate.get(0);
    	if(nodesToEvaluate.size() > 1)
    	{
    		for(AStarNode n : nodesToEvaluate)
    		{
    			if(n.calculateF() < lowCostNode.calculateF() || (n.calculateF() == lowCostNode.calculateF() && n.getG() < lowCostNode.getG()))
    			{
    				lowCostNode = n;
    			}
    		}
    	}
		return lowCostNode;
	}
	
	/**
	 * An accessor method for the class's fields 
	 * @return the total path found from applying the A* algorithm
	 */

	public ArrayList<Location> getTotalPath()
    {
    	return totalPath;
    }
	
	/**
	 * Retraces the result of A* in reverse order to generate the locations that form the path to be taken 
	 * @param startNode the start point of the path 
	 * @param endNode the end point of the path
	 */
	
    private void tracePath(AStarNode startNode, AStarNode endNode) {
        AStarNode n = endNode;
        ArrayList<Location> subPath = new ArrayList<Location>();
        
        while(!(n.getParent() == null))
        {
            subPath.add(n.getLocation());
            n = n.getParent();
        }
        Collections.reverse(subPath);
        for(Location l : subPath)
        {
            totalPath.add(l);
        }
    }
}