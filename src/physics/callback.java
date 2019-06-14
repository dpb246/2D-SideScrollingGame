package physics;

import main.Vector2D;

public interface callback {
    public void on_hit(AABB other, Vector2D normal);
}