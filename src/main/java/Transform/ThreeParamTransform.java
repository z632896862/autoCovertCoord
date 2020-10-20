package Transform;

import org.locationtech.proj4j.ProjCoordinate;
import org.locationtech.proj4j.datum.GeocentricConverter;
import param.LngLatLike;
import param.ThreeParam;

import static Utils.EllipsoidUtil.getEllipsoid;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2020/10/20 16:12
 */
public class ThreeParamTransform {
    public static LngLatLike InverseTransform(LngLatLike src, ThreeParam param)
    {
        /**
         * lnglat转换为笛卡尔坐标系
         */
        final GeocentricConverter srcGeocentric = new GeocentricConverter(getEllipsoid(param.getEndEllipsoid()));
        final ProjCoordinate p = new ProjCoordinate(Math.toRadians(src.getX()) , Math.toRadians(src.getY()), src.size()>=3?src.get(2):0);
        srcGeocentric.convertGeodeticToGeocentric(p);
        /**
         * 笛卡尔坐标三参数转换
         */
        double Dx_BF = param.getDx();
        double Dy_BF = param.getDy();
        double Dz_BF = param.getDz();


        double x_out = p.x  - Dx_BF;
        double y_out = p.y  - Dy_BF;
        double z_out = p.z  - Dz_BF;



        /**
         * 笛卡尔坐标转会lnglat
         */
        final ProjCoordinate projCoordinate = new ProjCoordinate(x_out, y_out, z_out);
        final GeocentricConverter distGeocentric = new GeocentricConverter(getEllipsoid(param.getStartEllipsoid()));
        distGeocentric.convertGeocentricToGeodetic(projCoordinate);

        final LngLatLike target = new LngLatLike();
        target.add(Math.toDegrees(projCoordinate.x));
        target.add(Math.toDegrees(projCoordinate.y));
        target.add(projCoordinate.z);
        return target;
    }
    public static LngLatLike Transform(LngLatLike src,ThreeParam param)
    {
/**
 * lnglat转换为笛卡尔坐标系
 */
        final GeocentricConverter srcGeocentric = new GeocentricConverter(getEllipsoid(param.getStartEllipsoid()));
        final ProjCoordinate p = new ProjCoordinate(Math.toRadians(src.getX()) , Math.toRadians(src.getY()), src.size()>=3?src.get(2):0);
        srcGeocentric.convertGeodeticToGeocentric(p);
        /**
         * 笛卡尔坐标三参数转换
         */
        double Dx_BF = param.getDx();
        double Dy_BF = param.getDy();
        double Dz_BF = param.getDz();


        double x_out = p.x  + Dx_BF;
        double y_out = p.y  + Dy_BF;
        double z_out = p.z  + Dz_BF;



        /**
         * 笛卡尔坐标转会lnglat
         */
        final ProjCoordinate projCoordinate = new ProjCoordinate(x_out, y_out, z_out);
        final GeocentricConverter distGeocentric = new GeocentricConverter(getEllipsoid(param.getEndEllipsoid()));
        distGeocentric.convertGeocentricToGeodetic(projCoordinate);

        final LngLatLike target = new LngLatLike();
        target.add(Math.toDegrees(projCoordinate.x));
        target.add(Math.toDegrees(projCoordinate.y));
        target.add(projCoordinate.z);
        return target;
    }

}
