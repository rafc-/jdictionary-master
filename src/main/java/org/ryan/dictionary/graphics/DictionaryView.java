package org.ryan.dictionary.graphics;

import lombok.extern.java.Log;
import org.ryan.dictionary.api.DictionaryProcessor;
import org.ryan.dictionary.api.WordData;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Log
public class DictionaryView extends JPanel {

    final List<Component> GUI = new ArrayList<>();
    final TextField FIELD;
    final Button LUCKY;
    int random;

    static WordAsset asset;

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
        random = ThreadLocalRandom.current().nextInt(0, SideView.COLLECTION.size());
        LUCKY.setName(SideView.COLLECTION.get(random));
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
            display(asset);
            FIELD.setEnabled(true);
        } else {
            log.severe("Error: problem during search.");
        }
    }

    void display(WordAsset word) {
        asset = word;
        Application.app.repaint();

        random = ThreadLocalRandom.current().nextInt(0, SideView.COLLECTION.size());
        LUCKY.setName(SideView.COLLECTION.get(random));
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        Image img;
        try {
            img = ImageIO.read(new File("bg.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g.drawImage(img, 0, 0, null);
        //g.fillRect(0, 0, Application.app.getWidth(), Application.app.getHeight());

        if (asset != null) {
            GUI.forEach(Component::repaint);
            asset.paint(g);
        }
    }
}
