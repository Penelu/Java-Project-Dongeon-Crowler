import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PatrollingEnemy extends Enemy {
    private double startX, endX;
    private boolean movingRight = true;
    private int frameIndex = 0; // Current frame index
    private int timeBetweenFrames = 200; // Time in milliseconds between frames
    private long lastFrameTime = 0; // Time when the last frame was updated
    private BufferedImage[] frames; // Array of frames extracted from the sprite sheet

    public PatrollingEnemy(double x, double y, BufferedImage spriteSheet, int frameWidth, int frameHeight, double endX) {
        super(x, y, null, frameWidth, frameHeight); // Set image to null since we use frames
        this.startX = x;
        this.endX = endX;

        // Extract frames from the sprite sheet
        int frameCount = spriteSheet.getWidth() / frameWidth;
        this.frames = new BufferedImage[frameCount];
        for (int i = 0; i < frameCount; i++) {
            frames[i] = spriteSheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
        }
    }

    @Override
    public void move(ArrayList<SolidSprite> environment, DynamicSprite hero) {
        double speed = getSpeed();

        // Update position
        if (movingRight) {
            x += speed;
            if (x >= endX) {
                movingRight = false; // Change direction
            }
        } else {
            x -= speed;
            if (x <= startX) {
                movingRight = true; // Change direction
            }
        }

        // Update animation frame based on time
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFrameTime >= timeBetweenFrames) {
            frameIndex = (frameIndex + 1) % frames.length; // Loop through frames
            lastFrameTime = currentTime;
        }
    }

    @Override
    public void draw(Graphics g) {
        // Draw the current frame
        BufferedImage currentFrame = frames[frameIndex];
        g.drawImage(currentFrame, (int) x, (int) y, (int) (x + width), (int) (y + height),
                0, 0, currentFrame.getWidth(), currentFrame.getHeight(), null);
    }
}
