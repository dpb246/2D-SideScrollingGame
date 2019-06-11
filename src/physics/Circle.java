package physics;

import main.Vector2D;

public class Circle {
    Vector2D pos;
    double radius;
    public Circle() {
        this(new Vector2D(), 0);
    }
    public Circle(Vector2D pos, double rad) {
        this.pos = pos;
        this.radius = rad;
    }
}