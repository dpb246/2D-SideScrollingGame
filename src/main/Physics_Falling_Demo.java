package main;

import input.Keyboard;
import physics.Box;
import physics.PhysicsEngine;
import rendering.Camera;
import rendering.RenderEngine;
import rendering.Renderable;

import javax.swing.*;
import java.awt.*;

public class Physics_Falling_Demo extends JFrame implements Runnable{
    public Physics_Falling_Demo() {
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
            Physics_Falling_Demo ex = new Physics_Falling_Demo();
        });
    }
    @Override
    public void run() {
        RenderEngine screen =  RenderEngine.getInstance();
        Renderable r = screen.add(new Renderable(100, 10, 20, 20, 1.0, 0, "resources/wall.png"));
        PhysicsEngine world = new PhysicsEngine(9.8);
        Box test = new Box(100.0,10.0,20.0,20.0,10.0, false, 10.0, 10.0);
        world.add(test);
        while(true){
            world.simulate();
            r.setPosition(test.getPosition().scaled(5));
            screen.repaint();
            Ults.sleep(1000/60);
        }
    }
}
