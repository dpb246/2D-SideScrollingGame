package rendering;

import main.Player;
import main.Vector2D;

/**
 * Provides translation/scaling for all objects in RenderEngine rather useful if i may say so myself
 * is just setters and getters though ugh
 */
public class Camera {
    private double zoom;
    private Vector2D position;
    private Player p = null;
    public Camera(double x, double y, double zoom) {
        position = new Vector2D(x, y);
        this.zoom = zoom;
    }
    public Camera reset() {
        this.zoom = 1.0;
        this.position.setX(0).setY(0);
        return this;
    }
    public double getZoom() {
        return this.zoom;
    }
    public Camera setZoom(double zoom) {
        this.zoom = zoom;
        return this;
    }
    public Camera changeZoom(double zoomc) {
        this.zoom += zoomc;
        return this;
    }
    public double getX() {
        return position.getX();
    }
    public Camera setX(double x) {
        this.position.setX(x);
        return this;
    }
    public double getY() {
        return this.position.getY();
    }
    public Camera setY(double y) {
        this.position.setY(y);
        return this;
    }
    public Camera changeX(double xc) {
        this.position.setX(this.position.getX()+xc);
        return this;
    }
    public Camera changeY(double yc) {
        this.position.setY(this.position.getY()+yc);
        return this;
    }
    public Camera setPos(Vector2D pos) {
        this.position = pos;
        return this;
    }
    public Vector2D getPos() {
        return this.position;
    }
    public void trackPlayer(Player p) {
        this.p = p;
    }
    public void update() {
        if (p != null) {
            if (p.getPos().x - position.x > 450) {
                position.x += p.getPos().x - position.x - 450;
            }
        }
    }
}
