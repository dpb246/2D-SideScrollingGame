package rendering;
import java.util.concurrent.CopyOnWriteArrayList;
class LevelPointers {
    public Level_Tile N, S, E, W, NE, NW, SE, SW;
}
public class Level_Tile {
    private CopyOnWriteArrayList<Renderable> objs; //ya ya its slow
    private double x, y, w, h;
    private LevelPointers adjacent;
    public Level_Tile(double x, double y, double w, double h) {
        objs = new CopyOnWriteArrayList<>();
        this.x = x;
        this.y = y;
        adjacent = new LevelPointers();
    }
    public Renderable add(Renderable obj) {
        objs.add(obj);
        return obj;  //Return obj passed to make nicer usage
    }
    public CopyOnWriteArrayList getAll() {
        return objs;
    }
}
