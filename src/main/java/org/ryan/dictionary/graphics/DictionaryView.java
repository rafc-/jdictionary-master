package org.ryan.dictionary.graphics;

import lombok.extern.java.Log;
import org.ryan.dictionary.api.DictionaryProcessor;
import org.ryan.dictionary.api.WordData;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.ryan.dictionary.graphics.SideView.COLLECTION;

@Log
public class DictionaryView extends JPanel {

    final List<Component> GUI = new ArrayList<>();
    final TextField FIELD;
    final Button LUCKY;

    WordAsset asset;

    public DictionaryView() {
        FIELD = new TextField("intrinsic");
        FIELD.setPreferredSize(new Dimension(200, 25));
        FIELD.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        FIELD.addActionListener(e -> search(FIELD.getText()));
        GUI.add(FIELD);

        Button button = new Button("Go");
        button.addActionListener(e -> search(FIELD.getText()));
        GUI.add(button);

        LUCKY = new Button("I'm feeling lucky");
        LUCKY.addActionListener(e -> search(LUCKY.getName()));
        GUI.add(LUCKY);

        add(FIELD);
        add(button);
        add(LUCKY);
    }

    void search(String input) {
        FIELD.setEnabled(false);
        WordData data = DictionaryProcessor.fetchData(input);
        Application.yOffset = 0;
        Application.xOffset = 0;
        if (data != null) {
            WordAsset asset = new WordAsset(60, 100, data);
            this.display(asset);
            FIELD.setEnabled(true);
        } else {
            log.severe("Error: problem during search.");
        }
    }

    void display(WordAsset word) {
        this.asset = word;
        this.repaint();
        Application.app.repaint();

        System.out.println("display called -> " + asset.data.getWord());

        LUCKY.setEnabled(false);
        int random = ThreadLocalRandom.current().nextInt(0, COLLECTION.size());
        LUCKY.setName(COLLECTION.get(random));
        LUCKY.setEnabled(true);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Application.app.getWidth(), Application.app.getHeight());

        if (asset != null) {
            GUI.forEach(Component::repaint);
            this.asset.paint(g);
            System.out.println("painted -> " + asset.data.getWord());
        }
    }
}
