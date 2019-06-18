package physics;

import main.Vector2D;

/**
 * I hate getters and setters change whatever you want
 * stores data for an AABB
 */
public class AABB {
    public Vector2D pos;
    public double width, height;
    public int bitmask = 0;
    public Vector2D gravity = new Vector2D(0, -9.8);
    public double mass = 0.0;
    public double inv_mass = 0.0;
    public Vector2D force;
    public Vector2D velocity;
    public double restitution = 0.0;
    public String type = "";
    public Vector2D max_velocity = new Vector2D(300, 1000);
    public callback callbacks = null;
    public AABB() {
        this(new Vector2D(), 0, 0, 0.0);
    }
    public AABB(Vector2D pos, double width, double height, double mass) {
        this.pos = pos;
        this.width = width;
        this.height = height;
        this.mass = mass;
        if (mass != 0.0) {
            this.inv_mass = 1 / mass;
        }
        this.force = new Vector2D();
        this.velocity = new Vector2D();
    }

}
