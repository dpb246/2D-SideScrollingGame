package physics;
import java.util.ArrayList;
class vector {
    public double x;
    public double y;
}
public abstract class PhysicsEngine {

    private final double mass;
    private final double GRAVITY;

    public vector postition = new vector();
    protected int x, y; // centre of object

    protected double velocityX = 0.0f; // units per frame
    protected double velocityY = 0.0f;

    private double MAX_VELOCITY_X, MAX_VELOCITY_Y;

    private ArrayList<Force> forces = new ArrayList<>();

    PhysicsEngine(double mass, double gravity, int start_x, int start_y){
        this.mass = mass;
        this.GRAVITY = gravity;
        this.x = start_x;
        this.y = start_y;
    }

    public PhysicsEngine setPosition(int x, int y){
        this.x = x;
        this.y = y;
        return this;
    }

    public PhysicsEngine setVelocity(double x, double y){
        this.velocityX = x;
        this.velocityY = y;
        return this;
    }

    private void run(){
        updatePos(); // move

        if (filter()) {
            checkCollisions(); // check intersections
        }

        updatePos(); // adjust

        draw(); // display
    }

    public void addForce(Force force){
        forces.add(force);
    }

    private void updatePos(){

        Force gravity = new Force(mass * GRAVITY, velocityY, true, true);
        forces.add(gravity);

        for (Force force : forces){

            double a = force.getValue() / mass; // F = ma
            double t = 1 / 60; // time since last frame (1 frame)
            double v = a * t;

            if (force.isVertical()){
                velocityY += v;
            } else {
                velocityX += v;
            }

        }

        forces = new ArrayList<>(); // clear instantaneous forces

        x += velocityX;
        y += velocityY;

    }

    protected abstract void checkCollisions();

    private boolean filter(){
        return true;
    }

    private void draw(){

    }

}
