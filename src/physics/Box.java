package physics;

public class Box extends Shape {
    private double height;
    private double width;
    public Box(double x, double y, double height, double width, boolean isStatic){
        super(x, y, 1.0, isStatic, -1.0, -1.0);
        this.height = height;
        this.width = width;
    }
    public Box(double x, double y, double height, double width, boolean isStatic, double mass, double maxVelocityX, double maxVelocityY){
        super(x, y, mass, isStatic, maxVelocityX, maxVelocityY);
        this.height = height;
        this.width = width;
    }
    public double getHeight(){
        return this.height;
    }
    public double getWidth(){
        return this.width;
    }
}
