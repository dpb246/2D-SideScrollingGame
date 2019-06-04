package physics;
import java.util.ArrayList;
import main.Vector2D;
public class PhysicsEngine {

    private Vector2D gravity;
    private ArrayList<Shape> shapes = new ArrayList<>();

    public PhysicsEngine(Vector2D gravity){
        this.gravity = gravity.copy();
    }

    public PhysicsEngine setGravity(Vector2D gravity){
        this.gravity = gravity.copy();
        return this;
    }

    public PhysicsEngine add(Shape shape){
        this.shapes.add(shape);
        return this;
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

        shape.addForce(gravity.scaled(shape.getMass()));
        double t = 1.0 / 60.0; // time since last computation (1 frame)

        for (Vector2D force : shape.getForces()){

            Vector2D acceleration = force.scaled(1.0 / shape.getMass()); // F = ma
            shape.getVelocity().add(acceleration.scaled(t)).print(); // v = at + v

            // Limit velocity
            Vector2D maxVelocity = shape.getTerminalVelocity();
            if (maxVelocity.getX() > 0 && shape.getVelocity().getMag() > maxVelocity.getMag()){
                shape.setVelocity( shape.getVelocity().unit().scaled( maxVelocity.getMag()) );
            }

        }

        shape.clearForces(); // clear instantaneous forces
        shape.getPosition().add(shape.getVelocity().scaled(t)); // update position

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
