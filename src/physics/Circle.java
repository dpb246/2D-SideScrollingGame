package physics;

public class Circle extends Shape {
    private double radius;
    Circle(double x, double y, double radius, double mass){
        super(x, y, mass);
        this.radius = radius;
    }
    public double getRadius(){
        return this.radius;
    }
}
