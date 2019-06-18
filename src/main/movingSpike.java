package main;

import physics.AABB;
import physics.PhysicsWorld;
import physics.callback;
import rendering.RenderEngine;
import rendering.Renderable;

import javax.swing.*;
import java.awt.*;

public class movingSpike extends GameObject {
    public AABB hitbox;
    private int frame_count;
    final int frames_before_disappear = 30;
    private boolean playerTouched = false;
    private Image sand = (new ImageIcon("./resources/Blocks/spikes.png")).getImage();
    private Renderable image;
    private int moveDistance;
    private double x, y, TILE_SIZE;
    private double move_step = 150*1.0/60.0;
    private double direction = 1;
    public movingSpike(double x, double y, double TILE_SIZE, int move_distance) {
        this.x = x;
        this.y = y;
        this.moveDistance = move_distance;
        this.TILE_SIZE = TILE_SIZE;
        hitbox = PhysicsWorld.getInstance().add(new AABB(new Vector2D(x, y), TILE_SIZE, TILE_SIZE, 0));
        hitbox.bitmask = 1;
        hitbox.type = "spike";
        image = RenderEngine.getInstance().add(new Renderable(x, y, TILE_SIZE, TILE_SIZE, sand));
    }
    @Override
    public void update() {
        if (hitbox.pos.x < x) {
            direction = 1;
        } else if (hitbox.pos.x > x+moveDistance*TILE_SIZE) {
            direction = -1;
        }
        hitbox.pos.x += direction*move_step;
        image.setPosition(hitbox.pos);
    }

    @Override
    public void reset() {
        hitbox.pos.x = x;
        hitbox.pos.y = y;
        image.setPosition(hitbox.pos);
        direction = 1;
    }
}
