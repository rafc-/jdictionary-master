package org.ryan.dictionary.graphics;

import lombok.extern.java.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Log
public class SideView extends JPanel {

    final Font font = new Font("Times New Roman", Font.PLAIN, 15);

    final int LINE_SPACING = 10;

    static final List<String> COLLECTION = new ArrayList<>();

    public SideView() {
        setFont(font);
        setLayout(null);
        displayLabels();
    }

    void displayLabels() {
        int y = 50;
        int realY = y - Application.yOffset;
        int lines = 0;

        Label[] labels = createLabels();
        for (Label label : labels) {
            this.add(label);

            label.setBackground(Color.BLACK);
            label.setForeground(Color.GREEN);
            label.setBounds(50, realY + (18 + LINE_SPACING) * lines++, 150, 20);
        }
    }

    Label[] createLabels() {
        Label[] labels = new Label[COLLECTION.size()];
        for(int i = 0; i < COLLECTION.size(); i++) {
            String word = COLLECTION.get(i);
            labels[i] = new Label(COLLECTION.get(i));
            labels[i].addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    new DictionaryView().search(word);
                }
                @Override
                public void mousePressed(MouseEvent e) {
                }
                @Override
                public void mouseReleased(MouseEvent e) {
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                }
                @Override
                public void mouseExited(MouseEvent e) {
                }
            }); {
            }
        }

        return labels;
    }

    public static void read() throws FileNotFoundException {
        File file = new File("words.txt");
        Scanner scanner = new Scanner(file);

        while (scanner.hasNext()) {
            String words = scanner.next();
            COLLECTION.add(words);
        }

        COLLECTION.sort(String::compareTo);
        scanner.close();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Application.app.getWidth(), Application.app.getHeight());
    }
}