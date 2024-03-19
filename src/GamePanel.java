import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    public static final int WIDTH = 700, HEIGHT = 700;
    public static final int OBJ_SIZE = 35;
    public static final int delay = 90;
    public Snake snake = new Snake(new Block(0, OBJ_SIZE, OBJ_SIZE, OBJ_SIZE));
    boolean running = false;
    public DIRECTIONS directions = DIRECTIONS.RIGHT;

    public Timer timer;
    public Random rand;
    public int score = 0;

    public Food food;

    public GamePanel() {
        rand = new Random();
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        timer = new Timer(delay, this);
        timer.start();
        createApple();
        start();
        running = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) { // timer variable make our game
        if(running) {
            move();
            checkApple();
            checkCollision();
            snake.eaten = false;
            repaint();
        }
    }

    public void start() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);

    }

    public void createApple() {
        int maxRows = (HEIGHT - OBJ_SIZE) / OBJ_SIZE;

        // Generate a random x-coordinate within the width range
        int randomX = rand.nextInt(WIDTH / OBJ_SIZE) * OBJ_SIZE;

        // Generate a random y-coordinate within the height range, starting from OBJ_SIZE
        int randomY = rand.nextInt(maxRows) * OBJ_SIZE + OBJ_SIZE;

        // Create the food object
        food = new Food(randomX, randomY, OBJ_SIZE, OBJ_SIZE);
    }

    public void draw(Graphics g) {
        g.setColor(Color.ORANGE);
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 27));
        g.drawString("Score: " + score, WIDTH/2-50, 25);
        for (Block block : snake.body) {
            g.setColor(Color.GREEN);
            g.fillRect(block.x, block.y, block.WIDTH, block.HEIGHT);
        }
        for (int i = 0; i < WIDTH / OBJ_SIZE; i++) {
            g.setColor(Color.BLUE);
            g.drawLine(i * OBJ_SIZE, OBJ_SIZE, i * OBJ_SIZE, HEIGHT);
            g.drawLine(0, i * OBJ_SIZE, WIDTH, i * OBJ_SIZE);
        }
        g.setColor(Color.MAGENTA );
        g.fillOval(food.x, food.y, OBJ_SIZE, OBJ_SIZE);
        if(!running){
            g.setColor(Color.RED);
            g.setFont(new Font("Comic Sans MS", Font.BOLD, 42));
            g.drawString("Game over! Score: " + score, 160, HEIGHT - HEIGHT/2 - 5);
        }
    }

    public void move() {
        switch (directions) {
            case UP:
                for (int i = snake.body.size() - 1; i > 0; i--) {
                    snake.body.get(i).x = snake.body.get(i - 1).x;
                    snake.body.get(i).y = snake.body.get(i - 1).y;
                }
                snake.body.get(0).y -= OBJ_SIZE;
                break;
            case DOWN:
                for (int i = snake.body.size() - 1; i > 0; i--) {
                    snake.body.get(i).x = snake.body.get(i - 1).x;
                    snake.body.get(i).y = snake.body.get(i - 1).y;
                }
                snake.body.get(0).y += OBJ_SIZE;
                break;
            case LEFT:
                for (int i = snake.body.size() - 1; i > 0; i--) {
                    snake.body.get(i).x = snake.body.get(i - 1).x;
                    snake.body.get(i).y = snake.body.get(i - 1).y;
                }
                snake.body.get(0).x -= OBJ_SIZE;
                break;
            case RIGHT:
                for (int i = snake.body.size() - 1; i > 0; i--) {
                    snake.body.get(i).x = snake.body.get(i - 1).x;
                    snake.body.get(i).y = snake.body.get(i - 1).y;
                }
                snake.body.get(0).x += OBJ_SIZE;
                break;
        }
    }


    public void checkApple() {
        Block head = snake.body.getFirst();
        if(head.x == food.x && head.y == food.y){
            score++;
            snake.grow();
            createApple();
        }
    }

    public void checkCollision() {
        Block head = snake.body.getFirst();

        // Check if the head collides with any part of the body (excluding the head itself)
        for (int i = 1; i < snake.body.size(); i++) {
            Block bodyPart = snake.body.get(i);
            if (head.x == bodyPart.x && head.y == bodyPart.y && !snake.eaten) {
                gameOver();
                return;
            }
        }

        // Check if the head goes out of bounds
        if (head.x < 0 || head.x >= WIDTH || head.y < OBJ_SIZE || head.y >= HEIGHT) {
            gameOver();
        }
    }


    public void gameOver() {
        this.running = false;
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT: if(directions != DIRECTIONS.RIGHT) directions = DIRECTIONS.LEFT; break;
                case KeyEvent.VK_RIGHT: if(directions != DIRECTIONS.LEFT) directions = DIRECTIONS.RIGHT; break;
                case KeyEvent.VK_UP: if(directions != DIRECTIONS.DOWN) directions = DIRECTIONS.UP; break;
                case KeyEvent.VK_DOWN: if(directions != DIRECTIONS.UP) directions = DIRECTIONS.DOWN; break;
            }
        }
    }
}
