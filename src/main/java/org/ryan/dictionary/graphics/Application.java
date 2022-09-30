package org.ryan.dictionary.graphics;

import lombok.extern.java.Log;
import org.ryan.dictionary.graphics.control.MouseHandler;

import javax.swing.*;
import java.io.IOException;

@Log
public class Application extends JFrame {

    public static Application app;
    public static int yOffset = 0;
    public static int xOffset = 0;

    public Application() {
        String version = "alpha0.2";
        String title = "jdictionary " + version;
        setTitle(title);
        setSize(1400, 800);
        setLocationRelativeTo(null);
        JSplitPane splitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitter.setDividerLocation(1125);
        splitter.setEnabled(false);
        splitter.setLeftComponent(new DictionaryView());
        splitter.setRightComponent(new SideView());
        add(splitter);
        addMouseWheelListener(new MouseHandler());
        setResizable(true);
        setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        SideView.read();
        app = new Application();
    }
}
