package Model;

public class Distance {
	public static int manhattanDist(Location l1, Location l2) {
		int absX = Math.abs(l1.getX() - l2.getX());
		int absY = Math.abs(l1.getY() - l2.getY());
		return absX + absY;
	}

}
