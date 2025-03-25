package MemoGame;

import javax.swing.*;
import java.io.File;

public abstract class GameTheme {
    protected String themeName; // Nome do tema
    protected String imagePath; // Caminho base das imagens

    public GameTheme(String themeName) {
        this.themeName = themeName;
        this.imagePath = "MemoGame/images/" + themeName + "/";
    }

    public ImageIcon loadImage(String fileName) {
        System.out.println("🔍 Carregando imagem: " + fileName);
        return findImage(fileName);
    }
    

    protected ImageIcon findImage(String fileName) {
        String[] extensions = {".png", ".jpg", ".jpeg"};
        File foundFile = null;
    
        for (String ext : extensions) {
            File file = new File(imagePath + fileName + ext);
            System.out.println("🔍 Testando caminho: " + file.getAbsolutePath()); // Adicionado para debug
    
            if (file.exists()) {
                foundFile = file;
                System.out.println("✅ Imagem encontrada: " + foundFile.getAbsolutePath());
                break; // Para assim que encontrar uma imagem válida
            }
        }
    
        if (foundFile == null) {
            System.out.println("❌ ERRO: Imagem NÃO encontrada para " + fileName);
        }
    
        return foundFile != null ? new ImageIcon(foundFile.getAbsolutePath()) : new ImageIcon();
    }
    
    
    
}
