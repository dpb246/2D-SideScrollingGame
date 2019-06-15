package physics;

import main.Vector2D;

/**
 * Love this in its own file
 * all three lines
 * notice how this comment is longer than the code in this file
 * wacky isn't it
 * so nice of java to require public classes in their own files
 * screw this terrible language
 */
public interface callback {
    public void on_hit(AABB other, Vector2D normal);
}