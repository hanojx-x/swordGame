import java.awt.*;

public abstract class Enemy {
    //enemy x and y coordinates, size and speed
    protected int x, y;
    protected int size = 20;
    protected int speed;

    //constructor
    public Enemy(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    //makes enemies head towards player
    public void chasePlayer(CubePlayer player, int panelWidth, int panelHeight) {
        //creates target coordinates for player dependent on their center compared to the enemy center
        int targetX = player.getX() + (player.getSize() / 2) - (size / 2);
        int targetY = player.getY() + (player.getSize() / 2) - (size / 2);

        //if enemy is to the left of player, enemy heads right and vice versa
        if (this.x < targetX)
            this.x += speed;
        else if (this.x > targetX)
            this.x -= speed;
        //if enemy is below player, enemy heads up and vice versa
        if (this.y < targetY)
            this.y += speed;
        else if (this.y > targetY)
            this.y -= speed;

        //sets boundaries for enemies
        if (x < 0)
            x = 0;
        if (x > panelWidth - size)
            x = panelWidth - size;
        if (y < 0)
            y = 0;
        if (y > panelHeight - size)
            y = panelHeight - size;
    }

    //abstract method used to draw the enemies
    public abstract void drawEnemy(Graphics g);

    //gets boundaries for collisions
    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }
}