import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.awt.Graphics2D;

public class PlayGround {
    private ArrayList<Sprite> environment = new ArrayList<>();

    public PlayGround(String pathName) {
        try {
            final Image imageTree = ImageIO.read(new File("./img/tree.png"));
            final Image imageGrass = ImageIO.read(new File("./img/grass.png"));
            final Image imageRock = ImageIO.read(new File("./img/rock.png"));

            BufferedReader bufferedReader = new BufferedReader(new FileReader(pathName));
            String line = bufferedReader.readLine();
            int lineNumber = 0;

            while (line != null) {
                int columnNumber = 0;
                for (byte element : line.getBytes(StandardCharsets.UTF_8)) {
                    switch (element) {
                        case 'T' -> environment.add(new SolidSprite(
                                columnNumber * imageTree.getWidth(null),
                                lineNumber * imageTree.getHeight(null),
                                toBufferedImage(imageTree),
                                imageTree.getWidth(null),
                                imageTree.getHeight(null)));
                        case ' ' -> environment.add(new Sprite(
                                columnNumber * imageGrass.getWidth(null),
                                lineNumber * imageGrass.getHeight(null),
                                toBufferedImage(imageGrass),
                                imageGrass.getWidth(null),
                                imageGrass.getHeight(null)));
                        case 'R' -> environment.add(new SolidSprite(
                                columnNumber * imageRock.getWidth(null),
                                lineNumber * imageRock.getHeight(null),
                                toBufferedImage(imageRock),
                                imageRock.getWidth(null),
                                imageRock.getHeight(null)));
                    }
                    columnNumber++;
                }
                lineNumber++;
                line = bufferedReader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }
        BufferedImage bImage = new BufferedImage(
                img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bImage.createGraphics();
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();
        return bImage;
    }

    public ArrayList<Sprite> getSolidSpriteList() {
        ArrayList<Sprite> solidSprites = new ArrayList<>();
        for (Sprite sprite : environment) {
            if (sprite instanceof SolidSprite) {
                solidSprites.add(sprite);
            }
        }
        return solidSprites;
    }

    public ArrayList<Displayable> getSpriteList() {
        ArrayList<Displayable> displayableList = new ArrayList<>();
        for (Sprite sprite : environment) {
            displayableList.add(sprite);
        }
        return displayableList;
    }
}
