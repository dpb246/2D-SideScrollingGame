package physics;

public class Box extends Shape {
    private double height;
    private double width;
    Box(double x, double y, double height, double width, double mass){
        super(x, y, mass);
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
