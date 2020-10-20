package param;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2020/10/20 15:51
 */
public class ProjectionParam extends  TransformObject{
    private String code ="";

    private String projWkt="";

    public ProjectionParam() {
        this.setType("wkt");
    }

    public String getCode() {
        return code;
    }

    public ProjectionParam setCode(String code) {
        this.code = code;
        return this;
    }

    public String getProjWkt() {
        return projWkt;
    }

    public ProjectionParam setProjWkt(String projWkt) {
        this.projWkt = projWkt;
        return this;
    }
}
