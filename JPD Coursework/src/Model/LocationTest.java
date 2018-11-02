package Model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Performs unit tests on the {@link Location} class.
 * 
 * @author Arun Bahra
 * @version 1.0
 */
public class LocationTest {

	private Location location1, location2, location3;

	@Before
	public void setUp() throws Exception {
		location1 = new Location(0, 0);
		location2 = new Location(0, 0);
		location3 = new Location(5, 8);
	}

	@Test
	public void testEquals() {
		assertTrue(location1.equals(location2));
		assertFalse(location1.equals(location3));
		assertFalse(location1.equals("test"));
	}

	@Test
	public void testGetCoords() {
		assertEquals(5, location3.getX());
		assertNotEquals(10, location3.getX());

		assertEquals(8, location3.getY());
		assertNotEquals(1, location3.getY());
	}

	@Test
	public void testToString() {
		assertEquals("x = 0, y = 0", location1.toString());
	}

}