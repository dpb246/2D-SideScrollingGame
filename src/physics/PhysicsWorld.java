package physics;

import com.sun.deploy.config.VerboseDefaultConfig;
import main.Vector2D;

import java.util.ArrayList;

public class PhysicsWorld {
    private ArrayList<AABB> objects;
    private static PhysicsWorld instance = null;
    private PhysicsWorld() {
        objects = new ArrayList<>();
    }
    public static PhysicsWorld getInstance() {
        if (instance == null) {
            instance = new PhysicsWorld();
        }
        return instance;
    }
    public AABB add(AABB a) {
        objects.add(a);
        return a;
    }
    public void step(double dt) {
        //apply gravity, forces, position
        for (AABB a : objects) {
            if (a.mass != 0.0) {
                a.force.add(a.gravity.scaled(a.mass));
                a.velocity.add(a.force.scaled(dt/a.mass));
                a.force = new Vector2D();
            }
            //clamp velocity
            if (Math.abs(a.velocity.x) > a.max_velocity.x) {
                a.velocity.x = Math.copySign(a.max_velocity.x, a.velocity.x);
            }
            if (Math.abs(a.velocity.y) > a.max_velocity.y) {
                a.velocity.y = Math.copySign(a.max_velocity.y, a.velocity.y);
            }
            a.pos.add(a.velocity.scaled(dt));
        }
        //List of collisions
        for (int i = 0; i < objects.size(); i++) {
            for (int j = i+1; j < objects.size(); j++) {
                AABB a = objects.get(i);
                AABB b = objects.get(j);
                if ((a.bitmask & b.bitmask) == 0 && (a.mass != 0.0 || b.mass != 0.0)) { //Dont share group therefore check collision
                    if (AABBvsAABB(a, b)) {
                        Vector2D displacement = distanceAABBvsAABB(a, b);
                        Vector2D rv = b.velocity.copy().subtract(a.velocity);
                        Vector2D normal = b.pos.copy().subtract(a.pos);
                        if (displacement.x == 0.0) {
                            normal.x = 0;
                            normal.y /= Math.abs(normal.y);
                        } else if (displacement.y == 0.0) {
                            normal.x /= Math.abs(normal.x);
                            normal.y = 0;
                        }
                        double velnormal = normal.dot(rv);
                        if (velnormal > 0) {
                            continue;
                        }
                        double e = Math.min(a.restitution, b.restitution);
                        double k = -(1+e)*velnormal;
                        k /= a.inv_mass + b.inv_mass;
                        Vector2D impluse = normal.scaled(k);
                        a.velocity.subtract(impluse.scaled(a.inv_mass));
                        b.velocity.add(impluse.scaled(b.inv_mass));

                        double percent = 0.8;
                        Vector2D correction = normal.scale(displacement.getMag() / (a.inv_mass + b.inv_mass) * percent);
                        a.pos.subtract(correction.scaled(a.inv_mass));
                        b.pos.add(correction.scaled(b.inv_mass));
                    }
                }
            }
        }
    }
    private boolean AABBvsAABB(AABB a, AABB b) {
        // Exit with no intersection if found separated along an axis
        if(a.pos.x+a.width/2 < b.pos.x-b.width/2 || a.pos.x-a.width/2 > b.pos.x+b.width/2) return false;
        if(a.pos.y+a.height/2 < b.pos.y-b.height/2 || a.pos.y-a.height/2 > b.pos.y+b.height/2) return false;
        // No separating axis found, therefor there is at least one overlapping axis
        return true;
    }
    private Vector2D distanceAABBvsAABB(AABB a, AABB b) {
        //Assume collision
        double disx = 0;
        double lengthx = Math.abs(b.pos.x - a.pos.x);
        double gapx = lengthx - a.width*0.5  - b.width*0.5;
        if (gapx <= -0.1) {
            disx = -gapx;
        }
        double disy = 0;
        double lengthy = Math.abs(b.pos.y - a.pos.y);
        double gapy = lengthy - a.height*0.5  - b.height*0.5;
        if (gapy <= 0.0) {
            disy = -gapy;
        } else if (gapy <= 0.05) {
            disy = gapy;
        }

        if (disy > disx) {
            return new Vector2D(disx, 0);
        } else if (disx > disy) {
            return new Vector2D(0, disy);
        }
        return new Vector2D();
    }
}
