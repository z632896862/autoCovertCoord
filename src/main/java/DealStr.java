import Transform.PipeLineTransform;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import param.LngLatLike;
import param.PipeLineParam;

public class DealStr {
    static PipeLineParam pipeLineParam;
    public static String dealwithstr(String str, PipeLineParam param) throws ParseException {
        WKTReader wktReader=new WKTReader();
        Geometry geometry=wktReader.read(str);
        String newstr="";
        String updatesql="";
        pipeLineParam=param;
        switch (geometry.getGeometryType().toUpperCase())
        {
            case "POINT":
                newstr=String.format("POINT(%s)",dealPointString(str));
                break;
            case "LINESTRING":
                newstr=String.format("LINESTRING(%s)",dealLineString(str));
                break;
            case "POLYGON":
                newstr=String.format("POLYGON((%s))",dealPolygonString(str));
                break;
            case "MULTIPOINT":
                newstr=String.format("MULTIPOINT(%s)",dealMutilPointString(str));
                break;
            case "MULTILINESTRING":
                newstr=String.format("MULTILINESTRING((%s))",dealMutilLineString(str));
                break;
            case "MULTIPOLYGON":
                newstr=String.format("MULTIPOLYGON(((%s)))",dealMutilPolygonString(str));
                break;
            default:
                break;
        }
        return newstr;
    }
    private static String dealPointString(String str)
    {
        String geostr="";
        geostr=removesymol(str,0,0);
        String[] cr=geostr.split(" ");
        LngLatLike lngLatLike=new LngLatLike(Double(cr[0]),Double(cr[1]));
        LngLatLike cr84= PipeLineTransform.Transform(lngLatLike,pipeLineParam);
        return String.format("%s %s",cr84.getX(),cr84.getY());
    }
    private static String dealLineString(String str)
    {
        String geostr="";
        geostr=removesymol(str,0,0);
        String[] allcrs=geostr.split(",");
        for (int q=0;q<=allcrs.length-1;q++)
        {
            String pointStr=allcrs[q];
            allcrs[q]=dealPointString(pointStr);
        }
        return String.join(",",allcrs);
    }
    private static String dealPolygonString(String str)
    {
        String geostr="";
        geostr=removesymol(str,1,1);
        String[] allcrs=geostr.split("\\),\\(");
        for (int q=0;q<=allcrs.length-1;q++)
        {
            String lineStr=allcrs[q];
            allcrs[q]=dealLineString(lineStr);
        }
        return String.join("),(",allcrs);
    }
    private static String dealMutilPointString(String str)
    {
        String geostr="";
        geostr=removesymol(str,0,0);
        String[] allcrs=geostr.split(",");
        for (int q=0;q<=allcrs.length-1;q++)
        {
            String pointStr=allcrs[q];
            allcrs[q]=dealPointString(pointStr);
        }
        return String.join(",",allcrs);
    }
    private static String dealMutilLineString(String str)
    {
        String geostr="";
        geostr=removesymol(str,1,1);
        String[] allcrs=geostr.split("\\),\\(");
        for (int q=0;q<=allcrs.length-1;q++)
        {
            String pointStr=allcrs[q];
            allcrs[q]=dealLineString(pointStr);
        }
        return String.join("),(",allcrs);
    }
    private static String dealMutilPolygonString(String str)
    {
        String geostr="";
        geostr=removesymol(str,2,2);
        String[] allcrs=geostr.split("\\)\\),\\(\\(");
        for (int q=0;q<=allcrs.length-1;q++)
        {
            String pointStr=allcrs[q];
            allcrs[q]=dealPolygonString(pointStr);
        }
        return String.join(")),((",allcrs);
    }
    public static double Double(String str)
    {
        return Double.parseDouble(str);
    }
    private static String removesymol(String str,int leftoffset,int rightoffset)
    {
        int geoStrstartIndex=0;
        int geoStrendIndex=0;
        geoStrstartIndex=str.indexOf("(");
        geoStrendIndex=str.lastIndexOf(")");
        if(geoStrstartIndex<=0||geoStrstartIndex>20)
        {
            return str;
        }
        return str.substring(geoStrstartIndex+1+leftoffset,geoStrendIndex-rightoffset);
    }
}
