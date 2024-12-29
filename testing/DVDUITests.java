package testing;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import dvdClasses.*;


public class DVDUITests {

	@Test
	@DisplayName("Constructor Test")
	public void constructorTest() {
		var d = new DVDCollection();
		
		var console = new DVDConsoleUI(d);
		
		assertNotNull(console);
		System.out.println("Passed Constructor Tests");
	}

}
