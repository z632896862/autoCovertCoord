package param;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2020/10/20 15:50
 */
public class SevenParam extends  TransformObject{
    public SevenParam() {
        this.setType("7");
    }
    private String startEllipsoid;
    private String endEllipsoid;
    private double dx;
    private double dy;
    private double dz;
    private double rotX;
    private double rotY;
    private double rotZ;
    private double m;

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public double getDz() {
        return dz;
    }

    public void setDz(double dz) {
        this.dz = dz;
    }

    public double getRotX() {
        return rotX;
    }

    public void setRotX(double rotX) {
        this.rotX = rotX;
    }

    public double getRotY() {
        return rotY;
    }

    public void setRotY(double rotY) {
        this.rotY = rotY;
    }

    public double getRotZ() {
        return rotZ;
    }

    public void setRotZ(double rotZ) {
        this.rotZ = rotZ;
    }

    public double getM() {
        return m;
    }

    public void setM(double m) {
        this.m = m;
    }

    public String getStartEllipsoid() {
        return startEllipsoid;
    }

    public void setStartEllipsoid(String startEllipsoid) {
        this.startEllipsoid = startEllipsoid;
    }

    public String getEndEllipsoid() {
        return endEllipsoid;
    }

    public void setEndEllipsoid(String endEllipsoid) {
        this.endEllipsoid = endEllipsoid;
    }
}
