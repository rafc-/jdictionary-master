package org.ryan.dictionary.graphics;

import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.ryan.dictionary.graphics.control.MouseHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log
public class Application extends JFrame {

    public static Application app;
    static final List<Component> GUI = new ArrayList<>();
    public static int yOffset = 0;
    public static int xOffset = 0;

    public Application() {
        final String VERSION = "alpha0.3_7";
        final String TITLE = "jdictionary " + VERSION;
        setTitle(TITLE);
        setSize(1400, 800);
        setLocationRelativeTo(null);
        JSplitPane viewSplitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        JSplitPane sideviewSplitter = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        viewSplitter.setEnabled(false);
        viewSplitter.setDividerLocation(1200);
        viewSplitter.setDividerSize(0);
        viewSplitter.setLeftComponent(new DictionaryView());
        viewSplitter.setRightComponent(sideviewSplitter);
        sideviewSplitter.setEnabled(false);
        sideviewSplitter.setDividerLocation(50);
        sideviewSplitter.setDividerSize(0);
        sideviewSplitter.setTopComponent(new ButtonView());
        sideviewSplitter.setBottomComponent(getScrollPane());
        add(viewSplitter);
        addMouseWheelListener(new MouseHandler());
        setResizable(false);
        setVisible(true);
    }

    Component getScrollPane() {
        JViewport viewport = new JViewport() {
            @SneakyThrows
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(ImageIO.read(new File("res/bglist.png")), 0, 0, null);
            }
        };
        JScrollPane jsp = new JScrollPane();
        jsp.setViewport(viewport);
        jsp.setViewportView(new ListView());

        return jsp;
    }

    public static void main(String[] args) throws IOException {
        ListView.read();
        app = new Application();
    }
}
