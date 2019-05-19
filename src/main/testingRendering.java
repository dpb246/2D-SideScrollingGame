package main;

import input.Keyboard;
import rendering.Camera;
import rendering.RenderEngine;
import rendering.Renderable;

import javax.swing.*;
import java.awt.*;

public class testingRendering extends JFrame {
    public testingRendering() {
        initUI();
    }
    private void initUI() {
        add(Keyboard.getInstance());
        add(RenderEngine.getInstance());
        RenderEngine.getInstance().add(new Renderable(10, 10, 20, 20, 1.0, Math.PI/8, "resources/wall.png"));
        RenderEngine.getInstance().add(new Renderable(100, 100, 20, 20, 2.0, Math.PI/4, "resources/wall.png"));
        RenderEngine.getInstance().add(new Renderable(100, 100, 20, 20, 1.0, 0, "resources/wall.png"));
        RenderEngine.getInstance().setCamera(new Camera(0,0,1));
        RenderEngine.getInstance().repaint();
        setResizable(false);
        setTitle("Testing Rendering");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        requestFocusInWindow();
        pack();
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            testingRendering ex = new testingRendering();
            ex.setVisible(true);
        });
    }
}
