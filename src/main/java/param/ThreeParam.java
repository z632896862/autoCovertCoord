package param;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2020/10/20 15:51
 */
public class ThreeParam extends TransformObject{
    public ThreeParam() {
        this.setType("3");
    }
    private String startEllipsoid;

    public String getStartEllipsoid() {
        return startEllipsoid;
    }

    public ThreeParam setStartEllipsoid(String startEllipsoid) {
        this.startEllipsoid = startEllipsoid;
        return this;
    }

    public String getEndEllipsoid() {
        return endEllipsoid;
    }

    public ThreeParam setEndEllipsoid(String endEllipsoid) {
        this.endEllipsoid = endEllipsoid;
        return this;
    }

    private String endEllipsoid;
    private double dx;
    private double dy;
    private double dz;


    public double getDx() {
        return dx;
    }

    public ThreeParam setDx(double dx) {
        this.dx = dx;
        return this;
    }

    public double getDy() {
        return dy;
    }

    public ThreeParam setDy(double dy) {
        this.dy = dy;
        return this;
    }

    public double getDz() {
        return dz;
    }

    public ThreeParam setDz(double dz) {
        this.dz = dz;
        return this;
    }
}
