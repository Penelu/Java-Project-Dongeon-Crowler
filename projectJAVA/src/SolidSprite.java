import java.awt.Image;
import java.awt.image.BufferedImage;

public class SolidSprite extends Sprite {
    public SolidSprite(double x, double y, Image image, double width, double height) {
        super(x, y, (BufferedImage) image, width, height);
    }
}
