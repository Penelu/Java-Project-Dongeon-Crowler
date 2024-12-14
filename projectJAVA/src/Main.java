import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.ArrayList;

public class Main {
    private JFrame displayZoneFrame;
    private RenderEngine renderEngine;
    private PhysicEngine physicEngine;
    private GameEngine gameEngine;
    private TitleScreen titleScreen;

    public Main() throws Exception {
        // Set up JFrame
        displayZoneFrame = new JFrame("Java Dungeon Crawler");
        displayZoneFrame.setSize(400, 600); // Adjusted size for better layout
        displayZoneFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize the title screen
        titleScreen = new TitleScreen(this);
        displayZoneFrame.add(titleScreen);
        displayZoneFrame.setVisible(true);
    }

    public void showGameScreen() throws Exception {
        displayZoneFrame.getContentPane().removeAll();

        // Create the game environment
        renderEngine = new RenderEngine();
        physicEngine = new PhysicEngine(this); // Pass reference to Main
        DynamicSprite hero = new DynamicSprite(200, 300,
                ImageIO.read(new File("./img/heroTileSheetLowRes.png")), 48, 50);
        gameEngine = new GameEngine(hero);

        PlayGround playground = new PlayGround("./data/level1.txt");
        for (Displayable sprite : playground.getSpriteList()) {
            renderEngine.addToRenderList(sprite);
        }
        ArrayList<SolidSprite> solidSpritesList = new ArrayList<>();
        for (Sprite sprite : playground.getSolidSpriteList()) {
            if (sprite instanceof SolidSprite) {
                solidSpritesList.add((SolidSprite) sprite);
            }
        }
        physicEngine.setEnvironment(solidSpritesList);
        renderEngine.addToRenderList(hero);
        physicEngine.addMovingSprite(hero);

        // Create and add enemies to the game
        PatrollingEnemy patrollingEnemy = new PatrollingEnemy(100, 200,
                ImageIO.read(new File("./img/enemyPatrolSprite.png")), 32, 32, 300); // Assuming each frame is 32x32
        physicEngine.addEnemy(patrollingEnemy);
        renderEngine.addToRenderList(patrollingEnemy);

        // Start timers for engines
        Timer renderTimer = new Timer(50, (time) -> renderEngine.update());
        Timer gameTimer = new Timer(50, (time) -> gameEngine.update());
        Timer physicTimer = new Timer(50, (time) -> physicEngine.update());

        renderTimer.start();
        gameTimer.start();
        physicTimer.start();

        // Add key listener and request focus
        displayZoneFrame.getContentPane().add(renderEngine);
        displayZoneFrame.addKeyListener(gameEngine);
        displayZoneFrame.revalidate();
        displayZoneFrame.repaint();
        displayZoneFrame.requestFocus();
    }

    public void showGameOverScreen() throws Exception {
        displayZoneFrame.getContentPane().removeAll();
        displayZoneFrame.add(new GameOverScreen(this));
        displayZoneFrame.revalidate();
        displayZoneFrame.repaint();
    }

    public static void main(String[] args) throws Exception {
        new Main();
    }
}
