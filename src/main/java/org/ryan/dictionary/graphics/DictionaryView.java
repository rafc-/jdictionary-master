package org.ryan.dictionary.graphics;

import lombok.extern.java.Log;
import org.ryan.dictionary.api.DictionaryProcessor;
import org.ryan.dictionary.api.WordData;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.ryan.dictionary.graphics.SideView.collection;

@Log
public class DictionaryView extends JPanel {

    List<Component> gui = new ArrayList<>();
    TextField field;
    Button lucky;

    WordAsset asset;

    public DictionaryView() {
        field = new TextField("intrinsic");
        field.setPreferredSize(new Dimension(200, 25));
        field.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        field.addActionListener(e -> search(field.getText()));
        gui.add(field);

        Button button = new Button("Go");
        button.addActionListener(e -> search(field.getText()));
        gui.add(button);

        lucky = new Button("I'm feeling lucky");
        lucky.addActionListener(e -> search(lucky.getName()));
        gui.add(lucky);

        add(field);
        add(button);
        add(lucky);
    }

    void display(WordAsset word) {
        this.asset = word;
        this.repaint();
        Application.app.repaint();

        System.out.println("display called -> " + asset.data.getWord());

        lucky.setEnabled(false);
        int random = ThreadLocalRandom.current().nextInt(0, collection.size());
        lucky.setName(collection.get(random));
        lucky.setEnabled(true);
    }

    void search(String input) {
        field.setEnabled(false);
        WordData data = DictionaryProcessor.fetchData(input);
        Application.yOffset = 0;
        Application.xOffset = 0;
        if (data != null) {
            WordAsset asset = new WordAsset(60, 100, data);
            display(asset);
            field.setEnabled(true);
        } else {
            log.severe("Error: problem during search.");
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Application.app.getWidth(), Application.app.getHeight());

        if (asset != null) {
            gui.forEach(Component::repaint);
            asset.paint(g);
            System.out.println("painted -> " + asset.data.getWord());
        }
    }
}
