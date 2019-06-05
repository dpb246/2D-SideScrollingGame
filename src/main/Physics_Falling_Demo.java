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
        RenderEngine screen = RenderEngine.getInstance();
        Renderable r = screen.add(new Renderable(100, 10, 20, 20, 1.0, 0, "resources/wall.png"));
        Renderable r0 = screen.add(new Renderable(500, 500, 20, 20, 10.0, 0, "resources/wall.png"));

        PhysicsEngine world = new PhysicsEngine(new Vector2D(0,9.8));
        Box FallingBox = new Box(100.0,20.0,20.0,20.0, false, 10.0, 15.0, 15.0);
        Box floor = new Box(50, 500, 200.0, 200.0, true);
        world.add(FallingBox).add(floor);


        for (int i=0; i<1000; i++){
            world.simulate();
            r.setPosition(FallingBox.getPosition().scaled(5));
            screen.repaint();
            Ults.sleep(1000/60);
        }
    }
}
