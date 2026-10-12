import java.awt.*;

//slower enemy variant
public class SlowEnemy extends Enemy {
    //constructor
    public SlowEnemy(int x, int y) {
        super(x, y, 1);
    }

    //draws enemy, overrides abstract class
    @Override
    public void drawEnemy(Graphics g) {
        //slow enemies are white
        g.setColor(new Color(255, 255, 255));
        g.fillRect(x, y, size, size);
    }
}