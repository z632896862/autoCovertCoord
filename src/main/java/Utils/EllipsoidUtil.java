package Utils;

import org.locationtech.proj4j.datum.Ellipsoid;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2020/10/20 16:13
 */
public class EllipsoidUtil {
    public static final double SECONDS_TO_RAD = 4.84813681109535993589914102357e-6;
    public static final double MILLION = 1000000.0;
    /**
     * 椭球体获取，用于转换经纬度到笛卡尔坐标系用于坐标七参数转换
     * @param name 椭球名
     * @return 椭球体
     */
    public static Ellipsoid getEllipsoid(String name){
        switch (name.toLowerCase()){
            case "krass": return  Ellipsoid.KRASSOVSKY;
            case "grs80": return Ellipsoid.GRS80;
            case "wgs84": return Ellipsoid.WGS84;
            case "iau76": return Ellipsoid.IAU76;
        }
        throw  new RuntimeException("无此椭球体");
    }
}
