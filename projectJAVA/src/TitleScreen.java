import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class TitleScreen extends JPanel {
    private JButton startButton;
    private Image backgroundImage;
    private Image titleImage; // New image for the title label background

    public TitleScreen(Main main) {
        // Load the grass background image from ./img/grass.png
        try {
            backgroundImage = ImageIO.read(new File("./img/grass.png"));
        } catch (IOException e) {
            e.printStackTrace();
            backgroundImage = null; // Fallback in case of an error
        }

        // Load the WhiteCircle image for the title background
        try {
            titleImage = ImageIO.read(new File("./img/WhiteCircle.png"));
        } catch (IOException e) {
            e.printStackTrace();
            titleImage = null; // Fallback in case of an error
        }

        setLayout(new BorderLayout());

        // Title panel setup with background image
        JPanel titlePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (titleImage != null) {
                    // Center the title image
                    int x = (getWidth() - titleImage.getWidth(null)) / 2;
                    int y = (getHeight() - titleImage.getHeight(null)) / 2;
                    g.drawImage(titleImage, x, y, this);
                }
            }
        };
        titlePanel.setOpaque(false); // Make the panel transparent

        JLabel titleLabel = new JLabel("Dungeon Crawler Game", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 36));
        titleLabel.setForeground(new Color(139, 69, 19)); // Brown color for text
        titlePanel.setLayout(new GridBagLayout()); // Center the label
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.CENTER);

        // Start button setup
        startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.PLAIN, 24));
        startButton.setBackground(new Color(139, 69, 19)); // Brown button background
        startButton.setForeground(Color.WHITE); // White text for contrast
        startButton.setFocusPainted(false); // Remove the focus border

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    main.showGameScreen(); // Switch to the game screen
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // Button panel setup
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Make the panel transparent to show the background
        buttonPanel.add(startButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            // Draw the background image, tiled to fill the entire panel
            for (int x = 0; x < getWidth(); x += backgroundImage.getWidth(null)) {
                for (int y = 0; y < getHeight(); y += backgroundImage.getHeight(null)) {
                    g.drawImage(backgroundImage, x, y, this);
                }
            }
        } else {
            // Fallback color if the image fails to load
            g.setColor(new Color(144, 238, 144)); // Light green color
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
