package View;


import Model.SimElement;
import Model.Robot;
/**
 * The main method to start the application
 * @author Daniel Ahmed
 * @version 1.0
 *
 */

public class Warehouse {
	static int width;
	static int height;
	static SimElement[][][] toDraw;

	Warehouse(int userWidth, int userHeight) {
		width = userWidth;
		height = userHeight;
		toDraw = new SimElement[width][height][2];	}

	public void addToBoard(SimElement se, int userWidth, int userHeight, int level){
		Warehouse.toDraw[userWidth][userHeight][level] = se;
		
	}
	public void removeFromBoard(int userWidth, int userHeight, int level){
		Warehouse.toDraw[userWidth][userHeight][level] = null;
	}
	
	public void updateToBoard(SimElement se, int x, int y, int level)
	{
		if(se instanceof Robot)
		{
			Robot r = (Robot) se;
			if(!(r.getLocation().equals(r.getPreviousLocation())))
			{
				addToBoard(se, se.getLocation().getX(), se.getLocation().getY(), 0);
				removeFromBoard(r.getPreviousLocation().getX(), r.getPreviousLocation().getY(), 0);
			}
		}
	}
}