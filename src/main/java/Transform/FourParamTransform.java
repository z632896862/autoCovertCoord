package Transform;

import param.FourParam;
import param.LngLatLike;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2020/10/20 16:15
 */
public class FourParamTransform {
    /** x,y 仪器中 在CoordGM中东方向为Y北方向为X
     * 在 平面坐标系中，东方向为X,北方向为Y (问李单飞)
     * 四参数变换
     * @param src 坐标对
     * @param param 四参数
     * @return 转换后坐标对
     */
    public static LngLatLike Transform(LngLatLike src, FourParam param){
        final LngLatLike target = new LngLatLike();
        final LngLatLike latLike = new LngLatLike();
//        x,y 仪器中 在CoordGM中东方向为Y北方向为X
//       在 平面坐标系中，东方向为X,北方向为Y (问李单飞)
//       四餐数变换
        latLike.add(src.getY());
        latLike.add(src.getX());
        final double x = param.getDx() + param.getM() * (Math.cos(param.getRot()) * latLike.getX() - Math.sin(param.getRot()) * latLike.getY());
        final double y = param.getDy() + param.getM() * (Math.cos(param.getRot()) * latLike.getY() + Math.sin(param.getRot()) * latLike.getX());

        target.add(y);target.add(x);

        return target;
    }

    /**
     * 四参数反变换
     * @param src 坐标对
     * @param param 四参数
     * @return 转换后坐标对
     */
    public static LngLatLike InverseTransform(LngLatLike src,FourParam param){
        final LngLatLike target = new LngLatLike();
        final LngLatLike latLike = new LngLatLike();
//        x,y 仪器中 在CoordGM中东方向为Y北方向为X
//       在 平面坐标系中，东方向为X,北方向为Y (问李单飞)
//       四餐数变换
        latLike.add(src.getY());
        latLike.add(src.getX());
        final double x = (Math.cos(param.getRot())*(latLike.getX()-param.getDx())+Math.sin(param.getRot())*(latLike.getY()-param.getDy()))/param.getM();
        final double y = (-Math.sin(param.getRot())*(latLike.getX()-param.getDx())+Math.cos(param.getRot())*(latLike.getY()-param.getDy()))/param.getM();
        target.add(y);target.add(x);
        return target;
    }
}
