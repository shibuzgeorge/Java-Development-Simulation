package Model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Performs unit tests on the {@link AStarNode} class.
 * 
 * @author Azhar Zaman
 * @version 1.0
 */
public class AStarNodeTest {

	private Location location1, location2, location3;
	AStarNode node1;
	AStarNode node2;
	AStarNode node3;

	@Before
	public void setUp() throws Exception {

		location1 = new Location(5, 8);

		node1 = new AStarNode(location1);
		location2 = node1.getLocation();

		location3 = new Location(4, 8);
		node2 = new AStarNode(location3);
		node3 = new AStarNode(location3);

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void locationTest() {

		equals(node1.getLocation().equals(location1));

	}

	@Test
	public void fCalculationTest() {

		node1.setH(5);
		node1.setG(3);
		int f = node1.calculateF();
		assertEquals(8, f);

	}

	@Test
	public void sameLocationTest() {

		node2.sameLocationAs(node3);

	}

}
