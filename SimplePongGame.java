import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SimplePongGame extends JPanel implements ActionListener {
    private int ballX = 300, ballY = 250, ballDiameter = 15;
    private int ballXSpeed = 2, ballYSpeed = 2;
    private int leftPaddleY = 200, rightPaddleY = 200, paddleHeight = 60, paddleWidth = 10;
    private int leftScore = 0, rightScore = 0;
    private int touchCount = 0;

    private boolean wPressed = false;
    private boolean sPressed = false;
    private boolean upPressed = false;
    private boolean downPressed = false;

    public SimplePongGame() {
        setPreferredSize(new Dimension(600, 500));
        setBackground(Color.BLUE);
        Timer timer = new Timer(10, this);
        timer.start();

        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_W) wPressed = true;
                if (e.getKeyCode() == KeyEvent.VK_S) sPressed = true;
                if (e.getKeyCode() == KeyEvent.VK_UP) upPressed = true;
                if (e.getKeyCode() == KeyEvent.VK_DOWN) downPressed = true;
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_W) wPressed = false;
                if (e.getKeyCode() == KeyEvent.VK_S) sPressed = false;
                if (e.getKeyCode() == KeyEvent.VK_UP) upPressed = false;
                if (e.getKeyCode() == KeyEvent.VK_DOWN) downPressed = false;
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.fillOval(ballX, ballY, ballDiameter, ballDiameter);
        g2d.fillRect(0, leftPaddleY, paddleWidth, paddleHeight);
        g2d.fillRect(getWidth() - paddleWidth, rightPaddleY, paddleWidth, paddleHeight);

        // Desenha a linha da rede no meio
        g2d.setStroke(new BasicStroke(5));
        g2d.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());

        g2d.setFont(new Font("Arial", Font.BOLD, 30));
        g2d.drawString("Left: " + leftScore, 50, 50);
        g2d.drawString("Right: " + rightScore, getWidth() - 150, 50);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ballX += ballXSpeed;
        ballY += ballYSpeed;

        if (ballX < 0) {
            rightScore++;
            resetBall(1);
        }
        if (ballX > getWidth() - ballDiameter) {
            leftScore++;
            resetBall(-1);
        }

        if (ballY < 0 || ballY > getHeight() - ballDiameter) {
            ballYSpeed = -ballYSpeed;
        }

        if (ballX <= paddleWidth && ballY + ballDiameter >= leftPaddleY && ballY <= leftPaddleY + paddleHeight) {
            ballXSpeed = -ballXSpeed;
            touchCount++;
            checkIncreaseSpeed();
            ballX += 5;
        }
        if (ballX >= getWidth() - paddleWidth - ballDiameter && ballY + ballDiameter >= rightPaddleY && ballY <= rightPaddleY + paddleHeight) {
            ballXSpeed = -ballXSpeed;
            touchCount++;
            checkIncreaseSpeed();
            ballX -= 5;
        }

        if (wPressed && leftPaddleY > 0) leftPaddleY -= 5;
        if (sPressed && leftPaddleY < getHeight() - paddleHeight) leftPaddleY += 5;
        if (upPressed && rightPaddleY > 0) rightPaddleY -= 5;
        if (downPressed && rightPaddleY < getHeight() - paddleHeight) rightPaddleY += 5;

        repaint();
    }

    private void resetBall(int direction) {
        ballX = 300;
        ballY = 250;
        ballXSpeed = direction * 2;
        ballYSpeed = 2;
        touchCount = 0;
    }

    private void checkIncreaseSpeed() {
        if (touchCount % 5 == 0) {
            ballXSpeed += (ballXSpeed > 0) ? 1 : -1;
            ballYSpeed += (ballYSpeed > 0) ? 1 : -1;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Simple Pong Game");
        SimplePongGame pongGame = new SimplePongGame();
        frame.add(pongGame);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
