package org.ryan.dictionary.graphics;

import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class ButtonView extends JPanel {

    public ButtonView() {
        final JButton HEART_BUTTON = new JButton(new ImageIcon(getScaledImage("icon/heart.png", 26, 26)));
        setButton(HEART_BUTTON, "Add word to list", e -> addToList(DictionaryView.asset.getData().getWord()));
        Application.GUI.add(HEART_BUTTON);

        final JButton RANDOM_BUTTON = new JButton(new ImageIcon(getScaledImage("icon/random.png", 30, 30)));
        setButton(RANDOM_BUTTON, "Search random word", e -> new DictionaryView().search(ListView.COLLECTION.get(
                ThreadLocalRandom.current().nextInt(0, ListView.COLLECTION.size()))));
        Application.GUI.add(RANDOM_BUTTON);

        final JButton SORT_BUTTON = new JButton(new ImageIcon(getScaledImage("icon/sort.png", 26, 26)));
        setButton(SORT_BUTTON, "Sort alphabetically", e -> ListView.COLLECTION.sort(String::compareTo));
        Application.GUI.add(SORT_BUTTON);

        final JButton DATE_BUTTON = new JButton(new ImageIcon(getScaledImage("icon/date.png", 32, 32)));
        setButton(DATE_BUTTON, "Sort by date", e -> System.out.println("sort by date"));
        Application.GUI.add(DATE_BUTTON);
    }

    /**
     * Resizes an image using a Graphics2D object backed by a BufferedImage.
     * @param path - source path of image to scale
     * @param w - desired width
     * @param h - desired height
     * @return - the new resized image
     */
    @SneakyThrows
    Image getScaledImage(String path, int w, int h) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(path);
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(ImageIO.read(Objects.requireNonNull(is)), 0, 0, w, h, null);
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
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("bgbuttons_new.png");
        g.drawImage(ImageIO.read(Objects.requireNonNull(is)), 0, 0, null);
    }
}
