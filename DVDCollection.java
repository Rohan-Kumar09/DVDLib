import java.io.*;
import java.util.Scanner;

/* 
 * DVDCollection is a collection on DVD objects.
 * It contains methods for:
 * adding/modifying, 
 * removing,
 * printing total runtime of all DVDs, 
 * getting them by rating,
 * loading DVD collection from a file, 
 * saving DVD collection to a file.
 * 
 */


public class DVDCollection {
	// Attributes:
	private int numdvds; // Number of DVDs in the Collection at any time.
	private DVD[] dvdArray; // Array of DVDs
	private String sourceName; // file with DVD data
	private boolean modified; // tells us if the DVD collection was modified since last save
	private String[] validRatings = {"PG-13", "R", "PG"}; // valid ratings array
	
	// Default Constructor
	public DVDCollection() {
		numdvds = 0;
		dvdArray = new DVD[7];
		sourceName = "dvddata.txt"; // default file name
		modified = false;
	}
	
	/*
	 * Function: Output all the DVD objects within the DVDCollection
	 * as strings in the client-desired format, along with the values for
	 * numdvds and length of the current array, and return them.
	 * Precondition: Object has been constructed.
	 * Postcondition: Object is returned as a string.
	 * */
	public String toString() {
		String fileInfo = "\nnumdvds = " + numdvds + "\n\ndvdArray.length = " 
		+ dvdArray.length + "\n\n";
		for (int i = 0; i < numdvds; i++) {
			fileInfo += "dvdArray[";
			fileInfo += i;
			fileInfo += "] = ";
			fileInfo += dvdArray[i].toString();
			fileInfo += "\n\n";
		}
		return fileInfo;
	}

	/*
	 * Function: Given title, rating and runningTime (all 3 as Strings).
	 * Make a new DVD object and insert it into the DVDCollection Array in alphabetical order.
	 * If a DVD object with the same title exists within the DVDCollection
	 * then modify it's rating and runningTime to the given values.
	 * Precondition: Title, rating and runningTime are all of type String.
	 * Postcondition: The array is either updated with the new rating and runningTime or
	 * a new DVD object is added to the array in alphabetical order.
	 */
	public void addOrModifyDVD(String title, String rating, String runningTime) {
		int runTime = -1; // Initialize runTime
		
		try { // Try to convert runnintTime(String) to an Integer.
			runTime = Integer.parseInt(runningTime);
		} catch (NumberFormatException e) {
			return; // If conversion not possible then exit and do nothing.
		} 
		
		if (runTime < 0) {
			return; // return if runTime is negative
		}
		
		if (!contains(validRatings, rating)) { // check additional private function
			return; // return if rating is not valid
		}
		
		title = title.toUpperCase(); // make title upper case
		rating = rating.toUpperCase(); // make rating upper case
		
		// code for Modifying DVDs
		for (int i = 0; i < numdvds; i++) {
			if (title.equals(dvdArray[i].getTitle())) { // Find the title
				dvdArray[i].setRating(rating); // modify rating
				dvdArray[i].setRunningTime(runTime); // modify runningTime
				modified = true;
				return; // exit function
			}
		}
		
		// code for Adding DVDs
		DVD currDVD = new DVD(title, rating, runTime); // create a new DVD object
		
		// Check if there is space for 1 more DVD.
		if (dvdArray.length == numdvds) { // if out of space
			// double the array
			dvdArray = doubleArray(dvdArray); // check additional private function
		}
		
		DVD tempDVD = null; // temporary DVD object for swapping
		boolean hasBeenPlaced = false; // tells us if the new object has found it's placed or not
		
		for (int i = 0; i < numdvds; i++) {
			if (dvdArray[i] == null) { // if null is reached
				break; // break out of the loop
			}
			else if (!hasBeenPlaced && canPlaceString(title, dvdArray[i].getTitle())) { // check additional private function
				// Execute if DVD has not been placed AND "i" is the right place to insert
				// boolean for skipping redundant function calls
				hasBeenPlaced = true;
			}
			if (hasBeenPlaced) { // swap down the array
				tempDVD = dvdArray[i]; // 1
				dvdArray[i] = currDVD; // 2
				currDVD = tempDVD;     // 3
				
				// currDVD will have the next element to store, Demo -
				// 1 2 4 5   t = ? c = 3
				
				// 1 2 ? 5   t = 4 c = 3 // 1
				// 1 2 3 5   t = 4 c = ? // 2
				// 1 2 3 5   t = ? c = 4 // 3
				
				// 1 2 3 ?   t = 5 c = 4 // 1
				// 1 2 3 4   t = 5 c = ? // 2
				// 1 2 3 4   t = ? c = 5 // 3
				// ...To be continued...
			}
		}
		
		// for inserting the last element of the array.
		// for inserting the first element of the array if the array is empty.
		// and incrementing the number of DVDs.
		dvdArray[numdvds++] = currDVD;
		// ...Continued...
		// 1 2 3 4 5   t = ? c = ? //
		modified = true;
		return;
	}
	
	/*
	 * Function: Given the title of the DVD object as String, remove the DVD if it exists, 
	 * do nothing otherwise. decrement numdvds.
	 * Precondition: None
	 * Postcondition: DVD with title {title} is removed from DVDCollection's dvdArray. 
	 * if title doesn't exists nothing is done.
	 */
	
	public void removeDVD(String title) {
		boolean found = false; // boolean for skipping to code for "shifting down".
		for (int i = 0; i < numdvds; i++) {
			if (!found && title.toUpperCase().equals(dvdArray[i].getTitle())) {
				// if not found and title matches the one in array at position "i"
				// ran once
				found = true;
				--numdvds;
			}
			if (found) { // shift down if found
				dvdArray[i] = dvdArray[i+1];
			}
		}
		if (found) {
			modified = true;
		}
	}
	
	/*
	 * Function: Given rating return all DVD Objects with rating {rating} as a string.
	 * Precondition: rating is a String.
	 * Postcondition: All DVD Objects with rating {rating} are returned as line separated strings.
	 */
	public String getDVDsByRating(String rating) {
		String DVDsWithSameRating = "";
		for (int i = 0; i < numdvds; i++) {
			if (dvdArray[i].getRating().equals(rating.toUpperCase())) {
				DVDsWithSameRating += (dvdArray[i].toString() + "\n");
			}
		}
		return DVDsWithSameRating;
	}

	/*
	 * Function: Add all the running times from the array dvdArray and return their sum as Integer.
	 * Precondition: None.
	 * Postcondition: Total RunningTime is returned as an Integer.
	 */
	public int getTotalRunningTime() {
		int totalRunningTime = 0;
		for (int i = 0; i < numdvds; i++) {
			totalRunningTime += dvdArray[i].getRunningTime();
		}
		return totalRunningTime;
	}

	/*
	 * Function: Given a file name load it's contents into the dvdArray, double the array as needed.
	 * Precondition: Filename is valid, the file is formatted in the client-desired way.
	 * Postcondition: dvdArray has the contents of the file.
	 */
	public void loadData(String filename) {
		File file = new File(filename);
		sourceName = filename; // for save
		try {
			Scanner readFile = new Scanner(file);
			while (readFile.hasNextLine()) {
				if (dvdArray.length == numdvds) { // if out of space, double the array
					dvdArray = doubleArray(dvdArray); // additional private function
				}
				String data = readFile.nextLine();
				if (data != "") { // for empty lines in the file
					String[] dvdData = data.split(",");
					addOrModifyDVD(dvdData[0], dvdData[1], dvdData[2]);
				}
			}
			readFile.close();
		} catch (FileNotFoundException e) {
			// do nothing, array remains empty
		}
	}
	
	/*
	 * Function: Save the contents of dvdArray into the same file called by load (overwriting it).
	 * Precondition: loadData has been called on the file.
	 * Postcondition: the file {sourceName} has the contents of dvdArray. 
	 */
	
	public void save() {
		try {
			FileWriter writeFile = new FileWriter(sourceName);
			for (int i = 0; i < numdvds; i++) {
				writeFile.write(dvdArray[i].toString() // get DVD as string
										   .replaceAll("/", ",")
										   .replace("min", "") // remove the min
										   + "\n");
			}
			writeFile.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	// Additional private helper methods go here:
	
	/*
	 * Function: Given a string to add and a string to evaluate it with.
	 * return true if s_add is less than s_eval, false otherwise.
	 * 
	 * NOTE: If they are equal return true is the right choice if we want 2 or more titles that are the same,
	 * however this code will never run as the entire add portion of the addOrModifyDVD
	 * function will be skipped if the strings are equal (we'll just modify the existing DVD).
	 * 
	 * Precondition: input must be of type String.
	 * Postcondition: True is returned if s_add < s_eval, false otherwise, (== will never run by design).
	 */
	
	private boolean canPlaceString(String s_add, String s_eval) {
		// chose the minimum length among both strings.
		int length = s_add.length() <= s_eval.length() ? s_add.length() : s_eval.length();
		for (int i = 0; i < length; i++) {
			if (s_add.charAt(i) < s_eval.charAt(i)) {
				return true; // s_add is smaller than s_eval
			}
			else if (s_add.charAt(i) > s_eval.charAt(i)) {
				return false; // s_add is not smaller than s_eval
			}
		}
		return false; // never runs so doesn't matter
	}
	
	/* 
	 * Function: Doubles a given array.
	 * precondition: an array of non-zero size is given.
	 * postcondition: A new array of double the size is returned.
	 * 
	 */
	private DVD[] doubleArray(DVD[] array) {
		DVD[] newDVDArray = new DVD[array.length * 2]; // double the array size
		for (int i = 0; i < array.length; i++) {
			newDVDArray[i] = array[i]; // move all the old elements into new array
		}
		return newDVDArray;
	}
	
	/*
	 * Function: Given an array and a String element return true
	 * if the element is in the array, false otherwise.
	 * Precondition: None
	 * Postcondition: A boolean value of true is returned
	 * if the value was found, false otherwise.
	 * 
	 */
	private boolean contains(String[] array, String element) {
		for (String ele: array) {
			if (ele.equals(element)) {
				return true;
			}
		}
		return false;
	}
}
