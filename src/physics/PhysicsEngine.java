package physics;
import java.util.ArrayList;
import main.Vector2D;
public class PhysicsEngine {

    private final double GRAVITY;

    public PhysicsEngine(double gravity){
        this.GRAVITY = gravity;
    }

    private ArrayList<Shape> shapes = new ArrayList<>();

    public Shape add(Shape shape){
        this.shapes.add(shape);
        return shape;
    }

    public void simulate(){

        for (Shape shape : shapes){

            if (!shape.isStatic()) {

                updatePos(shape); // move

                if (filter()) {
                    checkCollisions(shape); // check intersections
                }

                //updatePos(shape); // adjust

                draw(); // display

            }

        }

    }

    private void updatePos(Shape shape){

        Vector2D gravity = new Vector2D(0, shape.getMass() * GRAVITY);
        shape.addForce(gravity);
        double t = 1.0 / 60.0; // time since last computation (1 frame)

        for (Vector2D force : shape.getForces()){
            Vector2D acceleration = force.scaled(1.0 / shape.getMass()); // F = ma
            //Vector2D velocity = acceleration.scale(t).addNew(shape.getVelocity()); // v = at + v
            shape.getVelocity().add(acceleration.scaled(t));
            // add onto the velocity

            // Cap velocity
//            Vector2D maxVelocity = shape.getTerminalVelocity();
//            if (maxVelocity.getX() > 0 && Math.abs(velocity.getX()) > maxVelocity.getX()){
//                velocity.setX( maxVelocity.getX() );
//            }
//            if (maxVelocity.getY() > 0 && Math.abs(velocity.getY()) > maxVelocity.getY()){
//                velocity.setY( maxVelocity.getY() );
//            }
            //shape.setVelocity(velocity);
        }
        shape.clearForces(); // clear instantaneous forces
        // update position
        //shape.setPosition(shape.getVelocity().scale(t).addNew(shape.getPosition())).getVelocity().print("Speed  ");
        shape.getPosition().add(shape.getVelocity().scaled(t));
    }

    private void checkCollisions(Shape shape){
        if (shape instanceof Box){

        } else if (shape instanceof Circle){

        }
    }

    private boolean boxesIntersect(Box a, Box b){
        return true;
    }

    private boolean circlesIntersect(Circle a, Circle b){
        return true;
    }

    private boolean boxCircleIntersect(Box b, Circle c){
        return true;
    }

    private boolean filter(){
        return false;
    }


    private void draw(){
    }

}
