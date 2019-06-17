package physics;

import com.sun.deploy.config.VerboseDefaultConfig;
import main.Ults;
import main.Vector2D;

import java.util.ArrayList;

/**
 * Does Physics things
 * Friction is sadly not negligible here
 */
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

    /**
     * I mean they do what they say they do
     */
    public AABB add(AABB a) {
        objects.add(a);
        return a;
    }
    public void remove(AABB a) {
        objects.remove(a);
    }
    public void removeAll() {
        objects.clear();
    }

    /**
     * Call each frame to update all objects in the world
     * @param dt
     */
    public void step(double dt) {
        //apply gravity, forces, position to everything
        for (AABB a : objects) {
            if (a.mass != 0.0) { //Massless things shouldn't be moving
                a.force.add(a.gravity.scaled(a.mass));
                a.velocity.add(a.force.scaled(dt/a.mass));
                a.force = new Vector2D();

                //clamp velocity
                if (Math.abs(a.velocity.x) > a.max_velocity.x) {
                    a.velocity.x = Math.copySign(a.max_velocity.x, a.velocity.x);
                }
                if (Math.abs(a.velocity.y) > a.max_velocity.y) {
                    a.velocity.y = Math.copySign(a.max_velocity.y, a.velocity.y);
                }
                a.pos.add(a.velocity.scaled(dt));
            }
        }
        //List of collisions
        for (int i = 0; i < objects.size(); i++) {
            for (int j = i+1; j < objects.size(); j++) {
                AABB a = objects.get(i);
                AABB b = objects.get(j);
                if ((a.bitmask & b.bitmask) == 0 && (a.mass != 0.0 || b.mass != 0.0)) { //Dont share group therefore check collision
                    if (AABBvsAABB(a, b)) { //Check for collision
                        //if true then do work of calculating penetration depth
                        Vector2D displacement = distanceAABBvsAABB(a, b);
                        Vector2D rv = b.velocity.copy().subtract(a.velocity);

                        //Sketchy way to do normal but since everything is a AABB it works
                        Vector2D normal = b.pos.copy().subtract(a.pos);
                        if (displacement.x == 0.0) {
                            normal.x = 0;
                            normal.y /= Math.abs(normal.y);
                        } else if (displacement.y == 0.0) {
                            normal.x /= Math.abs(normal.x);
                            normal.y = 0;
                        }
                        //Does actual cancelling of gravity and stuff
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
                        //Position correction to prevent drifting downwards into other objects
                        double percent = 0.8;  //Arbitrary number LETS GO
                        Vector2D correction = normal.scale(displacement.getMag() / (a.inv_mass + b.inv_mass) * percent);
                        a.pos.subtract(correction.scaled(a.inv_mass));
                        b.pos.add(correction.scaled(b.inv_mass));

//          This is an attempt at friction introduced wall clips though so
//                        Vector2D tangent = rv.copy().subtract(displacement.scaled(displacement.dot(rv)));
//                        tangent.normalize();
//                        double jt = -tangent.dot(rv);
//                        jt = jt / (a.inv_mass + b.inv_mass);
//
//                        double mu = Ults.hypot(a.staticFriction, b.staticFriction);
//                        Vector2D frictionImpulse;
//                        if (Math.abs(jt) < k * mu) {
//                            frictionImpulse = tangent.scaled(jt);
//                        } else {
//                            frictionImpulse = tangent.scaled(-k*Ults.hypot(a.dynamicFriction, b.dynamicFriction));
//                        }
//                        a.velocity.subtract(frictionImpulse.scaled(a.inv_mass));
//                        b.velocity.add(frictionImpulse.scaled(b.inv_mass));

                        //Call on hit functions for the objects
                        if (a.callbacks != null) {
                            a.callbacks.on_hit(b, normal);
                        }
                        if (b.callbacks != null) {
                            b.callbacks.on_hit(a, normal);
                        }
                    }
                }
            }
        }
    }

    /**
     * Checks if two AABB are colliding
     * @param a
     * @param b
     * @return
     */
    private boolean AABBvsAABB(AABB a, AABB b) {
        // Exit with no intersection if found separated along an axis
        if(a.pos.x+a.width/2 < b.pos.x-b.width/2 || a.pos.x-a.width/2 > b.pos.x+b.width/2) return false;
        if(a.pos.y+a.height/2 < b.pos.y-b.height/2 || a.pos.y-a.height/2 > b.pos.y+b.height/2) return false;
        // No separating axis found, therefor there is at least one overlapping axis
        return true;
    }

    /**
     * I LOVE IF STATEMENTS
     * @param a
     * @param b
     * @return
     */
    private Vector2D distanceAABBvsAABB(AABB a, AABB b) {
        //Assume collision
        double disx = 0;
        double lengthx = Math.abs(b.pos.x - a.pos.x);
        double gapx = lengthx - a.width*0.5  - b.width*0.5;
        if (gapx <= 0.0) {
            disx = -gapx;
        }
        double disy = 0;
        double lengthy = Math.abs(b.pos.y - a.pos.y);
        double gapy = lengthy - a.height*0.5  - b.height*0.5;
        if (gapy <= 0.0) {
            disy = -gapy;
        }
        if (disx > disy) {
            return new Vector2D(0, disy);
        } else if (disy > disx) {
            return new Vector2D(disx, 0);
        } else {
            return new Vector2D();
        }
    }
}
