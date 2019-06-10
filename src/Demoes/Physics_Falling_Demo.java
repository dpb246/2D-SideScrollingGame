package Demoes;

import input.Horipad;
import input.Keyboard;
import main.Ults;
import main.Vector2D;
import physics.Box;
import physics.PhysicsEngine;
import rendering.Camera;
import rendering.RenderEngine;
import rendering.Renderable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

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
        Renderable r0 = screen.add(new Renderable(100, 150, 20, 20, 10.0, 0, "resources/wall.png"));
        Renderable r_fb2 = screen.add(new Renderable(100, 150, 20, 20, 1.0, 0, "resources/wall.png"));

        PhysicsEngine world = new PhysicsEngine(new Vector2D(0,-9.8*10));
        Box FallingBox = new Box(100.0,300,20.0,20.0, false, 10.0, 150.0, 150.0);
        Box fb2 = new Box(100.0,250,20.0,20.0, false, 10.0, 150.0, 150.0);
        Box floor = new Box(100, 100, 200.0, 200.0, true);
        world.add(FallingBox).add(floor).add(fb2);

        r0.setPosition(floor.getPosition());
        r_fb2.setPosition(fb2.getPosition());

        Keyboard k = Keyboard.getInstance();
        Horipad h = new Horipad(0.5f, false);

        for (int i=0; i<1000; i++){
            world.simulate();
            r.setPosition(FallingBox.getPosition());
            r0.setPosition(floor.getPosition());
            r_fb2.setPosition(fb2.getPosition());
            screen.repaint();

            inputHandle(k,h,fb2);

            Ults.sleep(1000/60);
        }
    }

    private void inputHandle(Keyboard k, Horipad h, Box box){
        h.input(false);
        k.updateStates();
        if (k.isDown(KeyEvent.VK_UP) || h.isDown("UP")) {
            //screen.getCurrentcam().changeY(1);
        }
        if (k.isDown(KeyEvent.VK_DOWN) || h.isDown("DOWN")) {
            //screen.getCurrentcam().changeY(-1);
        }
        if (k.isDown(KeyEvent.VK_RIGHT) || h.isDown("RIGHT")) {
            box.addForce(new Vector2D(100,0));
            //screen.getCurrentcam().changeX(-1);
        }
        if (k.isDown(KeyEvent.VK_LEFT) || h.isDown("LEFT")) {
            box.addForce(new Vector2D(-100,0));
            //screen.getCurrentcam().changeX(1);
        }
        if (k.isDown(KeyEvent.VK_EQUALS) || h.isDown("A")) {
            //screen.getCurrentcam().changeZoom(0.01);
        }
        if (k.isDown(KeyEvent.VK_MINUS) || h.isDown("B")) {
            //creen.getCurrentcam().changeZoom(-0.01);
        }
    }
}
