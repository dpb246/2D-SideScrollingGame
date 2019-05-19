package rendering;

public class Camera {
    private double x, y, zoom;
    public Camera(double x, double y, double zoom) {
        this.x = x;
        this.y = y;
        this.zoom = zoom;
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
        return this.x;
    }
    public Camera setX(double x) {
        this.x = x;
        return this;
    }
    public double getY() {
        return this.y;
    }
    public Camera setY(double y) {
        this.y = y;
        return this;
    }
    public Camera changeX(double xc) {
        this.x += xc;
        return this;
    }
    public Camera changeY(double yc) {
        this.y += yc;
        return this;
    }
}
