
/*
 * A self explanatory file. But just in case:
 * This file consists the class DVD. DVD objects are the contents of
 * DVDCollection. DVD objects have a title, rating and runningTime.
 * These three attributes are retrievable by their respective getter methods.
 * These methods can also be set through their respective setter methods.
 * These methods must be specified during the objects initialization through 
 * the constructor.
 * 
 * */

public class DVD {
	// Attributes:
	private String title;		// Title of this DVD
	private String rating;		// Rating of this DVD
	private int runningTime;	// Running time of this DVD in minutes

	public DVD(String dvdTitle, String dvdRating, int dvdRunningTime) {
		title = dvdTitle;
		rating = dvdRating;
		runningTime = dvdRunningTime;
	}
	
	// Getter methods
	public String getTitle() {return title;}
	public String getRating() {return rating;}
	public int getRunningTime() {return runningTime;}
	
	// Setter methods
	public void setTitle(String newTitle) {this.title = newTitle;}
	public void setRating(String newRating) {this.rating = newRating;}
	public void setRunningTime(int newRunningTime) {this.runningTime = newRunningTime;}
	
	// returns the string in the client-desired string format.
	public String toString() {
		return title + "/" + rating + "/" + runningTime + "min";
	}
}