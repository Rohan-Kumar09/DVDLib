import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.IOException;

/**
 *  This class is an implementation of DVDUserInterface
 *  that uses JOptionPane to display the menu of command choices. 
 */

public class DVDGUI implements DVDUserInterface {
	
	private final String defaultImage = "Default.png";
	private DVDCollection dvdlist;
		 
	public DVDGUI(DVDCollection dl) {
		dvdlist = dl;
	}

	public void processCommands() {
		
		JFrame frame = new JFrame("DVD Collection");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// main panel holds buttons and prompt
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		// panel holds buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(0, 1)); // 0 row, 1 column Layout for Buttons
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(40, 7, 10, 7)); // top, left, bottom, right
		
		// topPanel holds prompt
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		JLabel prompt = new JLabel("Chose a command");
		prompt.setFont(new Font("Courier New", Font.PLAIN, 50));
		topPanel.add(prompt);
		
		String[] commands = {"Add/Modify DVD",
							"Remove DVD",
							"Get DVDs By Rating",
							"Get Total Running Time",
							"Show DVD Collection",
							"Exit and Save"};
		
		// for loop for adding event listeners
		for (String command: commands) {
			JButton button = new JButton(command);
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					commandHandler(command);
				}
			});
			button.setFont(new Font("Courier New", Font.BOLD, 15));
			button.setPreferredSize(new Dimension(200, 50));
			buttonPanel.add(button); // add buttons to buttonPanel
		}
		
		// add prompt and buttons to main panel
		mainPanel.add(topPanel);
		mainPanel.add(buttonPanel);
		
		// add mainPanel to frame
		frame.add(mainPanel);
		frame.pack(); // wrap around
		frame.setLocationRelativeTo(null); // centralize the frame when shown
		frame.setVisible(true); // make it visible
		
		// Simpler code using JOptionPane
//		int choice;
//		do {
//			choice = JOptionPane.showOptionDialog(null,
//					 "Select a command", 
//					 "DVD Collection", 
//					 JOptionPane.YES_NO_CANCEL_OPTION, 
//					 JOptionPane.PLAIN_MESSAGE, 
//					 null, 
//					 commands,
//					 commands[commands.length - 1]);
//		 
//			switch (choice) {
//			 	case 0: doAddOrModifyDVD(); break;
//			 	case 1: doRemoveDVD(); break;
//			 	case 2: doGetDVDsByRating(); break;
//			 	case 3: doGetTotalRunningTime(); break;
//			 	case 4: showDVDCollection(); break;
//			 	case 5: doSave(); break;
//			 	default:  // do nothing
//				}
//			} while (choice != commands.length - 1);
//			System.exit(0); // exit program
	}
	
	private void commandHandler(String command) {
		switch (command) {
	        case "Add/Modify DVD":
	            doAddOrModifyDVD();
	            break;
	        case "Remove DVD":
	            doRemoveDVD();
	            break;
	        case "Get DVDs By Rating":
	            doGetDVDsByRating();
	            break;
	        case "Get Total Running Time":
	            doGetTotalRunningTime();
	            break;
	        case "Show DVD Collection":
	            showDVDCollection();
	            break;
	        case "Exit and Save":
	            doSave();
	            System.exit(0); // exit program
	            break;
        	}
	}
	
	private void doAddOrModifyDVD(String... s) { // 0 or more Strings
		JFrame frame = new JFrame("Add/Modify DVD");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel mainPanel = new JPanel(new BorderLayout()); // for NSEW and Center
		JPanel topPanel = new JPanel(new GridLayout(0, 2)); // for Fields
		JPanel buttonPanel = new JPanel(); // for submit Button
		
		JLabel titleLabel = new JLabel("Enter title: "); // prompt
		JTextField titleText = new JTextField(); // field
		if (s.length == 0) {
			topPanel.add(titleLabel);
			topPanel.add(titleText);
		}
		else {
			topPanel.add(new JLabel("Modifying: "));
			topPanel.add(new JLabel(s[0]));
		}
		
		JLabel ratingLabel = new JLabel("Enter rating: ");
		JTextField ratingText = new JTextField();
		topPanel.add(ratingLabel);
		topPanel.add(ratingText);
		
		JLabel runTimeLabel = new JLabel("Enter running time: ");
		JTextField runTimeText = new JTextField();		
		topPanel.add(runTimeLabel);
		topPanel.add(runTimeText);
		
		JButton button = new JButton("Add/Modify");
		buttonPanel.add(button);
		
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		frame.add(mainPanel);
		
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
		
		button.addActionListener(e -> {			
			// retrieve data
			String title;
			if (s.length != 0) {
				title = s[0];
			}
			else {				
				title = titleText.getText().toUpperCase();
			}
			String rating = ratingText.getText().toUpperCase();
			String time = runTimeText.getText();
			
			if (title == null || rating == null || time == null) {
				return;		// dialog was cancelled
			}
			
			// Add or modify the DVD (assuming the rating and time are valid
			dvdlist.addOrModifyDVD(title, rating, time);
			// Display current collection to the console for debugging
			JOptionPane.showMessageDialog(null,
					"Adding/Modifying: " + title + "," + rating + "," + time,
					"DVD Collection",
					JOptionPane.PLAIN_MESSAGE);
			showDVDCollection();
			frame.dispose(); // dispose of the frame after displaying info
		});
	}
	
	 private void doRemoveDVD(String... s) {
		// Request the title
		String title;
		if (s.length == 0) {
			title = JOptionPane.showInputDialog("Enter title");
			if (title == null) {
				return;		// dialog was cancelled
			}
			title = title.toUpperCase();
		}
		else {			
			title = s[0];
		}
		// Remove the matching DVD if found
		dvdlist.removeDVD(title);
		// Display current collection to the console for debugging
		JOptionPane.showMessageDialog(null,
				"Removing: " + title,
				"DVD Collection",
				JOptionPane.PLAIN_MESSAGE);
		showDVDCollection();
	}
	
	private void doGetDVDsByRating() {
		// Request the rating
		String rating = JOptionPane.showInputDialog("Enter rating");
		if (rating == null) {
			return;		// dialog was cancelled
		}
		rating = rating.toUpperCase();
		showDVDCollection(rating);
	}
	
	private void doGetTotalRunningTime() {
		JOptionPane.showMessageDialog(null,
				"Total Running Time of DVDs:\n" + dvdlist.getTotalRunningTime() + " mins",
				"DVD Collection",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void showDVDCollection(String... s) {
		
		// text has DVD Collection's info
		String[] text = dvdlist.toString().split("\n\n");
		
		// jpgFiles has all the jpg files in current Directory
		ArrayList<String> jpgFiles = new ArrayList<>();
		ArrayList<JButton> buttons = new ArrayList<>(); // list of buttons
		ArrayList<JButton> delButtons = new ArrayList<>(); // list of delButtons
		
		Path currentDirectory = Paths.get("."); // get the current directory
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(currentDirectory, "*.jpg")) {
			for (Path filePath : stream) {
				// files are read in alphabetical order
				jpgFiles.add(filePath.getFileName().toString()); // add all files to list.
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		JFrame frame = new JFrame("DVD Collection");
		frame.setSize(new Dimension(800, 700));
		JPanel mainPanel = new JPanel(); // main screen
		mainPanel.setLayout(new BorderLayout()); // for regions NSEW and Center
		
		JPanel infoPanel = new JPanel(); // info screen
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS)); // align vertically
		infoPanel.add(new JLabel("<html>" + text[0] + "<br/>" + text[1] + "<html/>"));
		
		// get the number of DVD's
		String[] temp = text[0].split(" "); // text[0] = numDVD
		int collectionSize = Integer.valueOf(temp[2]); // numDVD = {a number}
		
		// gridPanel for DVD Images and information
		JPanel gridPanel = new JPanel(new GridLayout(0, 4)); // 0 rows, 4 columns
		gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // padding of 10
				
		for (int i = 0; i < collectionSize; i++) {
			// split info using "=" as delimiter
			temp = text[i + 2].toString().split("="); // array starts from index 2
			temp = temp[1].split("/");
			
			String dvdTitle = temp[0].trim(); // 0 index is title name
			String dvdRating = temp[1].trim(); // 1 index is rating
			String dvdRunTime = temp[2].trim(); // 2 index is run time
			
			// jpgFiles are in ascending order, DVD list is also in ascending order.
			ImageIcon imageIcon = null;
			// if file name exists in jpgFiles then add it to imageIcon
			for (String jpg: jpgFiles) {
				// find the right image for each DVD.
				if (jpg.toUpperCase().replaceAll("\\.JPG", "").equals(dvdTitle.toUpperCase())) {
					imageIcon = new ImageIcon(jpg);
					break;
				}
			}
			if (imageIcon == null) { // if the image doesn't exist give it default image
				imageIcon = new ImageIcon(defaultImage);
			}
			Image image = imageIcon.getImage()
					.getScaledInstance(100, 150, Image.SCALE_SMOOTH); // scale image, better visuals
			ImageIcon resizedIcon = new ImageIcon(image);
			
			JLabel imageLabel = new JLabel(resizedIcon);
			
			buttons.add(new JButton("Modify"));
			buttons.get(i).setBackground(new Color(70, 130, 180));
			buttons.get(i).setForeground(Color.WHITE);
			buttons.get(i).setFont(new Font("Courier New", Font.BOLD, 20));
			buttons.get(i).setBorder(new LineBorder(Color.BLACK, 2));
			
			buttons.get(i).addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					doAddOrModifyDVD(dvdTitle);
					frame.dispose();
					// dispose this frame let add function display new frame with updated info.
				}
			});
			
			delButtons.add(new JButton("Remove"));
			delButtons.get(i).setBackground(new Color(70, 130, 180));
			delButtons.get(i).setForeground(Color.WHITE);
			delButtons.get(i).setFont(new Font("Courier New", Font.BOLD, 20));
			delButtons.get(i).setBorder(new LineBorder(Color.BLACK, 2));
			
			delButtons.get(i).addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					doRemoveDVD(dvdTitle);
					frame.dispose();
				}
			});
			
			String formatedDVDInfo = "Title: " + dvdTitle + "<br>" + "Rating: " + dvdRating 
					+ "<br>" + "Run Time: " + dvdRunTime;
			JLabel dvdInfo = new JLabel("<html>" + formatedDVDInfo + "</html>");
			
			if (s.length != 0 && !dvdRating.equals(s[0])) {
				// if rating function called with a parameter and the parameter equals DVD rating
				// don't add it to the gridPanel.
				continue; // for sorting out using the get DVD by rating function
			}
			
			gridPanel.add(dvdInfo); // add info to first column
			gridPanel.add(imageLabel); // add image to 2nd column
			gridPanel.add(buttons.get(i)); // add button to the 3rd column
			gridPanel.add(delButtons.get(i)); // add remove button to the 4th column
		}
		
		// make the grid scroll enabled
		JScrollPane scrollPane = new JScrollPane(gridPanel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20); // increase scroll speed
		
		// Add to main panel
		mainPanel.add(infoPanel, BorderLayout.NORTH); // Info at top
		mainPanel.add(scrollPane, BorderLayout.CENTER);
		
		frame.add(mainPanel);
		frame.setLocationRelativeTo(null); // centralize the frame when shown
		frame.setVisible(true); // make frame visible !
	}
	
	private void doSave() {
		dvdlist.save();
	}
}





















