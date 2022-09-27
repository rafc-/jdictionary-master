package org.ryan.dictionary.graphics;

import lombok.extern.java.Log;
import org.ryan.dictionary.graphics.control.MouseHandler;

import javax.swing.*;
import java.io.FileNotFoundException;

@Log
public class Application extends JFrame {

    private final String VERSION = "alpha0.1_1";
    private final String NAME = "jdictionary " + VERSION;
    public static Application app;
    public static int yOffset = 0;
    public static int xOffset = 0;

    public Application() {
        setTitle(NAME);
        setSize(1400, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //add(new DictionaryView());
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

    public static void main(String[] args) throws FileNotFoundException {
        SideView.read();
        app = new Application();
    }
}
