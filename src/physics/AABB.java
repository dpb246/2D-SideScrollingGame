package physics;

import main.Vector2D;

public class AABB {
    public Vector2D min, max;
    public AABB() {
        this(new Vector2D(), new Vector2D());
    }
    public AABB(Vector2D min, Vector2D max) {
        this.min = min;
        this.max = max;
    }
}
