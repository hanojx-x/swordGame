import java.awt.*;

public class FastEnemy extends Enemy {
    //constructor
    public FastEnemy(int x, int y) {
        super(x, y, 3);
    }

    //draws enemy, overrides abstract class
    @Override
    public void drawEnemy(Graphics g) {
        //fast enemies are green
        g.setColor(Color.GREEN);
        g.fillRect(x, y, size, size);
    }
}