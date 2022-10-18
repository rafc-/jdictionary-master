package org.ryan.dictionary.graphics;

import lombok.extern.java.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Log
public class ListView extends JPanel {

    static final List<String> COLLECTION = new ArrayList<>();

    public ListView() {
        setOpaque(false);
        setLayout(new GridLayout(COLLECTION.size(), 1));
        displayLabels();
    }

    void displayLabels() {
        JLabel[] labels = createLabels();
        for (JLabel label : labels) {
            this.add(label);
            label.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            label.setForeground(Color.BLACK);
        }
    }

    JLabel[] createLabels() {
        JLabel[] labels = new JLabel[COLLECTION.size()];
        for(int i = 0; i < COLLECTION.size(); i++) {
            String word = COLLECTION.get(i);
            labels[i] = new JLabel(COLLECTION.get(i));
            labels[i].addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    new DictionaryView().search(word);
                }
                @Override
                public void mousePressed(MouseEvent e) {}
                @Override
                public void mouseReleased(MouseEvent e) {}
                @Override
                public void mouseEntered(MouseEvent e) {}
                @Override
                public void mouseExited(MouseEvent e) {}
            }); {
            }
        }

        return labels;
    }

    static void read() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("data/words.txt");
        Scanner scanner = new Scanner(Objects.requireNonNull(is));

        while (scanner.hasNext()) {
            String words = scanner.next();
            COLLECTION.add(words);
        }

        COLLECTION.sort(String::compareTo);
        scanner.close();
    }
}