package physics;
import main.Vector2D;
public class Shape {
    private Vector2D position;
    private Vector2D velocity;
    private double mass;
    Shape(double x, double y, double mass){
        position = new Vector2D(x, y);
        this.mass = mass;
    }
    public Vector2D getPosition(){
        return this.position;
    }
    public Shape setPosition(double x, double y){
        position.setX(x);
        position.setY(y);
        return this;
    }
    public Vector2D getVelocity(){
        return this.velocity;
    }
    public Shape setVelocity(double x, double y){
        velocity.setX(x);
        velocity.setY(y);
        return this;
    }
    public double getMass(){
        return this.mass;
    }
}
