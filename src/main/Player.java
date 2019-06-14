package main;

import input.Keyboard;
import physics.AABB;
import physics.PhysicsWorld;
import physics.callback;
import rendering.RenderEngine;
import rendering.Renderable;

import java.awt.event.KeyEvent;

public class Player {
    AABB hitbox;
    Renderable sprite;
    final String sprite_right_path = "resources/wall.png";
    public int health;
    public double x, y;
    private final Player myself = this;
    private int current_jump_count = 0;
    private int max_jump_count = 2;
    public Player(int x, int y) {
        this.x = (double) x;
        this.y = (double) y;
        this.health = 1;
        hitbox = PhysicsWorld.getInstance().add(new AABB(new Vector2D(x, y), 20, 20, 10));
        hitbox.gravity = new Vector2D(0, -98*6);
        hitbox.restitution = 1.0;
        hitbox.callbacks = new callback(){
            @Override
            public void on_hit(AABB other, Vector2D normal) {
                switch(other.type) {
                    case "spike":
                        myself.take_damage(1);
                        break;
                    case "goal":
                        myself.win();
                        break;
                    case "wood":
                        break;
                }
                if (normal.y > 0.0 || normal.x != 0.0) {
                    myself.reset_jump();
                }
            }
        };
        sprite = RenderEngine.getInstance().add(new Renderable(x, y, 20, 20, sprite_right_path));
    }
    public void reset_jump() {
        this.current_jump_count = 0;
    }
    public void win() {
        System.out.println("YAY");
    }
    public void take_damage(int amount) {
        health -= amount;
    }
    public void handle_inputs(Keyboard k) {
        if (k.isDown(KeyEvent.VK_RIGHT)) {
            hitbox.force.add(new Vector2D(3000, 0));
        } else if (k.isDown(KeyEvent.VK_LEFT)) {
            hitbox.force.add(new Vector2D(-3000, 0));
        }
        if (k.justPressed(KeyEvent.VK_SPACE)) {
            if (current_jump_count < max_jump_count) {
                hitbox.force.add(new Vector2D(0, 300000));
                current_jump_count++;
            }
        }
        if (k.justPressed(KeyEvent.VK_S)) {
            hitbox.velocity.x = 0;
            hitbox.velocity.y = 0;
        }
    }
    public void update() {
        sprite.setPosition(hitbox.pos);
        if (this.health <= 0) {
            System.out.println("You suck loser");
            this.health = 3;
        }
    }
}
