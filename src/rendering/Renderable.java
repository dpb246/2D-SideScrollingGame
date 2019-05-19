package rendering;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;

public class Renderable {
    protected Image image;
    protected double x;
    protected double y;
    protected double w;
    protected double h;
    protected double angle;
    protected double scale; //All coordinates world level
    protected boolean visible = true, delete = false;
    private Renderable(double x, double y, double w, double h, double scale, double angle) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
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
    public double getX() {
        return x;
    }

    public Renderable setX(double x) {
        this.x = x;
        return this;
    }

    public double getY() {
        return y;
    }

    public Renderable setY(double y) {
        this.y = y;
        return this;
    }

    public double getW() {
        return w;
    }

    public Renderable setW(double w) {
        this.w = w;
        return this;
    }

    public double getH() {
        return h;
    }

    public Renderable setH(double h) {
        this.h = h;
        return this;
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
     * @param shift_x x transform to turn the global coordinates into camera
     * @param shift_y y transform to turn the global coordinates into camera
     * @param camera_scale how zoomed in/out to draw the sprite
     */
    public void draw(Graphics2D g, ImageObserver io, double shift_x, double shift_y, double camera_scale) {
        if (!visible) return;
        AffineTransform at = new AffineTransform();
        at.translate((this.x-shift_x-w/2*scale)*camera_scale, (this.y-shift_y-h/2*scale)*camera_scale); //Translates to center coordinates
        at.rotate(angle, w/2*scale*camera_scale, h/2*scale*camera_scale); //Rotates angle radians around center
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
