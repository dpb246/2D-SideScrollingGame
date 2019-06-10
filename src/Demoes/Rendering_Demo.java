package Demoes;

import input.Keyboard;
import main.Ults;
import rendering.Camera;
import rendering.RenderEngine;
import rendering.Renderable;

import javax.swing.*;
import java.awt.*;

public class Rendering_Demo extends JFrame implements Runnable{
    public Rendering_Demo() {
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
            Rendering_Demo ex = new Rendering_Demo();
        });
    }
    @Override
    public void run() {
        RenderEngine screen =  RenderEngine.getInstance();
        screen.add(new Renderable(10, 10, 20, 20, "resources/wall.png"));
        Renderable spin = screen.add(new Renderable(100, 100, 20, 20, 2.0, Math.PI/4, "resources/wall.png"));
        Renderable r = screen.add(new Renderable(100, 100, 20, 20, 1.0, 0, "resources/wall.png"));
        for (double d  = 0.0; d < Math.PI*2; d += 0.02) {
            spin.setAngle(d);
            screen.getCurrentcam().changeX(-1).setZoom(d/2);
            screen.repaint();
            Ults.sleep(16);
        }
        screen.getCurrentcam().setX(0).setZoom(1);
        screen.repaint();
    }
}
