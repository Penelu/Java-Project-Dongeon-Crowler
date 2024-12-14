import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RenderEngine extends JPanel implements Engine {
    private ArrayList<Displayable> renderList;

    public RenderEngine() {
        renderList = new ArrayList<>();
    }

    public void addToRenderList(Displayable displayable) {
        renderList.add(displayable);
    }

    @Override
    public void update() {
        repaint();  // Triggers a repaint of the panel
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Displayable displayable : renderList) {
            displayable.draw(g);
        }
    }

    public ArrayList<SolidSprite> getSolidSpriteList() {
        ArrayList<SolidSprite> solids = new ArrayList<>();
        for (Displayable d : renderList) {
            if (d instanceof SolidSprite) {
                solids.add((SolidSprite) d);
            }
        }
        return solids;
    }
}
