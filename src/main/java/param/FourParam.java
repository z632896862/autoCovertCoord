package param;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2020/10/20 15:44
 */
public class FourParam extends TransformObject{

    public FourParam() {
        this.setType("4");
    }

    //x偏移(m)
    private double dx;
    //y偏移(m)
    private double dy;
    //旋转角度,单位radian
    private double rot;
    //比例缩放(比例因子)
    private double m;

    public double getDx() {
        return dx;
    }

    public FourParam setDx(double dx) {
        this.dx = dx;
        return this;
    }

    public double getDy() {
        return dy;
    }

    public FourParam setDy(double dy) {
        this.dy = dy;
        return this;
    }

    public double getRot() {
        return rot;
    }

    public FourParam setRot(double rot) {
        this.rot = rot;
        return this;
    }

    public double getM() {
        return m;
    }

    public FourParam setM(double m) {
        this.m = m;
        return this;
    }
}

