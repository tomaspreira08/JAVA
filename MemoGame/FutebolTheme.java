package MemoGame;

import javax.swing.*;

public class FutebolTheme extends GameTheme {
    public FutebolTheme() {
        super("futebol");
    }

    @Override
    public ImageIcon loadImage(String fileName) {
        return findImage(fileName);
    }
}
