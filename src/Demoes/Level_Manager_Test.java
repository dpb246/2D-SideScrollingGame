package Demoes;

import input.Keyboard;
import main.Level_Manager;
import main.Player;
import main.Ults;
import physics.PhysicsWorld;
import rendering.Camera;
import rendering.RenderEngine;
import rendering.TextElement;

import javax.swing.*;
import java.awt.*;

public class Level_Manager_Test extends JFrame implements Runnable{
    public Level_Manager_Test() {
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
            Level_Manager_Test ex = new Level_Manager_Test();
        });
    }
    @Override
    public void run() {
        RenderEngine screen =  RenderEngine.getInstance();
        PhysicsWorld world = PhysicsWorld.getInstance();
        Keyboard k = Keyboard.getInstance();
        Player p = new Player(0, 0);

        Level_Manager lmanage = new Level_Manager(p, 3);

        screen.getCurrentcam().setZoom(0.5);  //Cause why change sprite size when you can do this LOL

        //TextElement t = screen.addText("LEVEL 1", 5, 42);
        TextElement instructions = screen.addText("Left/Right Arrows to move\nSpace to jump (You have double jump)\ns to stop movement\nr to restart", 5, 20, 18);
        instructions.setColor(Color.RED);

        long start_time = System.currentTimeMillis();
        while (!lmanage.getPlayerWon()) {
            screen.repaint();
            p.handle_inputs(k);
            world.step(1.0/60.0); //Assumes 60 fps
            p.update();
            k.updateStates();
            lmanage.update();
            long sleep = Math.max(1, 16-(System.currentTimeMillis()-start_time)); //Attempt to provide stable 60 fps
            Ults.sleep(sleep);
            start_time = System.currentTimeMillis();
        }
        System.out.println("WIN!!");
        TextElement t = screen.addText("YOU WIN!", screen.getWidth()/2, screen.getHeight()/2-50, 100);
        t.centerOnX();
        screen.repaint();
        //YAYAYAYAY
    }
}
