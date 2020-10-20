package Transform;
import Jama.LUDecomposition;
import Jama.Matrix;
import org.locationtech.proj4j.ProjCoordinate;
import org.locationtech.proj4j.datum.GeocentricConverter;
import param.LngLatLike;
import param.SevenParam;

import static Utils.EllipsoidUtil.*;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2020/10/20 16:17
 */
public class SevenParamTransform{
    /**
     * 七参数计算
     * @param src 坐标对
     * @param param 七参数
     * @return 转换后坐标对
     */
    public static LngLatLike Transform(LngLatLike src, SevenParam param) {

        /*
          lnglat转换为笛卡尔坐标系
         */
        final GeocentricConverter srcGeocentric = new GeocentricConverter(getEllipsoid(param.getStartEllipsoid()));
        final ProjCoordinate p = new ProjCoordinate(Math.toRadians(src.getX()) , Math.toRadians(src.getY()), src.size()>=3?src.get(2):0);
        srcGeocentric.convertGeodeticToGeocentric(p);
        /*
          笛卡尔坐标七参数转换
         */
        double Dx_BF = param.getDx();
        double Dy_BF = param.getDy();
        double Dz_BF = param.getDz();
        double Rx_BF = param.getRotX()*SECONDS_TO_RAD;
        double Ry_BF = param.getRotY()*SECONDS_TO_RAD;
        double Rz_BF = param.getRotZ()*SECONDS_TO_RAD;
        double M_BF = param.getM()/MILLION+1;

        double x_out = M_BF * (p.x + Rz_BF * p.y - Ry_BF * p.z) + Dx_BF;
        double y_out = M_BF * (-Rz_BF * p.x + p.y + Rx_BF * p.z) + Dy_BF;
        double z_out = M_BF * (Ry_BF * p.x - Rx_BF * p.y + p.z) + Dz_BF;



        /*
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

    /**
     * 七参数反计算
     * @param src 坐标对
     * @param param 七参数
     * @return 转换后坐标对
     */
    public static LngLatLike InverseTransform(LngLatLike src, SevenParam param) {
        /*
         * lnglat转换为笛卡尔坐标系
         */
        final GeocentricConverter srcGeocentric = new GeocentricConverter(getEllipsoid(param.getEndEllipsoid()));
        ProjCoordinate p = new ProjCoordinate(Math.toRadians(src.getX()) , Math.toRadians(src.getY()), src.size()>=3?src.get(2):0);
        srcGeocentric.convertGeodeticToGeocentric(p);

        /*
         * 笛卡尔坐标七参数反转
         */
        double Dx_BF = param.getDx();
        double Dy_BF = param.getDy();
        double Dz_BF = param.getDz();
        double Rx_BF = param.getRotX()*SECONDS_TO_RAD;
        double Ry_BF = param.getRotY()*SECONDS_TO_RAD;
        double Rz_BF = param.getRotZ()*SECONDS_TO_RAD;
        double M_BF = param.getM()/MILLION+1;

        double[] transform =new double[7];
        transform[0]=Dx_BF;
        transform[1]=Dy_BF;
        transform[2]=Dz_BF;
        transform[3]=Rx_BF;
        transform[4]=Ry_BF;
        transform[5]=Rz_BF;
        transform[6]=M_BF;
        sevenTransformInv(p,transform);
        /*
         * 笛卡尔坐标转会lnglat
         */
        final GeocentricConverter distGeocentric = new GeocentricConverter(getEllipsoid(param.getStartEllipsoid()));
        distGeocentric.convertGeocentricToGeodetic(p);

        final LngLatLike target = new LngLatLike();
        target.add(Math.toDegrees(p.x));
        target.add(Math.toDegrees(p.y));
        target.add(p.z);
        return target;


    }

    public static void sevenTransformInv(ProjCoordinate p, double[] transform){
        if (transform.length == 3) {
            p.x -= transform[0];
            p.y -= transform[1];
            p.z -= transform[2];

        } else if (transform.length == 7) {
            double Dx_BF =(p.x- transform[0])/transform[6];
            double Dy_BF =(p.y- transform[1])/transform[6];
            double Dz_BF =(p.z- transform[2])/transform[6];
            double Rx_BF = transform[3];
            double Ry_BF = transform[4];
            double Rz_BF = transform[5];



            double[] rhs={Dx_BF,Dy_BF,Dz_BF};
            double [][] values = {{1, Rz_BF, -Ry_BF},
                    {-Rz_BF, 1, Rx_BF},
                    {Ry_BF, -Rx_BF, 1}};
            final Matrix matrix = new Matrix(values);
            LUDecomposition luDecomposition = new LUDecomposition(matrix);
//            luDecomposition.getL().print(10, 8); // lower matrix
//            luDecomposition.getU().print(10, 8); // upper matrix
            Matrix b = new Matrix(rhs, rhs.length);
            // solve Ax = b for the unknown vector x
            Matrix x = luDecomposition.solve(b);
//            x.print(10, 8); // print the solution
            // calculate the residual error
            Matrix residual = matrix.times(x).minus(b);
            // get the max error (yes, it's very small)
            double rnorm = residual.normInf();
//            System.out.println("residual: " + rnorm);



            p.x = x.get(0,0);
            p.y = x.get(1,0);
            p.z = x.get(2,0);
        }
    }

}
