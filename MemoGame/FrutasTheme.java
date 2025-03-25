package MemoGame;

import javax.swing.*;

public class FrutasTheme extends GameTheme {
    public FrutasTheme() {
        super("frutas");
    }

    @Override
    public ImageIcon loadImage(String fileName) {
        return findImage(fileName);
    }
}
