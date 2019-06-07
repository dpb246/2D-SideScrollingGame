package main;

import input.Keyboard;
import rendering.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.security.Key;

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
        String path_Level1 = "resources/Levels/test.txt";
        String path_Level2 = "resources/Levels/test2.txt";
        Level_Tile l = new Level_Tile(0, 0).load_from_file(path_Level1);
        Level_Tile l2 = new Level_Tile(0, 0).load_from_file(path_Level2);
        screen.setLevel(l);
        screen.getCurrentcam().setZoom(0.5);
        TextElement t = screen.addText("LEVEL 1", 5, 42);
        long start_time = System.currentTimeMillis();
        while (true) {
            screen.repaint();
            if (k.isDown(KeyEvent.VK_RIGHT)) {
                screen.getCurrentcam().changeX(2);
            }
            if (k.isDown(KeyEvent.VK_LEFT)) {
                screen.getCurrentcam().changeX(-2);
            }
            if (k.justPressed(KeyEvent.VK_N)) {
                t.message = "LEVEL 2";
                screen.setLevel(l2);
            }
            if (k.justPressed(KeyEvent.VK_R)) {
                t.message = "LEVEL 1";
                screen.setLevel(l);
            }

            long sleep = Math.max(1, 16-(System.currentTimeMillis()-start_time));
            Ults.sleep(sleep);
            start_time = System.currentTimeMillis();
        }
    }
}
