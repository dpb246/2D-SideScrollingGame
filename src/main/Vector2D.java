package main;

public class Vector2D {
    private double x;
    private double y;
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public double getX() {
        return this.x;
    }
    public Vector2D setX(double x) {
        this.x = x;
        return this;
    }
    public double getY() {
        return this.y;
    }
    public Vector2D setY(double y) {
        this.y = y;
        return this;
    }
    public double dot(Vector2D other) {
        return this.x*other.x + this.y*other.y;
    }
    public Vector2D add(Vector2D other) {
        this.x += other.x;
        this.y += other.y;
        return this;
    }
    public Vector2D subtract(Vector2D other) {
        this.x -= other.x;
        this.y -= other.y;
        return this;
    }
    public double getMag() {
        return this.x*this.x + this.y*this.y;
    }
    public Vector2D unit() {
        double mag = getMag();
        return new Vector2D(this.x/mag, this.y/mag);
    }
    public Vector2D scale(double scale) {
        this.x *= scale;
        this.y *= scale;
        return this;
    }
    public Vector2D scaled(double scale) {
        return new Vector2D(this.x*scale, this.y*scale);
    }
    public Vector2D copy() {
        return new Vector2D(this.x, this.y);
    }
    public Vector2D print(String title){
        String s = title;
        if (title.length() > 0){s+=": ";}
        s += "X: " + Double.toString(Ults.round(this.x, 3));
        s += " || Y: " + Double.toString(Ults.round(this.y, 3));
        System.out.println(s);
        return this;
    }
    public Vector2D print(){
        return print("");
    }
}