import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class GameOverScreen extends JPanel {
    private Image backgroundImage;
    private Image titleImage;

    public GameOverScreen(Main main) {
        // Load the grass background image from ./img/grass.png
        try {
            backgroundImage = ImageIO.read(new File("./img/grass.png"));
        } catch (IOException e) {
            e.printStackTrace();
            backgroundImage = null; // Fallback in case of an error
        }

        // Load the WhiteCircle image for the "Game Over" background
        try {
            titleImage = ImageIO.read(new File("./img/WhiteCircle.png"));
        } catch (IOException e) {
            e.printStackTrace();
            titleImage = null; // Fallback in case of an error
        }

        setLayout(new BorderLayout());

        // Title panel setup with WhiteCircle background
        JPanel titlePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (titleImage != null) {
                    // Center the WhiteCircle image
                    int x = (getWidth() - titleImage.getWidth(null)) / 2;
                    int y = (getHeight() - titleImage.getHeight(null)) / 2;
                    g.drawImage(titleImage, x, y, this);
                }
            }
        };
        titlePanel.setOpaque(false); // Make the panel transparent

        JLabel gameOverLabel = new JLabel("Game Over", JLabel.CENTER);
        gameOverLabel.setFont(new Font("Serif", Font.BOLD, 48));
        gameOverLabel.setForeground(Color.RED);
        titlePanel.setLayout(new GridBagLayout()); // Center the label
        titlePanel.add(gameOverLabel);
        add(titlePanel, BorderLayout.CENTER);

        JButton restartButton = new JButton("Restart Game");
        restartButton.setFont(new Font("Arial", Font.PLAIN, 24));
        restartButton.setBackground(new Color(139, 69, 19)); // Brown button background
        restartButton.setForeground(Color.WHITE); // White text for contrast
        restartButton.setFocusPainted(false); // Remove the focus border

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    main.showGameScreen(); // Restart the game
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Make the panel transparent to show the background
        buttonPanel.add(restartButton);
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
