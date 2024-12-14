import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DynamicSprite extends SolidSprite {
    private boolean isWalking = true;
    private double speed = 5;
    private Direction direction = Direction.SOUTH;
    private final int spriteSheetNumberOfColumn = 10;
    private int timeBetweenFrame = 200; // in milliseconds

    public DynamicSprite(double x, double y, BufferedImage image, double width, double height) {
        super(x, y, image, width, height);
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void draw(Graphics g) {
        int index = (int) ((System.currentTimeMillis() / timeBetweenFrame) % spriteSheetNumberOfColumn);
        int attitude = direction.getFrameLineNumber();
        g.drawImage(image, (int) x, (int) y, (int) (x + width), (int) (y + height),
                index * (int) width, attitude * (int) height,
                (index + 1) * (int) width, (attitude + 1) * (int) height, null);
    }

    public boolean isMovingPossible(ArrayList<SolidSprite> environment) {
        Rectangle2D.Double hitBox = new Rectangle2D.Double(x, y, width, height);

        // Check new position based on the current direction
        switch (direction) {
            case NORTH -> hitBox.y -= speed;
            case SOUTH -> hitBox.y += speed;
            case EAST -> hitBox.x += speed;
            case WEST -> hitBox.x -= speed;
        }

        for (SolidSprite solid : environment) {
            Rectangle2D.Double solidHitBox = new Rectangle2D.Double(solid.x, solid.y, solid.width, solid.height);
            if (hitBox.intersects(solidHitBox)) {
                return false; // Collision detected
            }
        }
        return true; // No collision detected
    }

    public void moveIfPossible(ArrayList<SolidSprite> environment) {
        if (isMovingPossible(environment)) {
            switch (direction) {
                case NORTH -> y -= speed;
                case SOUTH -> y += speed;
                case EAST -> x += speed;
                case WEST -> x -= speed;
            }
        }
    }

    // Getter for speed to allow subclasses to access it
    protected double getSpeed() {
        return speed;
    }

    // Optionally, add a setter if you want to allow changing the speed:
    protected void setSpeed(double speed) {
        this.speed = speed;
    }
}
