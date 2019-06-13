package Demoes;

import input.Horipad;
import input.Keyboard;
import main.Ults;
import main.Vector2D;
import physics.AABB;
import physics.PhysicsWorld;
import rendering.Camera;
import rendering.RenderEngine;
import rendering.Renderable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.security.Key;

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
    RenderEngine screen;
    @Override
    public void run() {
        screen = RenderEngine.getInstance();
        Renderable r = screen.add(new Renderable(100, 10, 20, 20, 1.0, 0, "resources/wall.png"));
        Renderable r1 = screen.add(new Renderable(100, 10, 20, 20, 1.0, 0, "resources/wall.png"));
        Renderable r0 = screen.add(new Renderable(100, 100, 20, 20, 10.0, 0, "resources/wall.png"));
        //Renderable r_fb2 = screen.add(new Renderable(100, 150, 20, 20, 1.0, 0, "resources/wall.png"));

        PhysicsWorld world = PhysicsWorld.getInstance();
        AABB FallingBox = world.add(new AABB(new Vector2D(100, 500), 20, 20, 10));
        AABB FallingBox2 = world.add(new AABB(new Vector2D(100, 550), 20, 20, 10));
        FallingBox.gravity = new Vector2D(0, -98);
        FallingBox2.gravity = new Vector2D(0, -98/2);
        AABB ground = world.add(new AABB(new Vector2D(100, 100), 200, 200, 0));
        Keyboard k = Keyboard.getInstance();

        r0.setPosition(ground.pos);
        while (true){
            if (k.justPressed(KeyEvent.VK_SPACE)) {
                FallingBox.force.add(new Vector2D(0, 100000));
            }
            world.step(1.0/60.0);
            r.setPosition(FallingBox.pos);
            r1.setPosition(FallingBox2.pos);
            screen.repaint();
            k.updateStates();
            Ults.sleep(1000/60);
        }
    }
}
