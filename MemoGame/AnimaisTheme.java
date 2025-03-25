package MemoGame;

import javax.swing.*;

public class AnimaisTheme extends GameTheme {
    public AnimaisTheme() {
        super("animais");
    }

    @Override
    public ImageIcon loadImage(String fileName) {
        return findImage(fileName);
    }
}
