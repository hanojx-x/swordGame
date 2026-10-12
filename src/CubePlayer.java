import java.awt.*;

public class CubePlayer {
    //x and y coordinates of player
    private int x, y;
    private int size;

    //constructor
    public CubePlayer(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    //handles movement and prevents moving past border
    public void move(int dx, int dy, int frameWidth, int frameHeight) {
        //moves player based on the direction parameters
        x += dx;
        y += dy;

        //prevents player from leaving the screen
        if (x < 0)
            x = 0;
        if (y < 0)
            y = 0;
        if (x + size > frameWidth)
            x = frameWidth - size;
        if (y + size > frameHeight)
            y = frameHeight - size;
    }

    //gets boundaries of player for collision and sword generation
    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }

    //draws player
    public void draw(Graphics g) {
        //fill the body
        g.setColor(Color.BLUE);
        g.fillRect(x, y, size, size);
    }

    //getter methods
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getSize() {
        return size;
    }
}