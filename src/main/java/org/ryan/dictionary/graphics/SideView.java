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

    static class SideButtonView extends JPanel {

        public SideButtonView() {
            displayButton(new JButton(new ImageIcon(getScaledImage(
                    new ImageIcon("res/icon/random.png").getImage(), 24, 24))),
                    "Search random word",
                    e -> new DictionaryView().search(COLLECTION.get(
                            ThreadLocalRandom.current().nextInt(0, SideView.COLLECTION.size()))));

            displayButton(new JButton(new ImageIcon(getScaledImage(
                    new ImageIcon("res/icon/sort.png").getImage(), 24, 24))),
                    "Sort alphabetically",
                    e -> COLLECTION.sort(String::compareTo));

            displayButton(new JButton(new ImageIcon(getScaledImage(
                    new ImageIcon("res/icon/date.png").getImage(), 24, 24))),
                    "Sort by date added",
                    e -> System.out.println("sort by date"));
        }

        /**
         * Resizes an image using a Graphics2D object backed by a BufferedImage.
         * @param srcImg - source image to scale
         * @param w - desired width
         * @param h - desired height
         * @return - the new resized image
         */
        static Image getScaledImage(Image srcImg, int w, int h){
            BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);
            Graphics2D g2 = resizedImg.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(srcImg, 0, 0, w, h, null);
            g2.dispose();

            return resizedImg;
        }

        void displayButton(JButton button, String tooltip, ActionListener action) {
            this.add(button);
            button.setLayout(null);
            button.setOpaque(false);
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
            button.setToolTipText(tooltip);
            button.addActionListener(action);
        }
    }

    static final List<String> COLLECTION = new ArrayList<>();

    public SideView() {
        setLayout(new GridLayout(COLLECTION.size(), 1));
        displayLabels();
    }

    void displayLabels() {
        JLabel[] labels = createLabels();
        for (JLabel label : labels) {
            this.add(label);
            label.setFont(new Font("Times New Roman", Font.PLAIN, 15));
            label.setBackground(Color.WHITE);
            label.setForeground(Color.BLACK);
        }
    }

    JLabel[] createLabels() {
        JLabel[] labels = new JLabel[COLLECTION.size()];
        for(int i = 0; i < COLLECTION.size(); i++) {
            labels[i] = new JLabel(COLLECTION.get(i));
            String word = COLLECTION.get(i);
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

    static void read() throws FileNotFoundException {
        File file = new File("words.txt");
        Scanner scanner = new Scanner(file);

        while (scanner.hasNext()) {
            String words = scanner.next();
            COLLECTION.add(words);
        }

        COLLECTION.sort(String::compareTo);
        scanner.close();
    }
}