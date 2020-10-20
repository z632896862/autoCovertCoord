package param;

import java.util.List;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2020/10/20 15:49
 */
public class PipeLineParam {
    private static final long serialVersionUID = - 1L;

    List<Step> transforms;
    private boolean inverse;

    public PipeLineParam(){
        inverse=false;
    }
    public boolean isInverse() {
        return inverse;
    }

    public void setInverse(boolean inverse) {
        this.inverse = inverse;
    }

    public List<Step> getTransforms() {
        return transforms;
    }

    public void setTransforms(List<Step> transforms) {
        this.transforms = transforms;
    }
}
