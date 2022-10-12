package org.ryan.dictionary.graphics;

import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.ryan.dictionary.api.DictionaryProcessor;
import org.ryan.dictionary.api.WordData;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

@Log
public class DictionaryView extends JPanel {

    final TextField SEARCH_FIELD;
    static WordAsset asset;

    public DictionaryView() {
        SEARCH_FIELD = new TextField("");
        SEARCH_FIELD.setPreferredSize(new Dimension(200, 25));
        SEARCH_FIELD.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        SEARCH_FIELD.addActionListener(e -> search(SEARCH_FIELD.getText()));
        Application.GUI.add(SEARCH_FIELD);

        final Button GO_BUTTON = new Button("Go");
        GO_BUTTON.addActionListener(e -> search(SEARCH_FIELD.getText()));
        Application.GUI.add(GO_BUTTON);

        add(SEARCH_FIELD);
        add(GO_BUTTON);
    }

    void search(String input) {
        SEARCH_FIELD.setEnabled(false);
        WordData data = DictionaryProcessor.fetchData(input);
        Application.yOffset = 0;
        Application.xOffset = 0;
        if (data != null) {
            WordAsset asset = new WordAsset(data);
            display(asset);
            SEARCH_FIELD.setEnabled(true);
            SEARCH_FIELD.setText(null);
        } else {
            log.severe("Error: problem during search.");
        }
    }

    void display(WordAsset word) {
        asset = word;
        Application.app.repaint();
    }

    @SneakyThrows
    @Override
    public void paint(Graphics g) {
        g.drawImage(ImageIO.read(new File("res/bg.png")), 0, 0, null);

        if (asset != null) {
            Application.GUI.forEach(Component::repaint);
            asset.paint(g);
        }
    }
}
