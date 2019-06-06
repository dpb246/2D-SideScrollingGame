package main;

import input.Keyboard;
import rendering.Camera;
import rendering.Level_Tile;
import rendering.RenderEngine;
import rendering.Renderable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Level_Tiling_Test extends JFrame implements Runnable{
    public Level_Tiling_Test() {
        initUI();
        (new Thread(this)).start();
    }
    private void initUI() {
        add(Keyboard.getInstance());
        add(RenderEngine.getInstance().setCamera(new Camera(0,0,1)));
        setResizable(false);
        setTitle("Rendering Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        requestFocusInWindow();
        pack();
        this.setVisible(true); //PUT THIS LAST
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Level_Tiling_Test ex = new Level_Tiling_Test();
        });
    }
    @Override
    public void run() {
        RenderEngine screen =  RenderEngine.getInstance();
        Keyboard k = Keyboard.getInstance();
        Level_Tile l = new Level_Tile(0, 0).load_from_file("test.txt");
        screen.setLevel(l);
        while (true) {
            if (k.isDown(KeyEvent.VK_RIGHT)) {
                screen.getCurrentcam().changeX(2);
            }
            if (k.isDown(KeyEvent.VK_LEFT)) {
                screen.getCurrentcam().changeX(-2);
            }
            screen.repaint();
            Ults.sleep(16);
        }
    }
}
