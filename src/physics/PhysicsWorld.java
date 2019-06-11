package physics;

public class PhysicsWorld {


    private boolean AABBvsAABB(AABB a, AABB b) {
        // Exit with no intersection if found separated along an axis
        if(a.max.x < b.min.x || a.min.x > b.max.x) return false;
        if(a.max.y < b.min.y || a.min.y > b.max.y) return false;
        // No separating axis found, therefor there is at least one overlapping axis
        return true;
    }
    private boolean CirclevsCircle(Circle a, Circle b) {
        double radius = a.radius + b.radius;
        return radius * radius  < (a.pos.x + b.pos.x)*(a.pos.x + b.pos.x) + (a.pos.y + b.pos.y)*(a.pos.y + b.pos.y);
    }
}
