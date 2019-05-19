package main;

import input.Keyboard;
import rendering.Camera;
import rendering.RenderEngine;
import rendering.Renderable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.security.Key;

public class Camera_Demo extends JFrame implements Runnable{
    public Camera_Demo() {
        initUI();
        (new Thread(this)).start();
    }
    private void initUI() {
        add(Keyboard.getInstance());
        add(RenderEngine.getInstance().setCamera(new Camera(0,0,1)));
        setResizable(false);
        setTitle("Camera Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        requestFocusInWindow();
        pack();
        this.setVisible(true);  //PUT THIS LAST
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Camera_Demo ex = new Camera_Demo();
        });
    }
    @Override
    public void run() {
        RenderEngine screen =  RenderEngine.getInstance();
        Keyboard k = Keyboard.getInstance();
        screen.add(new Renderable(10, 10, 20, 20, "resources/wall.png"));
        Renderable spin = screen.add(new Renderable(100, 100, 20, 20, 2.0, Math.PI/4, "resources/wall.png"));
        Renderable r = screen.add(new Renderable(100, 100, 20, 20, 1.0, 0, "resources/wall.png"));
        while(true) {
            if (k.isDown(KeyEvent.VK_UP)) {
                screen.getCurrentcam().changeY(1);
            }
            if (k.isDown(KeyEvent.VK_DOWN)) {
                screen.getCurrentcam().changeY(-1);
            }
            if (k.isDown(KeyEvent.VK_RIGHT)) {
                screen.getCurrentcam().changeX(-1);
            }
            if (k.isDown(KeyEvent.VK_LEFT)) {
                screen.getCurrentcam().changeX(1);
            }
            if (k.isDown(KeyEvent.VK_EQUALS)) {
                screen.getCurrentcam().changeZoom(0.01);
            }
            if (k.isDown(KeyEvent.VK_MINUS)) {
                screen.getCurrentcam().changeZoom(-0.01);
            }
            screen.repaint();
            k.updateStates();
            Ults.sleep(16);
        }
    }
}
