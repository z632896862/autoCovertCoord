package param;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2020/10/20 15:46
 */
@JsonTypeInfo(use =JsonTypeInfo.Id.NAME,include = JsonTypeInfo.As.EXISTING_PROPERTY,property = "type",visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SevenParam.class,name="7"),
        @JsonSubTypes.Type(value = ThreeParam.class,name ="3"),
        @JsonSubTypes.Type(value = FourParam.class,name ="4"),
        @JsonSubTypes.Type(value = OffsetParam.class,name = "offset"),
        @JsonSubTypes.Type(value = ProjectionParam.class,name = "wkt"),
})
public class TransformObject implements Serializable {

    private static final long serialVersionUID = - 1L;

    private String type;
    private boolean inverse=false;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isInverse() {
        return inverse;
    }

    public void setInverse(boolean inverse) {
        this.inverse = inverse;
    }
}