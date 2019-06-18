package main;

import input.Keyboard;
import physics.PhysicsWorld;
import rendering.Camera;
import rendering.RenderEngine;
import rendering.TextElement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class GameV1 extends JFrame implements Runnable{
    public GameV1() {


        initUI();
        (new Thread(this)).start(); //Need to seperate game logic from main thread to allow it to do repaints
    }
    private void initUI() {
        add(Keyboard.getInstance());
        add(RenderEngine.getInstance().setCamera(new Camera(0,0,1)));
        setResizable(false);
        setTitle("Garbage Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        requestFocusInWindow();
        pack();
        this.setLocation(0, 0);
        this.setVisible(true); //PUT THIS LAST
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            GameV1 ex = new GameV1();
        });
    }
    @Override
    public void run() {
        RenderEngine screen =  RenderEngine.getInstance();
        PhysicsWorld world = PhysicsWorld.getInstance();
        Keyboard k = Keyboard.getInstance();
        Player p = new Player(0, 0);

        Level_Manager lmanage = new Level_Manager(p, 8);

        TextElement instructions = screen.addText("Left/Right Arrows to move\nSpace to jump (You have double jump)\ns to stop movement\nr to restart", 1000, 20, 18);
        String death_base_message = "You have died: %d times";
        TextElement deathCount = screen.addText(String.format(death_base_message, p.getDeathCount()), 5, 20, 18);
        instructions.setColor(Color.RED);

        screen.getCurrentcam().trackPlayer(p);
        long start_time = System.currentTimeMillis();
        while (!lmanage.getPlayerWon()) {
            screen.repaint();
            p.handle_inputs(k);
            world.step(1.0/60.0); //Assumes 60 fps
            p.update();

            lmanage.update();

            deathCount.setMessage(String.format(death_base_message, p.getDeathCount()));

            //Dev Hack cause i don't feel like playing them all to test one
            if (k.isDown(KeyEvent.VK_PERIOD)){
                for (int x = KeyEvent.VK_1; x <= KeyEvent.VK_9; x++) {
                    if (k.justPressed(x)) {
                        lmanage.changeLevel(x-KeyEvent.VK_1);
                    }
                }
            }

            k.updateStates();
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
