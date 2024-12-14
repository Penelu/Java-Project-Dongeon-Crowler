import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

public class PhysicEngine implements Engine {
    private ArrayList<DynamicSprite> movingSprites = new ArrayList<>();
    private ArrayList<Enemy> enemyList = new ArrayList<>();
    private ArrayList<SolidSprite> environment = new ArrayList<>();
    private Main main; // Reference to Main for triggering game over

    public PhysicEngine(Main main) {
        this.main = main;
    }

    public void addMovingSprite(DynamicSprite sprite) {
        movingSprites.add(sprite);
    }

    public void addEnemy(Enemy enemy) {
        enemyList.add(enemy);
    }

    public void setEnvironment(ArrayList<SolidSprite> environment) {
        this.environment = environment;
    }

    @Override
    public void update() {
        // Update hero and other moving sprites
        for (DynamicSprite sprite : movingSprites) {
            sprite.moveIfPossible(environment);
        }

        // Update enemy movement and logic
        for (Enemy enemy : enemyList) {
            if (!movingSprites.isEmpty()) {
                enemy.move(environment, movingSprites.get(0)); // Assuming the first sprite is the hero

                // Check for collision with the hero
                if (checkCollision(enemy, movingSprites.get(0))) {
                    triggerGameOver(); // Game over when collision detected
                }
            }
        }
    }

    private boolean checkCollision(Sprite a, Sprite b) {
        Rectangle2D.Double hitBoxA = new Rectangle2D.Double(a.getX(), a.getY(), a.getWidth(), a.getHeight());
        Rectangle2D.Double hitBoxB = new Rectangle2D.Double(b.getX(), b.getY(), b.getWidth(), b.getHeight());
        return hitBoxA.intersects(hitBoxB);
    }

    private void triggerGameOver() {
        SwingUtilities.invokeLater(() -> {
            try {
                main.showGameOverScreen(); // Switch to game over screen
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
