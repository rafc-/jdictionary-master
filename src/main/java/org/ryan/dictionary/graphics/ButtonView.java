package org.ryan.dictionary.graphics;

import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

public class ButtonView extends JPanel {

    public ButtonView() {
        final JButton HEART_BUTTON = new JButton(new ImageIcon(getScaledImage(
                new ImageIcon("res/icon/heart.png").getImage(), 24, 24)));
        setButton(HEART_BUTTON, "Add word to list", e -> addToList(DictionaryView.asset.getData().getWord()));
        Application.GUI.add(HEART_BUTTON);

        final JButton RANDOM_BUTTON = new JButton(new ImageIcon(getScaledImage(
                new ImageIcon("res/icon/random.png").getImage(), 24, 24)));
        setButton(RANDOM_BUTTON, "Search random word", e -> new DictionaryView().search(ListView.COLLECTION.get(
                ThreadLocalRandom.current().nextInt(0, ListView.COLLECTION.size()))));
        Application.GUI.add(RANDOM_BUTTON);

        final JButton SORT_BUTTON = new JButton(new ImageIcon(getScaledImage(
                new ImageIcon("res/icon/sort.png").getImage(), 24, 24)));
        setButton(SORT_BUTTON, "Sort alphabetically", e -> ListView.COLLECTION.sort(String::compareTo));
        Application.GUI.add(SORT_BUTTON);

        final JButton DATE_BUTTON = new JButton(new ImageIcon(getScaledImage(
                new ImageIcon("res/icon/date.png").getImage(), 24, 24)));
        setButton(DATE_BUTTON, "Sort by date", e -> System.out.println("sort by date"));
        Application.GUI.add(DATE_BUTTON);
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

    void setButton(JButton button, String tooltip, ActionListener action) {
        this.add(button);
        button.setBorder(null);
        button.setOpaque(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setToolTipText(tooltip);
        button.addActionListener(action);
    }

    void addToList(String word) {
        ListView.COLLECTION.add(word);
        System.out.println("added " + word + " to collection");
    }

    @SneakyThrows
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(ImageIO.read(new File("res/bgbuttons.png")), 0, 0, null);
    }
}
