package physics;

public class Force {

    private final double value;
    private final double velocityAtImpact;
    private final boolean isVertical;
    private final boolean isConstant;
    private int time = 0; // measured in frames

    Force(double value, double velocity, boolean isVertical, boolean isConstant){
        this.value = value;
        this.velocityAtImpact = velocity;
        this.isVertical = isVertical;
        this.isConstant = isConstant;
    }

    public double getValue(){
        return value;
    }

    public boolean isVertical(){
        return isVertical;
    }

    public boolean isHorizontal(){
        return !isVertical;
    }

}
