package testing;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import dvdClasses.DVD;


public class DVDTests {

	@Test
	@DisplayName("Constructor Setters Getters Tests")
	public void constructorSettersGettersTests() {
		var d = new DVD("Fire", "R", 50);
		
		assertEquals("Fire", d.getTitle());
		assertEquals("R", d.getRating());
		assertEquals(50, d.getRunningTime());
		assertEquals("Fire/R/50min", d.toString());
		
		d.setTitle("Water");
		d.setRating("PG");
		d.setRunningTime(0);
		
		assertEquals("Water", d.getTitle());
		assertEquals("PG", d.getRating());
		assertEquals(0, d.getRunningTime());
		
		assertEquals("Water/PG/0min", d.toString());
		System.out.println("Passed All DVD Tests");
	}
}
