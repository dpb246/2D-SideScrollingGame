package physics;

public class Circle extends Shape {
    private double radius;
    public Circle(double x, double y, double radius, double mass, boolean isStatic){
        super(x, y, mass, isStatic, -1.0, -1.0);
        this.radius = radius;
    }
    public Circle(double x, double y, double radius, double mass, boolean isStatic, double maxVelocityX, double maxVelocityY){
        super(x, y, mass, isStatic, maxVelocityX, maxVelocityY);
        this.radius = radius;
    }
    public double getRadius(){
        return this.radius;
    }
    public double getMaxX(){
        return getPosition().getX() + radius;
    }
    public double getMinX(){
        return getPosition().getX() - radius;
    }
    public double getMaxY(){
        return getPosition().getY() + radius;
    }
    public double getMinY(){
        return getPosition().getY() - radius;
    }
}
