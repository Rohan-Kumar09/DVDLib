import javax.swing.*;
import java.awt.*;

public class test2 {

    public static void main(String[] args) {
        // Create a JFrame for the main window
        JFrame frame = new JFrame("DVD Collection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        // Create the main JPanel that will hold the info and grid panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Add information panel at the top
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS)); // Vertical arrangement
        infoPanel.add(new JLabel("DVD Collection Information:"));
        infoPanel.add(new JLabel("Total DVDs: 4"));
        infoPanel.add(new JLabel("Categories: Action, Sci-Fi, Drama"));
        
        // Create a panel with GridLayout for DVD titles and images
        JPanel gridPanel = new JPanel(new GridLayout(0, 2, 10, 10)); // 2 columns, 0 rows, with 10px gaps

        // Sample DVD titles and image paths
        String[] dvdTitles = {
            "Inception",
            "The Matrix",
            "Interstellar",
            "The Godfather"
        };

        // Sample image paths (make sure to use valid image paths)
        String[] imagePaths = {
            "image1.jpg",     // Replace with your image path
            "image2.jpg",        // Replace with your image path
            "image3.jpg",  // Replace with your image path
            "image4.jpg"      // Replace with your image path
        };

        // Loop to add each DVD title and corresponding image to the grid
        for (int i = 0; i < dvdTitles.length; i++) {
            // Create a label for the DVD title
            JLabel titleLabel = new JLabel(dvdTitles[i]);

            // Load and resize the image
            ImageIcon originalIcon = new ImageIcon(imagePaths[i]);
            Image originalImage = originalIcon.getImage();
            Image resizedImage = originalImage.getScaledInstance(100, 150, Image.SCALE_SMOOTH); // Resize as needed
            ImageIcon resizedIcon = new ImageIcon(resizedImage);

            // Create a label for the image
            JLabel imageLabel = new JLabel(resizedIcon);

            // Add the title and image to the grid panel
            gridPanel.add(titleLabel);
            gridPanel.add(imageLabel);
        }

        // Wrap the gridPanel in a JScrollPane to make it scrollable
        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        // Add infoPanel at the top and the scrollPane below it
        mainPanel.add(infoPanel, BorderLayout.NORTH); // Info panel at the top
        mainPanel.add(scrollPane, BorderLayout.CENTER); // Scroll pane in the center

        // Add the main panel to the frame
        frame.add(mainPanel);
        
        // Set the frame visible
        frame.setVisible(true);
    }
}
