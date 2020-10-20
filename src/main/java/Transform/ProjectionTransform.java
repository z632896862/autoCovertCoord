package Transform;

import org.locationtech.proj4j.*;
import param.LngLatLike;
import param.ProjectionParam;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2020/10/20 16:17
 */
public class ProjectionTransform {
    private static CRSFactory crsFactory = new CRSFactory();
    private static final CoordinateTransformFactory ctFactory = new CoordinateTransformFactory();
    final static CoordinateReferenceSystem wgs84 = crsFactory.createFromName("EPSG:4326");


    public static LngLatLike InverseTransform(LngLatLike src, ProjectionParam param) {
        final CoordinateReferenceSystem first = crsFactory.createFromParameters(param.getCode(), param.getProjWkt());
        final CoordinateTransform transform = ctFactory.createTransform(first, wgs84);
        final ProjCoordinate res = new ProjCoordinate();
        transform.transform(new ProjCoordinate(src.getX(),src.getY()),res);
        return new LngLatLike(res.x,res.y);
    }

    public static LngLatLike Transform(LngLatLike src,ProjectionParam param) {
        final CoordinateReferenceSystem first = crsFactory.createFromParameters(param.getCode(), param.getProjWkt());
        final CoordinateTransform transform = ctFactory.createTransform( wgs84,first);
        final ProjCoordinate res = new ProjCoordinate();
        transform.transform(new ProjCoordinate(src.getX(),src.getY()),res);
        return new LngLatLike(res.x,res.y);
    }
}
