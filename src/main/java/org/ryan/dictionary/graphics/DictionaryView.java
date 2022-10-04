package org.ryan.dictionary.graphics;

import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.ryan.dictionary.api.DictionaryProcessor;
import org.ryan.dictionary.api.WordData;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Log
public class DictionaryView extends JPanel {

    final List<Component> GUI = new ArrayList<>();
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

        ImageIcon heart = new ImageIcon("res/icon/heart.png");
        JButton favouriteButton = new JButton(new ImageIcon(SideView.getScaledImage(heart.getImage(), 15, 15)));
        favouriteButton.setBorderPainted(false);
        favouriteButton.addActionListener(e -> addToList(asset.getData().getWord()));
        GUI.add(favouriteButton);

        add(SEARCH_FIELD);
        add(goButton);
        add(favouriteButton);
    }

    void addToList(String word) {
        SideView.COLLECTION.add(word);
        System.out.println("added " + word + " to collection");
    }

    void search(String input) {
        SEARCH_FIELD.setEnabled(false);
        WordData data = DictionaryProcessor.fetchData(input);
        Application.yOffset = 0;
        Application.xOffset = 0;
        if (data != null) {
            WordAsset asset = new WordAsset(60, 100, data);
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
            GUI.forEach(Component::repaint);
            asset.paint(g);
        }
    }
}
