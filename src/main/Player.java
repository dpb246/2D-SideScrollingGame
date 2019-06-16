package main;

import input.Keyboard;
import physics.AABB;
import physics.PhysicsWorld;
import physics.callback;
import rendering.RenderEngine;
import rendering.Renderable;

import java.awt.event.KeyEvent;

/**
 * CLASSESSS YAAAAA....
 * Stores all information relavent to the player including hitbox and keyboard
 */
public class Player {
    private AABB hitbox;
    private Renderable sprite;
    private final String sprite_right_path = "resources/wall.png";
    private int health;
    public double x, y;
    private final Player myself = this;
    private boolean on_ground = false;
    private int current_jump_count = 0;
    private int max_jump_count = 2;
    private boolean want_next_level = false;
    private boolean need_restart = false;
    public Player(int x, int y) {
        this.x = (double) x;
        this.y = (double) y;
        this.health = 1;
        hitbox = PhysicsWorld.getInstance().add(new AABB(new Vector2D(x, y), 20, 20, 10));
        hitbox.gravity = new Vector2D(0, -98*6);
        hitbox.restitution = 1.0;
        hitbox.type = "player";
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
                myself.seton_ground(true);
                if (normal.y > 0.0 || normal.x != 0.0) {
                    myself.reset_jump();
                }
            }
        };
        sprite = RenderEngine.getInstance().add(new Renderable(x, y, 20, 20, sprite_right_path));
    }

    /**
     * At this point if you can't understand these methods try Alt-F4
     */
    public void reset_jump() {
        this.current_jump_count = 0;
    }
    public void win() {
        System.out.println("YAY");
        want_next_level = true;
    }
    public void take_damage(int amount) {
        health -= amount;
    }
    public void seton_ground(boolean state) {this.on_ground = state;}
    public boolean getWant_next_level() {
        return want_next_level;
    }
    public void loading_level(Vector2D startPos) {
        want_next_level = false;
        hitbox = PhysicsWorld.getInstance().add(hitbox); //Re add
        sprite = RenderEngine.getInstance().add(sprite); //Re add
        hitbox.force = new Vector2D();
        hitbox.velocity = new Vector2D();
        hitbox.pos = startPos;
        sprite.setPosition(startPos);
    }
    public void restart(Vector2D startPos) {
        need_restart = false;
        System.out.println("Restarted");
        hitbox.pos = startPos;
        hitbox.force = new Vector2D();
        hitbox.velocity = new Vector2D();
        sprite.setPosition(startPos);
    }
    public boolean getWantRestart() {
        return need_restart;
    }
    /**
     * Make sure to pass the one and only keyboard instance just to make sure you are paying attention
     * @param k
     */
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
        if (k.justPressed(KeyEvent.VK_R)) {
            this.need_restart = true;
        }
    }

    /**
     * Initially made to update sprite position after physics lol
     * does some other useful things now i guess
     */
    public void update() {
        sprite.setPosition(hitbox.pos);
        if (this.health <= 0) {
            need_restart = true;
            this.health = 1;
        }
    }
}
