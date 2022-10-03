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

@Log
public class SideView extends JPanel {

    final Font font = new Font("Times New Roman", Font.PLAIN, 15);

    final int LINE_SPACING = 10;

    static final List<String> COLLECTION = new ArrayList<>();

    public SideView() {
        setFont(font);

        ImageIcon random = new ImageIcon("res/icon/random.png");
        JButton randomButton = new JButton(new ImageIcon(getScaledImage(random.getImage(), 24, 24)));
        displayButton(randomButton, "Search random word", e -> new DictionaryView().search(COLLECTION.get(DictionaryView.random)));

        ImageIcon alphabet = new ImageIcon("res/icon/sort.png");
        JButton alphabetButton = new JButton(new ImageIcon(getScaledImage(alphabet.getImage(), 24, 24)));
        displayButton(alphabetButton, "Sort alphabetically", e -> COLLECTION.sort(String::compareTo));

        ImageIcon date = new ImageIcon("res/icon/date.png");
        JButton dateButton = new JButton(new ImageIcon(getScaledImage(date.getImage(), 24, 24)));
        displayButton(dateButton, "Sort by date added", e -> System.out.println("sort by date"));

        setLayout(new GridLayout(COLLECTION.size(), 1));
        displayLabels();
    }

    void displayButton(JButton button, String tooltip, ActionListener action) {
        this.add(button);
        button.setOpaque(false);
        button.setBorderPainted(true);
        button.setContentAreaFilled(false);
        button.setToolTipText(tooltip);
        button.addActionListener(action);
    }

    /**
     * Resizes an image using a Graphics2D object backed by a BufferedImage.
     * @param srcImg - source image to scale
     * @param w - desired width
     * @param h - desired height
     * @return - the new resized image
     */
    public static Image getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }

    void displayLabels() {
        int y = 50;
        int realY = y - Application.yOffset;
        int lines = 0;

        Label[] labels = createLabels();
        for (Label label : labels) {
            this.add(label);

            label.setBackground(Color.BLACK);
            label.setForeground(Color.WHITE);
            label.setBounds(50, realY + (18 + LINE_SPACING) * lines++, 150, 20);
        }
    }

    Label[] createLabels() {
        Label[] labels = new Label[COLLECTION.size()];
        for(int i = 0; i < COLLECTION.size(); i++) {
            labels[i] = new Label(COLLECTION.get(i));
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