package Demoes;

import input.Keyboard;
import main.Player;
import main.Ults;
import main.Vector2D;
import physics.AABB;
import physics.PhysicsWorld;
import rendering.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.security.Key;

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

        Player p = new Player(50, 300);

        AABB a = world.add(new AABB(new Vector2D(40, 120), 80, 80, 0));
        a.restitution = 1.0;


        screen.setLevel(l);
        screen.getCurrentcam().setZoom(0.5);
        TextElement t = screen.addText("LEVEL 1", 5, 42);
        long start_time = System.currentTimeMillis();
        while (true) {
            screen.repaint();
            p.handle_inputs(k);
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
            p.update();
            k.updateStates();
            long sleep = Math.max(1, 16-(System.currentTimeMillis()-start_time));
            Ults.sleep(sleep);
            start_time = System.currentTimeMillis();
        }
    }
}
