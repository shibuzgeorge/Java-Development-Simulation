package Model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Performs tests on the {@link Distance} class
 * 
 * @author Azhar Zaman
 * @version 1.0
 */
public class DistanceTest {

	Location location1;
	Location location2;
	Distance distance;

	@Before
	public void setUp() throws Exception {

		location1 = new Location(4, 3);
		location2 = new Location(5, 8);

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void manhattenDistanceTest() {

		assertEquals(6, distance.manhattanDist(location1, location2));

	}

}
