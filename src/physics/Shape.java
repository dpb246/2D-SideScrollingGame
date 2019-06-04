package physics;
import main.Vector2D;

import java.util.ArrayList;

public class Shape {
    private Vector2D position;
    private Vector2D velocity;
    private Vector2D terminalVelocity;
    private ArrayList<Vector2D> forces = new ArrayList<>();
    private double mass;
    private boolean isStatic;
    Shape(double x, double y, double mass, boolean isStatic, double maxVelocityX, double maxVelocityY){
        this.position = new Vector2D(x, y);
        this.mass = mass;
        this.isStatic = isStatic;
        this.terminalVelocity = new Vector2D(maxVelocityX, maxVelocityY);
        velocity = new Vector2D(0.0, 0.0);
    }
    public Vector2D getPosition(){
        return this.position;
    }
    public Shape setPosition(Vector2D position){
        this.position.setX(position.getX());
        this.position.setY(position.getY());
        return this;
    }
    public Vector2D getVelocity(){
        return this.velocity;
    }
    public Shape setVelocity(Vector2D velocity){
        this.velocity.setX(velocity.getX());
        this.velocity.setY(velocity.getY());
        return this;
    }
    public double getMass(){
        return this.mass;
    }
    public Vector2D getTerminalVelocity(){
        return this.terminalVelocity;
    }
    public Shape setTerminalVelocity(Vector2D velocity){
        this.terminalVelocity.setX(velocity.getX());
        this.terminalVelocity.setY(velocity.getY());
        return this;
    }
    public boolean isStatic(){return this.isStatic;}
    public Shape addForce(Vector2D force){
        forces.add(force);
        return this;
    }
    public ArrayList<Vector2D> getForces(){
        return this.forces;
    }
    public Shape clearForces(){
        forces = new ArrayList<>();
        return this;
    }
}
