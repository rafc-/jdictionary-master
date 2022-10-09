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

    @SneakyThrows
    public Application() {
        String version = "alpha0.3_6";
        String title = "jdictionary " + version;
        setTitle(title);
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
        sideviewSplitter.setTopComponent(new SideView.SideButtonView());
        sideviewSplitter.setBottomComponent(new JScrollPane(new SideView()));
        add(viewSplitter);
        addMouseWheelListener(new MouseHandler());
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        SideView.read();
        app = new Application();
    }


    @SneakyThrows
    @Override
    public void paint(Graphics g) {
        g.drawImage(ImageIO.read(new File("res/bg.png")), 0, 0, null);

        if (DictionaryView.asset != null) {
            Application.GUI.forEach(Component::repaint);
            DictionaryView.asset.paint(g);
        }
    }
}
