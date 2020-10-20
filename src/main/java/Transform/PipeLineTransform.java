package Transform;

import param.*;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2020/10/20 16:20
 */
public class PipeLineTransform {

    public static LngLatLike Transform(LngLatLike src, PipeLineParam param){
        try{
            if(param.isInverse()){
                return  _inverseTransform(src,param);
            }else{
                return  _transform(src,param);
            }
        }
        catch (Exception ex)
        {return src;}

    }


    /**
     * 坐标流水变换
     *
     * @param src 坐标对
     * @param param 坐标转换参数
     * @return 转换后坐标对
     */
    private static LngLatLike _transform(LngLatLike src, PipeLineParam param) {

        for (Step transformStep : param.getTransforms()) {
            final TransformObject transform = transformStep.getTransform();
            switch (transform.getType()) {
                case "4": {
                    final FourParam t = (FourParam) transform;
                    if (!transform.isInverse()) {
                        src = FourParamTransform.Transform(src, t);
                    } else {
                        src = FourParamTransform.InverseTransform(src, t);
                    }
                    break;
                }
                case "7": {
                    final SevenParam t = (SevenParam) transform;
                    if (!transform.isInverse()) {
                        src = SevenParamTransform.Transform(src, t);
                    } else {
                        src = SevenParamTransform.InverseTransform(src, t);
                    }
                    break;
                }
                case "3": {
                    final ThreeParam t = (ThreeParam) transform;
                    if (!transform.isInverse()) {
                        src = ThreeParamTransform.Transform(src, t);
                    } else {
                        src = ThreeParamTransform.InverseTransform(src, t);
                    }
                    break;
                }
                case "wkt": {
                    final ProjectionParam t = (ProjectionParam) transform;
                    if (!transform.isInverse()) {
                        src = ProjectionTransform.Transform(src, t);
                    } else {
                        src = ProjectionTransform.InverseTransform(src, t);
                    }
                    break;
                }
                case "offset": {
                    final OffsetParam t = (OffsetParam) transform;
                    if (!transform.isInverse()) {
                        src = OffsetTransform.Transform(src, t);
                    } else {
                        src = OffsetTransform.InverseTransform(src, t);
                    }
                    break;
                }
                default:
            }
        }
        return src;
    }

    /**
     * 坐标流水反变换
     *
     * @param src 坐标对
     * @param param 坐标转换参数
     * @return 转换后坐标对
     */
    public static LngLatLike _inverseTransform(LngLatLike src, PipeLineParam param) {
        if (param.getTransforms() != null && param.getTransforms().size() > 0) {
            for (int i = param.getTransforms().size() - 1; i >= 0; i--) {
                final Step transformStep = param.getTransforms().get(i);
                final TransformObject transform = transformStep.getTransform();
                switch (transform.getType()) {
                    case "4": {
                        final FourParam t = (FourParam) transform;
                        if (transform.isInverse()) {
                            src = FourParamTransform.Transform(src, t);
                        } else {
                            src = FourParamTransform.InverseTransform(src, t);
                        }
                        break;
                    }
                    case "7": {
                        final SevenParam t = (SevenParam) transform;
                        if (transform.isInverse()) {
                            src = SevenParamTransform.Transform(src, t);
                        } else {
                            src = SevenParamTransform.InverseTransform(src, t);
                        }
                        break;
                    }
                    case "3": {
                        final ThreeParam t = (ThreeParam) transform;
                        if (transform.isInverse()) {
                            src = ThreeParamTransform.Transform(src, t);
                        } else {
                            src = ThreeParamTransform.InverseTransform(src, t);
                        }
                        break;
                    }
                    case "wkt": {
                        final ProjectionParam t = (ProjectionParam) transform;
                        if (transform.isInverse()) {
                            src = ProjectionTransform.Transform(src, t);
                        } else {
                            src = ProjectionTransform.InverseTransform(src, t);
                        }
                        break;
                    }
                    case "offset": {
                        final OffsetParam t = (OffsetParam) transform;
                        if (transform.isInverse()) {
                            src = OffsetTransform.Transform(src, t);
                        } else {
                            src = OffsetTransform.InverseTransform(src, t);
                        }
                        break;
                    }
                    default:
                }
            }
            return src;
        }
        return new LngLatLike(0,0,0);
    }
}
