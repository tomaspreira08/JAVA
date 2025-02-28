import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SimplePongGame extends JPanel implements ActionListener {
    private int ballX, ballY, ballDiameter = 15;
    private int ballXSpeed = 3, ballYSpeed = 3;
    private int leftPaddleY = 200, rightPaddleY = 200, paddleHeight = 60, paddleWidth = 10;
    private int leftScore = 0, rightScore = 0; // Variáveis de pontuação

    public SimplePongGame() {
        setPreferredSize(new Dimension(600, 500));
        setBackground(Color.BLUE);
        Timer timer = new Timer(10, this);
        timer.start();
        resetBall(1); // Começa indo para a direita

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

        // Verifica colisões com as bordas esquerda e direita (pontuação)
        if (ballX < 0) { // Jogador da direita marca ponto
            rightScore++;
            resetBall(1); // Reinicia a bola indo para a direita
        } else if (ballX > getWidth() - ballDiameter) { // Jogador da esquerda marca ponto
            leftScore++;
            resetBall(-1); // Reinicia a bola indo para a esquerda
        }

        // Verifica colisões com as bordas superior e inferior
        if (ballY < 0 || ballY > getHeight() - ballDiameter) {
            ballYSpeed = -ballYSpeed; // Inverte a direção vertical
        }

        // Verifica colisão com as barras
        if (ballX <= paddleWidth && ballY + ballDiameter >= leftPaddleY && ballY <= leftPaddleY + paddleHeight) {
            ballXSpeed = Math.abs(ballXSpeed); // Garante que a bola vá para a direita
        }
        if (ballX >= getWidth() - paddleWidth - ballDiameter && ballY + ballDiameter >= rightPaddleY && ballY <= rightPaddleY + paddleHeight) {
            ballXSpeed = -Math.abs(ballXSpeed); // Garante que a bola vá para a esquerda
        }

        repaint(); // Atualiza a tela
    }

    // Método para reiniciar a bola no centro e definir a direção correta
    private void resetBall(int direction) {
        ballX = getWidth() / 2 - ballDiameter / 2; // Centraliza a bola no eixo X
        ballY = getHeight() / 2 - ballDiameter / 2; // Centraliza a bola no eixo Y

        ballXSpeed = 3 * direction; // Define a direção (1 = direita, -1 = esquerda)
        ballYSpeed = 3; // Mantém a velocidade vertical padrão
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
