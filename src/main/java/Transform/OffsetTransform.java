package Transform;

import param.LngLatLike;
import param.OffsetParam;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2020/10/20 16:16
 */
public class OffsetTransform {
    /**
     * 平面坐标平移
     * @param src 坐标对
     * @param param 平移参数
     * @return 转换后坐标对
     */
    public static LngLatLike InverseTransform(LngLatLike src, OffsetParam param) {
        final LngLatLike lngLatLike = new LngLatLike();
        lngLatLike.add(src.getX()-param.getDx());
        lngLatLike.add(src.getY()-param.getDy());
        return lngLatLike;
    }

    public static LngLatLike Transform(LngLatLike src, OffsetParam param){
        final LngLatLike lngLatLike = new LngLatLike();
        lngLatLike.add(src.getX()+param.getDx());
        lngLatLike.add(src.getY()+param.getDy());
        return lngLatLike;
    }
}
