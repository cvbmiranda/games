package snakegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private final int UNIT_SIZE = 25;
    private final int SCREEN_WIDTH = 600;
    private final int SCREEN_HEIGHT = 600;
    private final int DELAY = 100;

    private final ArrayList<Node> snake;
    private int appleX, appleY;
    private int score = 0; // Variável de pontuação
    private char direction = 'R';
    private boolean running = false;
    private Timer timer;
    private Random random;

    public GamePanel() {
        random = new Random();
        snake = new ArrayList<>();
        snake.add(new Node(UNIT_SIZE * 4, UNIT_SIZE * 4));
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(new Color(240, 240, 240));
        this.setFocusable(true);
        this.addKeyListener(this);
        startGame();
    }

    public void startGame() {
        newApple();
        running = true;
        score = 0; // Reseta a pontuação ao iniciar o jogo
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            // Desenha a maçã
            g.setColor(new Color(255, 80, 80));
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            // Desenha a cobra
            for (int i = 0; i < snake.size(); i++) {
                if (i == 0) {
                    g.setColor(new Color(0, 150, 0));
                } else {
                    g.setColor(new Color(0, 200, 0));
                }
                g.fillRoundRect(snake.get(i).x, snake.get(i).y, UNIT_SIZE, UNIT_SIZE, 10, 10);
            }

            // Exibe a pontuação
            g.setColor(Color.BLACK);
            g.setFont(new Font("Ink Free", Font.BOLD, 24));
            g.drawString("Score: " + score, 10, 30); // Exibe a pontuação no canto superior esquerdo
        } else {
            gameOver(g);
        }
    }

    public void newApple() {
        appleX = random.nextInt((SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    public void move() {
        for (int i = snake.size() - 1; i > 0; i--) {
            snake.get(i).x = snake.get(i - 1).x;
            snake.get(i).y = snake.get(i - 1).y;
        }

        switch (direction) {
            case 'U':
                snake.get(0).y -= UNIT_SIZE;
                break;
            case 'D':
                snake.get(0).y += UNIT_SIZE;
                break;
            case 'L':
                snake.get(0).x -= UNIT_SIZE;
                break;
            case 'R':
                snake.get(0).x += UNIT_SIZE;
                break;
        }
    }

    public void checkApple() {
        if ((snake.get(0).x == appleX) && (snake.get(0).y == appleY)) {
            snake.add(new Node(-1, -1)); // Adiciona um novo segmento à cobra
            newApple(); // Gera uma nova maçã
            score += 10; // Incrementa a pontuação
        }
    }

    public void checkCollisions() {
        for (int i = snake.size() - 1; i > 0; i--) {
            if ((snake.get(0).x == snake.get(i).x) && (snake.get(0).y == snake.get(i).y)) {
                running = false;
            }
        }

        if (snake.get(0).x < 0 || snake.get(0).x >= SCREEN_WIDTH || snake.get(0).y < 0 || snake.get(0).y >= SCREEN_HEIGHT) {
            running = false;
        }

        if (!running) {
            timer.stop();
        }
    }

    public void gameOver(Graphics g) {
        // Exibe "Game Over"
        g.setColor(new Color(255, 80, 80));
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);

        // Exibe a pontuação final
        g.setColor(Color.BLACK);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        g.drawString("Score: " + score, (SCREEN_WIDTH - metrics.stringWidth("Score: " + score)) / 2, SCREEN_HEIGHT / 2 + 60);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (direction != 'R') direction = 'L';
                break;
            case KeyEvent.VK_RIGHT:
                if (direction != 'L') direction = 'R';
                break;
            case KeyEvent.VK_UP:
                if (direction != 'D') direction = 'U';
                break;
            case KeyEvent.VK_DOWN:
                if (direction != 'U') direction = 'D';
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}
