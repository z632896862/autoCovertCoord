import DBparam.DBConfiguration;
import DBparam.DataStore;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.commons.io.IOUtils;
import org.locationtech.jts.io.ParseException;
import org.yaml.snakeyaml.Yaml;
import param.PipeLineParam;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
/**
 * @author zhaoyun
 */
public class App extends Application {
    public static void main(String[] args) {
        if(args.length!=3){
            System.out.println("need to specify the config file path or  it won't work");
            return;
        }
        //坐标转换参数
        String Pipeline = args[0];
        //图层名称
        String filePath =args[1];
        //数据库名称
        String configFilePath=args[2];

        System.out.println(filePath);
        System.out.println(configFilePath);
        //读入配置信息和发布信息
        List<String> dbparams = readLines(configFilePath);
        DBconfig db=new DBconfig();
        db.setUrl(dbparams.get(0));
        db.setUser(dbparams.get(1));
        db.setPassword(dbparams.get(2));
        List<String> params = readLines(Pipeline);
        String param="";
        if(params.size()>0 && params!=null)
        {
            param=params.get(0);
        }
        else {
            System.out.println("坐标转换参数错误");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        PipeLineParam pipeLineParam=null;
        try {
            pipeLineParam = objectMapper.readValue(param, PipeLineParam.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> rows = readLines(filePath);
        //Dbconfig dataStore=GetDataStore(Dbconfig);
        ConverCoord(db,rows,pipeLineParam);
        System.out.println("Finished");
    }
    /**
     * 读取geoserver 和数据库的相关信息
     * @param configFilePath
     * @return
     */
    public static DBconfig readConfiguration(String configFilePath){
        Yaml config =new Yaml();
        DBconfig dbconfig=null;
        try {
            InputStream inputStream  = new FileInputStream(configFilePath);
//            Map<String,Object> =(Map<String,Object>)config.load(inputStream);
            dbconfig= config.loadAs(inputStream, DBconfig.class);
            System.out.println(dbconfig);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return dbconfig;
    }
    /**
     * 读取要发布的图层名
     * @param filePath
     * @return
     */
    public static List<String> readLines(String filePath){
        List<String> result =null;
        try{
            InputStream inputStream =new FileInputStream(filePath);
            result= IOUtils.readLines(inputStream, Charset.forName("UTF-8"));

        }catch (IOException ex){
            ex.printStackTrace();
        }
        return result;
    }
    public static void ConverCoord(DBconfig dbConfiguration,List<String> layers,PipeLineParam pipeLineParam)
    {
        for(String row:layers){
            try {
                ConverCoordALayer(dbConfiguration,row,pipeLineParam);
                System.out.println("all finished!");
                System.exit(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void ConverCoordALayer(DBconfig dbConfiguration, String layer, PipeLineParam pipeLineParam) throws SQLException, ParseException, ClassNotFoundException {

        Enumeration<Driver> driverEnum = DriverManager.getDrivers();
        //打印出所有驱动信息
        while(driverEnum.hasMoreElements()){
            System.out.println(driverEnum.nextElement());
        }
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection(dbConfiguration.getUrl(), dbConfiguration.getUser(), dbConfiguration.getPassword());
        String sql="select  objectid,ST_AsText(geom) as wkt from "+layer;
        PreparedStatement cs = connection.prepareStatement(sql);
        ResultSet resultSet = cs.executeQuery();
        List<String> updataSqls=new ArrayList<String>();
        while (resultSet.next()){
            String geom=resultSet.getString("wkt");
            String objid=resultSet.getString("objectid");
            String wkt=DealStr.dealwithstr(geom,pipeLineParam);
            System.out.println(wkt);
            String updataSql=String.format("update "+layer+" set geom=ST_GeomFromText(\'%s\',0) where objectid='%s'",wkt,objid);
            System.out.println(updataSql);
            updataSqls.add(updataSql);
            if(updataSqls.size()>=1000)
            {
                System.out.println(Thread.currentThread().getName() + "executeSQL");
                Statement statement = connection.createStatement();
                for (int i = 0; i < updataSqls.size(); i++) {
                    statement.addBatch(updataSqls.get(i));
                }
                statement.executeBatch();
                statement.clearBatch();
                updataSqls.clear();
            }
           }
        System.out.println(Thread.currentThread().getName() + "executeSQL");
        Statement statement = connection.createStatement();
        for (int i = 0; i < updataSqls.size(); i++) {
            statement.addBatch(updataSqls.get(i));
        }
        statement.executeBatch();
        statement.clearBatch();
        updataSqls.clear();
        }

    public static DataStore GetDataStore(DBConfiguration dbConfiguration)
    {
        DataStore dataStore=null;
        dataStore.updateDataStore(
                dbConfiguration.getUser(),
                dbConfiguration.getPassword(),
                String.format("jdbc:postgresql://%s:%d/%s",dbConfiguration.getHost(),
                        dbConfiguration.getPort(),
                        "postgres"
                )
        );
        return dataStore;
    }
    public static double Double(String str)
    {
        return Double.parseDouble(str);
    }
    @Override
    public void start(Stage primaryStage) {

    }
}
