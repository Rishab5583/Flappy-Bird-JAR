package flappyBird;

import javax.swing.Timer;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class FlappyBird implements ActionListener, KeyListener, MouseListener {
    public static FlappyBird flappy = new FlappyBird();
    public final int WIDTH = 750, HEIGHT = 600;

    //frame number
    public int fNo = 0;

    public Renderer renderer = new Renderer();

    public Rectangle bird = new Rectangle(100, 200, 37, 25);
    public Rectangle wing = new Rectangle(bird.x-3, bird.y+6, bird.width / 2, bird.height/2);

    public ArrayList<Rectangle> pipes = new ArrayList<Rectangle>();

    //bird physics
    public int velocity;

    //game logistics
    public boolean started = false, gameOver = false;
    public int score = 0;

    //random
    public Random rand = new Random();

    public FlappyBird() {
        JFrame frame = new JFrame();
        Timer timer = new Timer((1000/30), this);

        frame.add(renderer);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setFocusable(true);
        frame.addKeyListener(this);
        frame.addMouseListener(this);
        frame.setTitle("Flappy Bird");
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setBackground(Color.cyan);

        timer.start();

        addPipe();
    }

    public static void main(String[] args) {

    }

    public void addPipe() {
        int width = 50, space = 75, height = 50 + rand.nextInt(200);

        pipes.add(new Rectangle(WIDTH - 3, 0, width, height));
        pipes.add(new Rectangle(WIDTH, height + 50 + space, width, HEIGHT - height - space - 100));
    }

    public void drawPipe(Graphics g, Rectangle pipe) {
        //draw pipe
        g.setColor(Color.green.darker());
        g.fillRect(pipe.x, pipe.y, pipe.width, pipe.height);
    }

    public void fly() {
        velocity = -10;

        if (!started || gameOver) {
            started = true;
            bird.x = 100;
            bird.y = 100;
            gameOver = false;
            pipes.clear();
            score = 0;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (started && !gameOver) {

            fNo++;

            if (fNo%30 == 0) {
                score ++;
            }

            for (Rectangle pipe : pipes) {
                if (bird.intersects(pipe)) {
                    gameOver = true;
                }
            }

            velocity++;

            bird.y += velocity;

            if (bird.y <= 0) {
                bird.y = 0;
                velocity = 0;
            }

            if (bird.y >= HEIGHT - 50 - 10) {
                bird.y = HEIGHT - bird.height - 50;
                gameOver = true;
            }

            for (int i = 0; i < pipes.size(); i++) {
                Rectangle pipe = pipes.get(i);
                pipe.x -= 5;

                if (pipe.x <= -50) {
                    pipes.remove(pipe);
                }
            }

            if (fNo % 75 == 0) {
                addPipe();
            }
        }//wing = new Rectangle(bird.x-3, bird.y+6, bird.width / 2, bird.height/2);
        wing.x = bird.x-3;
        wing.y = bird.y+6;
        wing.width = bird.width / 2;
        wing.height = bird.height/2;

        renderer.repaint();
    }

    public void repaint(Graphics g) {
        //background
        g.setColor(Color.cyan);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        //ground
        g.setColor(Color.orange);
        g.fillRect(0, HEIGHT - 50, WIDTH, 50);
        g.setColor(Color.green);
        g.fillRect(0, HEIGHT - 50, WIDTH, 10);

        //bird
        g.setColor(Color.yellow);
        g.fillOval(bird.x, bird.y, bird.width, bird.height);
        g.setColor(Color.black);
        g.drawOval(bird.x, bird.y, bird.width, bird.height);
        g.setColor(Color.white);
        g.fillOval(bird.x + 20, bird.y, 20, 20);
        g.setColor(Color.black);
        g.drawOval(bird.x + 20, bird.y, 20, 20);
        g.fillOval(bird.x + 25 + 3, bird.y + 3, 3, 6);
        g.setColor(Color.orange);
        g.fillOval(bird.x + 23, bird.y + 15, 30, 5);
        g.setColor(Color.black);
        g.drawOval(bird.x + 23, bird.y + 15, 30, 5);
        //wings
        g.setColor(Color.yellow.darker());
        g.fillOval(wing.x, wing.y, wing.width, wing.height);
        g.setColor(Color.black);
        g.drawOval(wing.x, wing.y, wing.width, wing.height);

        for (int i  = 0; i < pipes.size(); i++) {
            for (Rectangle pipe : pipes) {
                drawPipe(g, pipe);
            }
        }

        g.setColor(Color.white);
        g.setFont(new Font("Arial", 1, 50));
        g.drawString(String.valueOf(score), WIDTH - 100, 50);

        if (!started) {
            g.setColor(Color.white);
            g.setFont(new Font("Arial", 1, 75));
            g.drawString("Click to Start", (WIDTH/2) - 200, HEIGHT/2);
        }

        if (gameOver) {
            g.setColor(Color.red);
            g.setFont(new Font("Arial", 1, 75));
            g.drawString("Game Over!", (WIDTH/2) - 200, HEIGHT/2);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            fly();
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        fly();
    }
    @Override
    public void mousePressed(MouseEvent e) {

    }
    @Override
    public void mouseReleased(MouseEvent e) {

    }
    @Override
    public void mouseEntered(MouseEvent e) {

    }
    @Override
    public void mouseExited(MouseEvent e) {

    }
}

