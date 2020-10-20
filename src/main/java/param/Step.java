package param;

import java.io.Serializable;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2020/10/20 15:49
 */
public class Step implements Serializable {
    private static final long serialVersionUID = - 1L;

    public static class Builder {
        private TransformObject transform;



        public Builder(TransformObject transform){
            this.transform=transform;
        }

        public Step build(){
            final Step step = new Step();
            step.setTransform(this.transform);
            return step;
        }


    }
    private TransformObject transform;

    public TransformObject getTransform() {
        return transform;
    }

    public void setTransform(TransformObject transform) {
        this.transform = transform;
    }
}
