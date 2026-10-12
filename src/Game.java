import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Game extends JPanel implements Runnable, KeyListener {
    //runs the game loop
    private Thread thread;
    //stores player and sword objects
    private CubePlayer player;
    private Sword sword;

    //score goes up when an enemy is defeated
    private int score = 0;

    //stores each enemy
    private ArrayList<Enemy> enemies;
    //determines which side the enemies will spawn from
    private int enemySpawnArea = 0;

    //movement based on boolean. If the WASD keys are pressed, the player moves in the respective direction
    private boolean moveW = false;
    private boolean moveA = false;
    private boolean moveS = false;
    private boolean moveD = false;

    //checks whether the game is running or not
    private boolean gameOver = false;
    private boolean running = true;

    //constructor
    public Game() {
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        //instantiates player
        player = new CubePlayer(275, 275, 50); // x, y, size

        //instantiates enemy arraylist
        enemies = new ArrayList<>();

        thread = new Thread(this);
        thread.start();
    }

    //game loop method
    public void run() {
        while (running) {
            //only update game mechanics if the game isn't over
            if (!gameOver) {
                //Player moves based on WASD
                int dx = 0;
                int dy = 0;
                if (moveW)
                    dy -= 5;
                if (moveS)
                    dy += 5;
                if (moveA)
                    dx -= 5;
                if (moveD)
                    dx += 5;

                //move player based on direction variables
                player.move(dx, dy, getWidth(), getHeight());

                //update all enemies to chase the player
                for (int i = 0; i < enemies.size(); i++) {
                    enemies.get(i).chasePlayer(player, getWidth(), getHeight());
                }

                //check if the current sword has expired past its lifetime
                if (sword != null && sword.isExpired()) {
                    sword = null;
                }

                //creates an arrayList of enemies to remove (i.e. enemies hit with sword)
                ArrayList<Enemy> enemiesToRemove = new ArrayList<>();

                //checks collisions for each enemy
                for (int i = 0; i < enemies.size(); i++) {
                    Enemy e = enemies.get(i);

                    //checks if the sword is active and hitting an enemy
                    if (sword != null && sword.getBounds(player).intersects(e.getBounds())) {
                        //adds enemy to removal arrayList
                        enemiesToRemove.add(e);
                        score += 10;
                    }

                    //ends game if player intersects enemy
                    if (player.getBounds().intersects(e.getBounds()))
                        gameOver = true;
                }

                //remove enemies hit with sword outside loop
                enemies.removeAll(enemiesToRemove);

                //randomly spawns enemies, spawning becomes more common as more are killed
                if (((Math.random() * 100) + 1) < (2.5 + (double)score/100)) {
                    enemySpawnArea = (int)(Math.random() * 4 + 1);
                    //enemy spawn position
                    int spawnX = 0;
                    int spawnY = 0;

                    //randomly chooses whether to spawn an enemy at the top, bottom, left or right of the screen respectively
                    if (enemySpawnArea == 1) {
                        spawnX = (int)(Math.random() * getWidth());
                        spawnY = 0;
                    } else if (enemySpawnArea == 2) {
                        spawnX = (int)(Math.random() * getWidth());
                        spawnY = getHeight();
                    } else if (enemySpawnArea == 3) {
                        spawnX = 0;
                        spawnY = (int)(Math.random() * getHeight());
                    } else if (enemySpawnArea == 4) {
                        spawnX = getWidth();
                        spawnY = (int)(Math.random() * getHeight());
                    }

                    //determines whether the enemy is a regular slow enemy or a faster variant. 25% fast, 75% slow
                    int typeChance = (int)(Math.random() * 100) + 1;
                    if (typeChance <= 25)
                        enemies.add(new FastEnemy(spawnX, spawnY));
                    else
                        enemies.add(new SlowEnemy(spawnX, spawnY));
                }
            }

            //repaints the screen
            repaint();

            //controls game speed
            try {
                Thread.sleep(15);
            } catch (InterruptedException ex) {}
        }
    }

    //paints the screen
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //draws player using the player's draw function
        player.draw(g);

        //draws sword if it is active, checks player position
        if (sword != null) {
            sword.draw(g, player);
        }

        //draws each enemy (based on # of enemies)
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).drawEnemy(g);
        }

        //draws HUD
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        g.drawString("Score: " + score, 15, 25);

        //draws gameover
        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("GAME OVER", getWidth() / 2 - 120, getHeight() / 2 - 20);
            g.setColor(Color.BLUE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Your Score: " + score, getWidth() / 2 - 60, getHeight() / 2 + 20);
        }
    }

    //checks if a key is pressed
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        //if a key is pressed, direction movement is set to true. this is handled in the run method
        if (key == KeyEvent.VK_W)
            moveW = true;
        if (key == KeyEvent.VK_A)
            moveA = true;
        if (key == KeyEvent.VK_S)
            moveS = true;
        if (key == KeyEvent.VK_D)
            moveD = true;

        //creates a new sword if one of the respective keys is pressed
        if (!gameOver && (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT)) {
            sword = new Sword(key);
        }
    }

    //checks if a key is released
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        //stop movement when keys are released
        if (key == KeyEvent.VK_W) moveW = false;
        if (key == KeyEvent.VK_A) moveA = false;
        if (key == KeyEvent.VK_S) moveS = false;
        if (key == KeyEvent.VK_D) moveD = false;
    }

    //checks if key is pressed
    public void keyTyped(KeyEvent e) {
    }

    //main method
    public static void main(String[] args) {
        //creates game panel
        JFrame frame = new JFrame("Cube Swatter");
        Game gamePanel = new Game();
        frame.add(gamePanel);
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}