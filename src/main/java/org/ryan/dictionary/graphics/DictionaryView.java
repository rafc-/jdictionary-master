package org.ryan.dictionary.graphics;

import lombok.extern.java.Log;
import org.ryan.dictionary.api.DictionaryProcessor;
import org.ryan.dictionary.api.WordData;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Log
public class DictionaryView extends JPanel {

    static final List<Component> GUI = new ArrayList<>();
    final TextField SEARCH_FIELD;
    static WordAsset asset;

    public DictionaryView() {
        SEARCH_FIELD = new TextField("");
        SEARCH_FIELD.setPreferredSize(new Dimension(200, 25));
        SEARCH_FIELD.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        SEARCH_FIELD.addActionListener(e -> search(SEARCH_FIELD.getText()));
        GUI.add(SEARCH_FIELD);

        Button goButton = new Button("Go");
        goButton.addActionListener(e -> search(SEARCH_FIELD.getText()));
        GUI.add(goButton);

        add(SEARCH_FIELD);
        add(goButton);
    }

    void search(String input) {
        SEARCH_FIELD.setEnabled(false);
        WordData data = DictionaryProcessor.fetchData(input);
        Application.yOffset = 0;
        Application.xOffset = 0;
        if (data != null) {
            WordAsset asset = new WordAsset(90, 150, data);
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
}
