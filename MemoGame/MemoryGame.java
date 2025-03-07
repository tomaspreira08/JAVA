package MemoGame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class MemoryGame extends JFrame {
    private JButton[] buttons;
    private String[] cardValues;
    private boolean[] cardFlipped;
    private int firstCardIndex = -1;
    private int secondCardIndex = -1;
    private int pairsFound = 0;

    public MemoryGame() {
        setTitle("Jogo da Memória");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 4));

        // Inicializa os valores das cartas
        cardValues = new String[] {
            "A", "A", "B", "B", "C", "C", "D", "D",
            "E", "E", "F", "F", "G", "G", "H", "H"
        };
        cardFlipped = new boolean[16];

        // Embaralha as cartas
        ArrayList<String> cardList = new ArrayList<>();
        Collections.addAll(cardList, cardValues);
        Collections.shuffle(cardList); // Embaralha a lista de cartas
        cardValues = cardList.toArray(new String[0]); // Converte de volta para array

        // Cria os botões para as cartas
        buttons = new JButton[16];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton("?");
            final int index = i;
            buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    flipCard(index);
                }
            });
            add(buttons[i]);
        }

        setVisible(true);
    }

    private void flipCard(int index) {
        if (cardFlipped[index] || secondCardIndex != -1) {
            return; // Ignora se a carta já estiver virada ou se já houver duas cartas viradas
        }

        buttons[index].setText(cardValues[index]);
        cardFlipped[index] = true;

        if (firstCardIndex == -1) {
            firstCardIndex = index; // Primeiro cartão virado
        } else {
            secondCardIndex = index; // Segundo cartão virado
            checkForMatch();
        }
    }

    private void checkForMatch() {
        if (cardValues[firstCardIndex].equals(cardValues[secondCardIndex])) {
            pairsFound++;
            resetCardIndices();
            if (pairsFound == cardValues.length / 2) {
                JOptionPane.showMessageDialog(this, "Você ganhou!");
            }
        } else {
            Timer timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    buttons[firstCardIndex].setText("?");
                    buttons[secondCardIndex].setText("?");
                    cardFlipped[firstCardIndex] = false;
                    cardFlipped[secondCardIndex] = false;
                    resetCardIndices();
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    private void resetCardIndices() {
        firstCardIndex = -1;
        secondCardIndex = -1;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MemoryGame());
    }
}