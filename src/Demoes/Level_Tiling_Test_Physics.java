package Demoes;

import input.Keyboard;
import main.Ults;
import main.Vector2D;
import physics.AABB;
import physics.PhysicsWorld;
import rendering.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Level_Tiling_Test_Physics extends JFrame implements Runnable{
    public Level_Tiling_Test_Physics() {
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
            Level_Tiling_Test_Physics ex = new Level_Tiling_Test_Physics();
        });
    }
    @Override
    public void run() {
        RenderEngine screen =  RenderEngine.getInstance();
        PhysicsWorld world = PhysicsWorld.getInstance();

        Keyboard k = Keyboard.getInstance();
        String path_Level1 = "resources/Levels/test.txt";
        String path_Level2 = "resources/Levels/test2.txt";
        Level_Tile l = new Level_Tile(0, 0).load_from_file(path_Level2);
        //Level_Tile l2 = new Level_Tile(0, 0).load_from_file(path_Level2);

        Renderable r = screen.add(new Renderable(50, 500, 20, 20, 1.0, 0, "resources/wall.png"));
        AABB FallingBox = world.add(new AABB(new Vector2D(50, 500), 20, 20, 10));
        FallingBox.gravity = new Vector2D(0, -98);

        screen.setLevel(l);
        screen.getCurrentcam().setZoom(0.5);
        TextElement t = screen.addText("LEVEL 1", 5, 42);
        long start_time = System.currentTimeMillis();
        while (true) {
            screen.repaint();
            if (k.justPressed(KeyEvent.VK_RIGHT) || k.justReleased(KeyEvent.VK_LEFT)) {
                FallingBox.velocity.add(new Vector2D(200, 0));
            } else if (k.justReleased(KeyEvent.VK_RIGHT) || k.justPressed(KeyEvent.VK_LEFT)) {
                FallingBox.velocity.add(new Vector2D(-200, 0));
            }
            if (k.justPressed(KeyEvent.VK_SPACE)) {
                FallingBox.force.add(new Vector2D(0, 100000));
            }
//            if (k.justPressed(KeyEvent.VK_N)) {
//                t.setMessage("LEVEL 2").setColor(Color.RED);
//                screen.setLevel(l2);
//                screen.getCurrentcam().reset().setZoom(0.5);
//            }
            if (k.justPressed(KeyEvent.VK_R)) {
                t.setMessage("LEVEL 1").setColor(Color.YELLOW);
                screen.setLevel(l);
                screen.getCurrentcam().reset().setZoom(0.5);
            }
            world.step(1.0/60.0);
            r.setPosition(FallingBox.pos);
            k.updateStates();
            long sleep = Math.max(1, 16-(System.currentTimeMillis()-start_time));
            Ults.sleep(sleep);
            start_time = System.currentTimeMillis();
        }
    }
}
