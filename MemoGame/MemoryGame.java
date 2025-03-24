package MemoGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class MemoryGame extends JFrame {
    private JLabel[] labels;
    private String[] cardValues;
    private boolean[] cardFlipped;
    private int firstCardIndex = -1;
    private int secondCardIndex = -1;
    private int pairsFound = 0;
    private String theme;

    // Caminho base das imagens
    private final String IMAGE_PATH = "images/";

    public MemoryGame(String theme) {
        this.theme = theme; // Define o tema escolhido
        setTitle("Jogo da Memória - " + theme);
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 4));

        // Inicializa os valores das cartas com imagens
        cardValues = new String[] {
            "1", "1", "2", "2", "3", "3", "4", "4",
            "5", "5", "6", "6", "7", "7", "8", "8"
        };
        cardFlipped = new boolean[16];

        // Embaralha as cartas
        ArrayList<String> cardList = new ArrayList<>();
        Collections.addAll(cardList, cardValues);
        Collections.shuffle(cardList);
        cardValues = cardList.toArray(new String[0]);

        // Cria os rótulos para as cartas
        labels = new JLabel[16];
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new JLabel(loadImage("cardBack")); // Imagem de fundo
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
            return; // Ignora se a carta já estiver virada ou se já houver duas cartas viradas
        }

        // Exibe a imagem correspondente à carta
        labels[index].setIcon(loadImage("card" + cardValues[index]));
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
                JOptionPane.showMessageDialog(this, "Parabéns! Você ganhou!");
            }
        } else {
            Timer timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    labels[firstCardIndex].setIcon(loadImage("cardBack"));
                    labels[secondCardIndex].setIcon(loadImage("cardBack"));
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

    // Método para carregar as imagens diretamente da pasta do tema
    private ImageIcon loadImage(String fileName) {
        String themePath = IMAGE_PATH + theme + "/";
        File pngFile = new File(themePath + fileName + ".png");
        File jpgFile = new File(themePath + fileName + ".jpg");

        if (pngFile.exists()) {
            return new ImageIcon(pngFile.getAbsolutePath());
        } else if (jpgFile.exists()) {
            return new ImageIcon(jpgFile.getAbsolutePath());
        } else {
            System.out.println("❌ Imagem não encontrada: " + fileName);
            return new ImageIcon(); // Retorna um ícone vazio caso não encontre a imagem
        }
    }

    public static void main(String[] args) {
        // Tela de seleção de tema
        String[] themes = {"futebol", "animais", "frutas"};
        String selectedTheme = (String) JOptionPane.showInputDialog(null, "Escolha um tema:",
                "Seleção de Tema", JOptionPane.QUESTION_MESSAGE, null, themes, themes[0]);

        if (selectedTheme != null) {
            SwingUtilities.invokeLater(() -> new MemoryGame(selectedTheme));
        }
    }
}
