package main;

import input.Keyboard;
import physics.AABB;
import physics.PhysicsWorld;
import rendering.RenderEngine;
import rendering.Renderable;

import java.awt.event.KeyEvent;

public class Player {
    AABB hitbox;
    Renderable sprite;
    final String sprite_right_path = "resources/wall.png";
    public double x, y;
    public Player(int x, int y) {
        this.x = (double) x;
        this.y = (double) y;
        hitbox = PhysicsWorld.getInstance().add(new AABB(new Vector2D(x, y), 20, 20, 10));
        hitbox.gravity = new Vector2D(0, -98*6);
        hitbox.restitution = 1.0;

        sprite = RenderEngine.getInstance().add(new Renderable(x, y, 20, 20, sprite_right_path));
    }
    public void handle_inputs(Keyboard k) {
        if (k.isDown(KeyEvent.VK_RIGHT)) {
            hitbox.force.add(new Vector2D(1000, 0));
        } else if (k.isDown(KeyEvent.VK_LEFT)) {
            hitbox.force.add(new Vector2D(-1000, 0));
        }
        if (k.justPressed(KeyEvent.VK_SPACE)) {
            hitbox.force.add(new Vector2D(0, 300000));
        }
    }
    public void update() {
        sprite.setPosition(hitbox.pos);
    }
}
