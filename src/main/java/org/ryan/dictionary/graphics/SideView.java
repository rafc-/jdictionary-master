package org.ryan.dictionary.graphics;

import lombok.extern.java.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

@Log
public class SideView extends JPanel {

    static final List<String> COLLECTION = new ArrayList<>();

    static class SideButtonView extends JPanel {

        public SideButtonView() {
            final JButton HEART_BUTTON = new JButton(new ImageIcon(getScaledImage(
                    new ImageIcon("res/icon/heart.png").getImage(), 24, 24)));
            displayButton(HEART_BUTTON, "Add word to list", e -> addToList(DictionaryView.asset.getData().getWord()));
            Application.GUI.add(HEART_BUTTON);

            final JButton RANDOM_BUTTON = new JButton(new ImageIcon(getScaledImage(
                    new ImageIcon("res/icon/random.png").getImage(), 24, 24)));
            displayButton(RANDOM_BUTTON, "Search random word", e -> new DictionaryView().search(COLLECTION.get(
                    ThreadLocalRandom.current().nextInt(0, SideView.COLLECTION.size()))));
            Application.GUI.add(RANDOM_BUTTON);

            final JButton SORT_BUTTON = new JButton(new ImageIcon(getScaledImage(
                    new ImageIcon("res/icon/sort.png").getImage(), 24, 24)));
            displayButton(SORT_BUTTON, "Sort alphabetically", e -> COLLECTION.sort(String::compareTo));
            Application.GUI.add(SORT_BUTTON);

            final JButton DATE_BUTTON = new JButton(new ImageIcon(getScaledImage(
                    new ImageIcon("res/icon/date.png").getImage(), 24, 24)));
            displayButton(DATE_BUTTON, "Sort by date", e -> System.out.println("sort by date"));
            Application.GUI.add(DATE_BUTTON);

            add(HEART_BUTTON);
            add(RANDOM_BUTTON);
            add(SORT_BUTTON);
            add(DATE_BUTTON);
        }

        /**
         * Resizes an image using a Graphics2D object backed by a BufferedImage.
         * @param srcImg - source image to scale
         * @param w - desired width
         * @param h - desired height
         * @return - the new resized image
         */
        Image getScaledImage(Image srcImg, int w, int h){
            BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);
            Graphics2D g2 = resizedImg.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(srcImg, 0, 0, w, h, null);
            g2.dispose();

            return resizedImg;
        }
        void displayButton(JButton button, String tooltip, ActionListener action) {
            this.add(button);
            button.setOpaque(false);
            button.setBorder(null);
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
            button.setToolTipText(tooltip);
            button.addActionListener(action);
        }
        void addToList(String word) {
            SideView.COLLECTION.add(word);
            System.out.println("added " + word + " to collection");
        }
    }

    public SideView() {
        setLayout(new GridLayout(COLLECTION.size(), 1));
        displayLabels();
    }

    void displayLabels() {
        JLabel[] labels = createLabels();
        for (JLabel label : labels) {
            this.add(label);
            label.setFont(new Font("Times New Roman", Font.PLAIN, 14));
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

    static void read() throws FileNotFoundException {
        File file = new File("data/words.txt");
        Scanner scanner = new Scanner(file);

        while (scanner.hasNext()) {
            String words = scanner.next();
            COLLECTION.add(words);
        }

        COLLECTION.sort(String::compareTo);
        scanner.close();
    }
}