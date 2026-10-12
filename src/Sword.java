import java.awt.*;
import java.awt.event.KeyEvent;

public class Sword {
    //checks which arrow key is being pressed
    private int arrowKey;
    //checks how long sword has been out
    private long spawnTime;
    //sword max lifetime
    private long lifetime = 200;

    //dimensions of sword
    private int thickness = 20;
    private int length = 40;

    //constructor
    public Sword(int arrowKey) {
        this.arrowKey = arrowKey;
        this.spawnTime = System.currentTimeMillis();
    }

    //checks to see if the sword has expired or not
    public boolean isExpired() {
        return System.currentTimeMillis() - spawnTime > lifetime;
    }

    //gets boundaries of sword for collision depending on where the player is
    public Rectangle getBounds(CubePlayer player) {
        //getter methods from player!
        int px = player.getX();
        int py = player.getY();
        int pSize = player.getSize();

        //sword coordinates, width and height
        int sx = 0;
        int sy = 0;
        int sw = 0;
        int sh = 0;

        //gets sword coordinates depending on which arrow key is pressed
        if (arrowKey == KeyEvent.VK_UP) {
            sx = px + (pSize / 2) - (thickness / 2);
            sy = py - length;
            sw = thickness;
            sh = length;
        }
        else if (arrowKey == KeyEvent.VK_DOWN) {
            sx = px + (pSize / 2) - (thickness / 2);
            sy = py + pSize;
            sw = thickness;
            sh = length;
        }
        else if (arrowKey == KeyEvent.VK_LEFT) {
            sx = px - length;
            sy = py + (pSize / 2) - (thickness / 2);
            sw = length;
            sh = thickness;
        }
        else if (arrowKey == KeyEvent.VK_RIGHT) {
            sx = px + pSize;
            sy = py + (pSize / 2) - (thickness / 2);
            sw = length;
            sh = thickness;
        }

        return new Rectangle(sx, sy, sw, sh);
    }

    //draws the sword depending on its boundaries
    public void draw(Graphics g, CubePlayer player) {
        Rectangle r = getBounds(player);
        g.setColor(Color.RED);
        g.fillRect(r.x, r.y, r.width, r.height);
    }
}