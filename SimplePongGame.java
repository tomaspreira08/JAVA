import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SimplePongGame extends JPanel implements ActionListener {
    private int ballX = 300, ballY = 250, ballDiameter = 15;
    private int ballXSpeed = 3, ballYSpeed = 3;
    private int leftPaddleY = 200, rightPaddleY = 200, paddleHeight = 60, paddleWidth = 10;
    private int leftScore = 0, rightScore = 0; // Variáveis de pontuação

    public SimplePongGame() {
        setPreferredSize(new Dimension(600, 500));
        setBackground(Color.BLUE);
        Timer timer = new Timer(10, this);
        timer.start();

        // Adiciona o KeyListener para controlar as barras
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Movimentação da barra esquerda
                if (e.getKeyCode() == KeyEvent.VK_W && leftPaddleY > 0) {
                    leftPaddleY -= 10; // Move para cima
                }
                if (e.getKeyCode() == KeyEvent.VK_S && leftPaddleY < getHeight() - paddleHeight) {
                    leftPaddleY += 10; // Move para baixo
                }
                // Movimentação da barra direita
                if (e.getKeyCode() == KeyEvent.VK_UP && rightPaddleY > 0) {
                    rightPaddleY -= 10; // Move para cima
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN && rightPaddleY < getHeight() - paddleHeight) {
                    rightPaddleY += 10; // Move para baixo
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g; // Converte Graphics para Graphics2D
        g2d.setColor(Color.WHITE);
        g2d.fillOval(ballX, ballY, ballDiameter, ballDiameter); // Desenha a bola
        g2d.fillRect(0, leftPaddleY, paddleWidth, paddleHeight); // Desenha a barra esquerda
        g2d.fillRect(getWidth() - paddleWidth, rightPaddleY, paddleWidth, paddleHeight); // Desenha a barra direita

        // Desenha a linha da rede no meio
        g2d.setStroke(new BasicStroke(5)); // 5 pixels de espessura
        g2d.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight()); // Linha vertical no meio

        // Desenha a pontuação
        g2d.setFont(new Font("Arial", Font.BOLD, 30));
        g2d.drawString("Left: " + leftScore, 50, 50);
        g2d.drawString("Right: " + rightScore, getWidth() - 150, 50);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Atualiza a posição da bola
        ballX += ballXSpeed;
        ballY += ballYSpeed;

        // Verifica colisões com as bordas
        if (ballX < 0) {
            rightScore++; // Aumenta a pontuação do jogador da direita
            ballXSpeed = -ballXSpeed; // Inverte a direção da bola
        }
        if (ballX > getWidth() - ballDiameter) {
            leftScore++; // Aumenta a pontuação do jogador da esquerda
            ballXSpeed = -ballXSpeed; // Inverte a direção da bola
        }
        if (ballY < 0 || ballY > getHeight() - ballDiameter) {
            ballYSpeed = -ballYSpeed; // Inverte a direção se bater na parte superior ou inferior
        }

        // Verifica colisão com as barras
        if (ballX <= paddleWidth && ballY + ballDiameter >= leftPaddleY && ballY <= leftPaddleY + paddleHeight) {
            ballXSpeed = -ballXSpeed; // Inverte a direção
        }
        if (ballX >= getWidth() - paddleWidth - ballDiameter && ballY + ballDiameter >= rightPaddleY && ballY <= rightPaddleY + paddleHeight) {
            ballXSpeed = -ballXSpeed; // Inverte a direção
        }

        repaint(); // Atualiza a tela
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
