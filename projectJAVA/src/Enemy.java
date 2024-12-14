import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Enemy extends DynamicSprite {
    public Enemy(double x, double y, BufferedImage image, double width, double height) {
        super(x, y, image, width, height);
    }

    // Abstract method for enemy behavior
    public abstract void move(ArrayList<SolidSprite> environment, DynamicSprite hero);
}
