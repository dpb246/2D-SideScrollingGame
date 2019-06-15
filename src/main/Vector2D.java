package main;

/**
 * For some reason we made a custom vector class
 */
public class Vector2D {
    public double x; //Screw private variables
    public double y;

    /**
     * WOW OVERLOADED
     * @param x
     * @param y
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Vector2D() {
        this(0, 0);
    }

    /**
     * I hope you can read
     * @return
     */
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

    /**
     * Does dot product
     * @param other
     * @return
     */
    public double dot(Vector2D other) {
        return this.x*other.x + this.y*other.y;
    }

    /**
     * Adds the other one on to this vector, why no operator overloading java....
     * @param other
     * @return
     */
    public Vector2D add(Vector2D other) {
        this.x += other.x;
        this.y += other.y;
        return this;
    }

    /**
     * Returns a new vector that is the summation of the two
     * @param other
     * @return
     */
    public Vector2D addNew(Vector2D other) {
        return new Vector2D(this.x+other.x, this.y+other.y);
    }

    /**
     * Subtracts the other one on from this vector, why no operator overloading java....
     * @param other
     * @return
     */
    public Vector2D subtract(Vector2D other) {
        this.x -= other.x;
        this.y -= other.y;
        return this;
    }
    /**
     * Returns magnitude, are you really stilling reading these
     * @return
     */
    public double getMag() {
        return Math.sqrt(this.x*this.x + this.y*this.y);
    }

    /**
     * Returns a new vector that is the unit vector of the current
     * @return
     */
    public Vector2D unit() {
        double mag = getMag();
        return new Vector2D(this.x/mag, this.y/mag);
    }

    /**
     * Makes this vector into a unit vector
     * @return
     */
    public Vector2D normalize() {
        double mag = getMag();
        this.x /= mag;
        this.y /= mag;
        return this;
    }

    /**
     * Scales this current vector
     * @param scale
     * @return
     */
    public Vector2D scale(double scale) {
        this.x *= scale;
        this.y *= scale;
        return this;
    }

    /**
     * Returns a scaled copy
     * @param scale
     * @return
     */
    public Vector2D scaled(double scale) {
        return new Vector2D(this.x*scale, this.y*scale);
    }

    /**
     * I mean take a guess... it copies this vector into a new one
     * @return
     */
    public Vector2D copy() {
        return new Vector2D(this.x, this.y);
    }

    /**
     * Prints it with a fancy title
     * @param title
     * @return
     */
    public Vector2D print(String title){
        String s = title;
        if (title.length() > 0){s+=": ";}
        s += "X: " + Double.toString(Ults.round(this.x, 3));
        s += " || Y: " + Double.toString(Ults.round(this.y, 3));
        System.out.println(s);
        return this;
    }

    /**
     * cause passing empty strings is annoying
     * @return
     */
    public Vector2D print(){
        return print("");
    }
}