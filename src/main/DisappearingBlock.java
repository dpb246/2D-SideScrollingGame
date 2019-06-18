package main;

import physics.AABB;
import physics.PhysicsWorld;
import physics.callback;
import rendering.RenderEngine;
import rendering.Renderable;

import javax.swing.*;
import java.awt.*;

public class DisappearingBlock extends GameObject{
    public AABB hitbox;
    private int frame_count;
    final int frames_before_disappear = 30;
    private boolean playerTouched = false;
    private Image sand = (new ImageIcon("./resources/Blocks/sandBlock.png")).getImage();
    private Renderable image;
    public DisappearingBlock(double x, double y, double TILE_SIZE) {
        hitbox = PhysicsWorld.getInstance().add(new AABB(new Vector2D(x, y), TILE_SIZE, TILE_SIZE, 0));
        hitbox.bitmask = 1;
        hitbox.callbacks = new callback() {
            @Override
            public void on_hit(AABB other, Vector2D normal) {
                if (other.type == "player") {
                    playerTouched = true;
                }
            }
        };
        image = RenderEngine.getInstance().add(new Renderable(x, y, TILE_SIZE, TILE_SIZE, sand));
    }
    public void reset() {
        hitbox.bitmask = 1;
        image.setVisible(true);
        frame_count = 0;
        playerTouched = false;
    }
    public void update() {
        if (frame_count >= frames_before_disappear) {
            hitbox.bitmask = 3;
            image.setVisible(false);
        } else if (frame_count >= frames_before_disappear/2) {
            if (frame_count%2 == 0) {
                image.setVisible(!image.isVisible());
            }
            frame_count++;
        } else if (playerTouched) {
            frame_count++;
        }
    }
}
