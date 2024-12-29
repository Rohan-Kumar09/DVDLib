package testing;

import static org.junit.Assert.*;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import dvdClasses.DVDCollection;

public class DVDCollectionTests {

	
	@Test
	@DisplayName("Constructor Tests")
	public void constructor() {
		var collection = new DVDCollection();
		
		assertNotNull(collection);
		String[] arr = collection.toString().split("\n\n");
		
		assertEquals("\nnumdvds = 0", arr[0]);
		assertEquals("dvdArray.length = 7", arr[1]);
	}
	
	
	@Test
	@DisplayName("Add or Modify Tests (5 Sub Tests)")
	public void addOrModify() {
		var collection = new DVDCollection();
		
		collection.addOrModifyDVD("Fire", "R", "3");
		
		// code for getting the first DVD
		String[] arr = collection.toString().split("\n\n");
		String[] arr2 = arr[2].split(" ");
		String firstDVD = arr2[2];
		// end code
		
		assertEquals("FIRE/R/3min", firstDVD);
		System.out.println("Passed Single Insertion");
		// ----------------------------------------------------------
		
		
		collection.addOrModifyDVD("AAAA", "R", "0");
		collection.addOrModifyDVD("Zeta", "R", "10");
		
		arr = collection.toString().split("\n\n");
		arr2 = arr[2].split(" ");
		firstDVD = arr2[2];
		
		assertEquals("AAAA/R/0min", firstDVD);
		System.out.println("Passed Ascending Order Insertion Check");
		// ----------------------------------------------------------
		
		
		collection.addOrModifyDVD("AAAA", "PG-13", "12");
		
		arr = collection.toString().split("\n\n");
		arr2 = arr[2].split(" ");
		firstDVD = arr2[2];
		
		assertEquals("AAAA/PG-13/12min", firstDVD);
		System.out.println("Passed Modify Check");
		// ----------------------------------------------------------
		
		
		collection.addOrModifyDVD("Title", "", "0");
		
		String s = collection.toString();
		
		assertFalse(s.contains("Title"));
		System.out.println("Passed Invalid Rating Check");
		// ----------------------------------------------------------
		
		
		collection.addOrModifyDVD("Title", "R", "-10");
		
		s = collection.toString();
		
		assertFalse(s.contains("Title/R/-10min"));
		System.out.println("Passed negative run time Check");
		// ----------------------------------------------------------
	}
	
	
	@Test
	@DisplayName("Remove Tests (4 Sub Tests)")
	public void removeTest() {
		var collection = new DVDCollection();
		collection.addOrModifyDVD("Fire", "R", "3");
		collection.addOrModifyDVD("AAAA", "R", "0");
		collection.addOrModifyDVD("Zeta", "R", "10");
		
		String prevS = collection.toString();
		collection.removeDVD("Title");
		String postS = collection.toString();
		
		assertTrue(prevS.equals(postS));
		System.out.println("Passed deleting Non Existing DVD");
		// ----------------------------------------------------------
		
		
		prevS = collection.toString();
		collection.removeDVD("Fire");
		postS = collection.toString();
		
		assertFalse(postS.contains("Fire"));
		System.out.println("Passed deleting the DVD named Fire");
		// ----------------------------------------------------------
		
		
		prevS = collection.toString();
		collection.removeDVD("AAAA");
		postS = collection.toString();
		
		assertFalse(postS.contains("AAAA"));
		System.out.println("Passed deleting the DVD named AAAA");
		// ----------------------------------------------------------
		
		
		prevS = collection.toString();
		collection.removeDVD("Zeta");
		postS = collection.toString();
		
		assertFalse(postS.contains("Zeta"));
		System.out.println("Passed deleting the DVD named Zeta");
		// ----------------------------------------------------------
	}
	
	
	@Test
	@DisplayName("Testing Getting DVDs by Rating (2 Sub Tests)")
	public void byRatingTest() {
		var collection = new DVDCollection();
		collection.addOrModifyDVD("Fire", "R", "3");
		collection.addOrModifyDVD("AAAA", "R", "0");
		collection.addOrModifyDVD("Zeta", "R", "10");
		
		
		assertTrue(collection.getDVDsByRating("PG").equals(""));
		System.out.println("Passed No DVDs with Rating PG test");
		// ----------------------------------------------------------
		
		
		assertEquals("AAAA/R/0min\nFIRE/R/3min\nZETA/R/10min\n", collection.getDVDsByRating("R"));
		System.out.println("Passed getting all DVDs with rating R");
		// ----------------------------------------------------------
	}
	
	
	@Test
	@DisplayName("Testing Getting Total Runtime (2 Sub Tests)")
	public void totalRunTime() {
		var collection = new DVDCollection();
		
		assertEquals(0, collection.getTotalRunningTime());
		System.out.println("Passed Empty Collection Test");
		// ----------------------------------------------------------
		
		
		collection.addOrModifyDVD("Fire", "R", "300");
		collection.addOrModifyDVD("AAAA", "R", "450");
		collection.addOrModifyDVD("Zeta", "R", "90");
		
		assertEquals(840, collection.getTotalRunningTime());
		System.out.println("Passed Runtime Tests");
	}
	
	
	@Test
	@DisplayName("Testing Load Function")
	public void loadTest() {
		var collection = new DVDCollection();
		
		collection.loadData("dvddata.txt");
		String s = collection.toString();
		
		assertEquals(
				"\nnumdvds = 3\n\n"
				+ "dvdArray.length = 7\n\n"
				+ "dvdArray[0] = ANGELS AND DEMONS/PG-13/138min\n\n"
				+ "dvdArray[1] = STAR TREK/R/127min\n\n"
				+ "dvdArray[2] = UP/PG/96min\n\n"
				, s);
		System.out.println("Passed Loading a formatted file into DVD Collection");
	}
	
	
	@Test
	@DisplayName("Testing Save Function")
	public void saveTest() {
		var collection = new DVDCollection();
		collection.loadData("dvddata.txt");
		collection.removeDVD("STAR TREK");
		collection.save();
		
		var savedCollection = new DVDCollection();
		savedCollection.loadData("dvddata.txt");

		// fix "dvddata.txt" to original State 
		try {
			FileWriter writeFile = new FileWriter("dvddata.txt");
			writeFile.write("ANGELS AND DEMONS,PG-13,138\n"
					+ "STAR TREK,R,127\n"
					+ "UP,PG,96"
					+ "\n\n");
			writeFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals("\nnumdvds = 2\n\n"
				+ "dvdArray.length = 7"
				+ "\n\ndvdArray[0] = ANGELS AND DEMONS/PG-13/138min\n\n"
				+ "dvdArray[1] = UP/PG/96min\n\n"
				, savedCollection.toString());
		System.out.println("Passed save tests");
	}
}