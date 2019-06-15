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
        (new Thread(this)).start(); //Need to seperate game logic from main thread to allow it to do repaints
    }
    private void initUI() {
        add(Keyboard.getInstance());
        add(RenderEngine.getInstance().setCamera(new Camera(0,0,1)));
        setResizable(false);
        setTitle("TEST Demo");
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
        String path_Level = "resources/Levels/test2.txt";
        Level_Tile l = new Level_Tile(0, 0).load_from_file(path_Level);

        Player p = new Player(50, 300);

        screen.setLevel(l);
        screen.getCurrentcam().setZoom(0.5);  //Cause why change sprite size when you can do this LOL

        TextElement t = screen.addText("LEVEL Test", 5, 42);
        TextElement instructions = screen.addText("Left/Right Arrows to move\nSpace to jump (You have double jump)\ns to stop movement\nmake sure to read console output", 5, 60, 18);
        instructions.setColor(Color.RED);

        long start_time = System.currentTimeMillis();
        while (true) {
            screen.repaint();
            p.handle_inputs(k);
            world.step(1.0/60.0); //Assumes 60 fps
            p.update();
            k.updateStates();
            long sleep = Math.max(1, 16-(System.currentTimeMillis()-start_time)); //Attempt to provide stable 60 fps
            Ults.sleep(sleep);
            start_time = System.currentTimeMillis();
        }
    }
}
