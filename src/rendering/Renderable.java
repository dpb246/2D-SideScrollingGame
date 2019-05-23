package rendering;

import main.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
import java.util.Vector;

public class Renderable {
    protected Image image;
    protected Vector2D pos, size;
    protected double angle;
    protected double scale; //All coordinates world level
    protected boolean visible = true, delete = false;
    private Renderable(double x, double y, double w, double h, double scale, double angle) {
        this.pos = new Vector2D(x, y);
        this.size = new Vector2D(w, h);
        this.scale = scale;
        this.angle = angle;
    }
    public Renderable(double x, double y, double w, double h, double scale, double angle, String filepath) {
        this(x, y, w, h, scale, angle);
        loadImage(filepath);
    }
    public Renderable(double x, double y, double w, double h, double scale, double angle, Image im) {
        this(x, y, w, h, scale, angle);
        this.image = im;
    }
    public Renderable(double x, double y, double w, double h, String filepath) {
        this(x, y, w, h, 1, 0);
        loadImage(filepath);
    }
    public Renderable(double x, double y, double w, double h, Image im) {
        this(x, y, w, h, 1, 0);
        this.image = im;
    }
    public Renderable setPosition(Vector2D pos) {
        this.pos = pos;
        return this;
    }
    public double getX() {
        return this.pos.getX();
    }

    public Renderable setX(double x) {
        this.pos.setX(x);
        return this;
    }

    public double getY() {
        return this.pos.getY();
    }

    public Renderable setY(double y) {
        this.pos.setY(y);
        return this;
    }

    public double getW() {
        return this.size.getX();
    }

    public double getH() {
        return this.size.getY();
    }

    public double getAngle() {
        return angle;
    }

    public Renderable setAngle(double angle) {
        this.angle = angle;
        return this;
    }

    public double getScale() {
        return scale;
    }

    public Renderable setScale(double scale) {
        this.scale = scale;
        return this;
    }

    public boolean isVisible() {
        return visible;
    }

    public Renderable setVisible(boolean visible) {
        this.visible = visible;
        return this;
    }
    /**
     * Sets object to be deleted next draw cycle
     */
    public void delete() {
        this.delete = true;
    }
    public boolean shouldDelete() {
        return this.delete;
    }
    /**
     *
     * @param g graphics surface to draw to
     * @param io image observer
     * @param camera_shift transform to turn the global coordinates into camera
     * @param camera_scale how zoomed in/out to draw the sprite
     */
    public void draw(Graphics2D g, ImageObserver io, Vector2D camera_shift, double camera_scale) {
        if (!visible) return;
        AffineTransform at = new AffineTransform();
        Vector2D transformed = pos.copy().subtract(size.scaled(scale/2)).subtract(camera_shift).scaled(camera_scale);//Translates by center coordinates
        at.translate(transformed.getX(), transformed.getY());
        Vector2D forRotate = size.copy().scaled(scale*camera_scale/2);
        at.rotate(angle, forRotate.getX(), forRotate.getY()); //Rotates angle radians around center
        at.scale(scale*camera_scale, scale*camera_scale); //Scales image
        g.drawImage(this.image, at, io);
    }
    /**
     * Loads image from filepath
     * @param filepath string to image file
     */
    protected void loadImage(String filepath) {
        ImageIcon ii = new ImageIcon(filepath);
        this.image = ii.getImage();
    }
}
