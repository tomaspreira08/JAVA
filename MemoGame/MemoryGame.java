package MemoGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class MemoryGame extends JFrame {
    private JLabel[] labels;
    private String[] cardValues;
    private boolean[] cardFlipped;
    private int firstCardIndex = -1;
    private int secondCardIndex = -1;
    private int pairsFound = 0;
    private GameTheme theme; // Agora usa a classe GameTheme

    public MemoryGame(GameTheme theme) {
        this.theme = theme;
        setTitle("Jogo da Memória - " + theme.themeName);
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 4));

        // Inicializa os valores das cartas
        cardValues = new String[]{"1", "1", "2", "2", "3", "3", "4", "4", "5", "5", "6", "6", "7", "7", "8", "8"};
        cardFlipped = new boolean[16];

        // Embaralha as cartas
        ArrayList<String> cardList = new ArrayList<>();
        Collections.addAll(cardList, cardValues);
        Collections.shuffle(cardList);
        cardValues = cardList.toArray(new String[0]);

        // Cria os rótulos para as cartas
        labels = new JLabel[16];
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new JLabel(theme.loadImage("cardBack"));
            final int index = i;
            labels[i].addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    flipCard(index);
                }
            });
            add(labels[i]);
        }

        setVisible(true);
    }

    private void flipCard(int index) {
        if (cardFlipped[index] || secondCardIndex != -1) {
            return;
        }

        // Exibe a imagem correspondente à carta
        labels[index].setIcon(theme.loadImage("card" + cardValues[index]));
        cardFlipped[index] = true;

        if (firstCardIndex == -1) {
            firstCardIndex = index;
        } else {
            secondCardIndex = index;
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
                    labels[firstCardIndex].setIcon(theme.loadImage("cardBack"));
                    labels[secondCardIndex].setIcon(theme.loadImage("cardBack"));
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
        // Tela de seleção de tema
        String[] themes = {"Futebol", "Animais", "Frutas"};
        String selectedTheme = (String) JOptionPane.showInputDialog(null, "Escolha um tema:",
                "Seleção de Tema", JOptionPane.QUESTION_MESSAGE, null, themes, themes[0]);

        if (selectedTheme != null) {
            GameTheme theme;
            switch (selectedTheme.toLowerCase()) {
                case "futebol":
                    theme = new FutebolTheme();
                    break;
                case "animais":
                    theme = new AnimaisTheme();
                    break;
                case "frutas":
                    theme = new FrutasTheme();
                    break;
                default:
                    theme = new FutebolTheme(); // Padrão
            }
            SwingUtilities.invokeLater(() -> new MemoryGame(theme));
        }
    }
}
