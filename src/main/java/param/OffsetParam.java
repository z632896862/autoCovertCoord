package param;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2020/10/20 15:48
 */
public class OffsetParam extends TransformObject{
    public OffsetParam() {
        this.setType("offset");
    }
    private double dx;
    private double dy;

    public double getDx() {
        return dx;
    }

    public OffsetParam setDx(double dx) {
        this.dx = dx;
        return this;
    }

    public double getDy() {
        return dy;
    }

    public OffsetParam setDy(double dy) {
        this.dy = dy;
        return this;

    }
}
