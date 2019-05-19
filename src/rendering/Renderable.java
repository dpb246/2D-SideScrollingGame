package rendering;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;

public class Renderable extends JComponent {
    protected Image image;
    protected double x, y, w, h, angle, scale; //All coordinates world level
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
    /**
     *
     * @param g graphics surface to draw to
     * @param io image observer
     * @param shift_x x transform to turn the global coordinates into camera
     * @param shift_y y transform to turn the global coordinates into camera
     * @param camera_scale how zoomed in/out to draw the sprite
     */
    public void draw(Graphics2D g, ImageObserver io, double shift_x, double shift_y, double camera_scale) {
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
