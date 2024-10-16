import javax.swing.*;
import java.awt.*;

public class test {

    public static void main(String[] args) {
        // Create a JFrame to hold the content
        JFrame frame = new JFrame("DVD Collection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        
        // Create a JPanel with GridLayout
        JPanel panel = new JPanel(new GridLayout(0, 2)); // 0 rows, 2 columns

        // Sample DVD titles
        String[] dvdTitles = {
            "dracula",
            "avatar",
            "light",
            "after"
        };

        // Sample image paths (make sure to use valid image paths)
        String[] imagePaths = {
            "image1.jpg",     // Replace with your image path
            "image2.jpg",        // Replace with your image path
            "image3.jpg",  // Replace with your image path
            "image4.jpg"      // Replace with your image path
        };

        // Loop through the DVD titles and images
        for (int i = 0; i < dvdTitles.length; i++) {
            // Create a JLabel for the DVD title
            JLabel titleLabel = new JLabel(dvdTitles[i]);

            // Load and resize the image
            ImageIcon originalIcon = new ImageIcon(imagePaths[i]); // Load image
            Image originalImage = originalIcon.getImage();
            Image resizedImage = originalImage.getScaledInstance(100, 150, Image.SCALE_SMOOTH); // Set size as needed
            ImageIcon resizedIcon = new ImageIcon(resizedImage);
            JLabel imageLabel = new JLabel(resizedIcon);

            // Add the title and image to the panel
            panel.add(titleLabel);
            panel.add(imageLabel);
        }

        // Add the panel to the frame
        frame.add(panel);
        frame.setVisible(true);
    }
}
