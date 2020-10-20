package DBparam;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * 数据存储类
 * @author zxs
 * 版本 1.0.1
 */
public class DataStore {
    private HikariDataSource geoDataSource =null;
    private HikariConfig configGeo =null;
    public  HikariDataSource fetchHikariDataSource(String  user,String password,String connStr){
        if(geoDataSource==null) {
            configGeo = new HikariConfig();
            configGeo.setJdbcUrl(connStr);
            configGeo.setUsername(user);
            configGeo.setPassword(password);
            configGeo.setMaximumPoolSize(50);
            configGeo.setMinimumIdle(10);
            configGeo.setValidationTimeout(3000);
//            一个连接的生命时长（毫秒），超时且没被使用就被释放，缺省 30mins，建议比数据库超时时长少30s
            configGeo.setMaxLifetime(1800000);
            configGeo.setIdleTimeout(6000);
            configGeo.addDataSourceProperty("cachePrepStmts", "true");
            configGeo.addDataSourceProperty("prepStmtCacheSize", "250");
            configGeo.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            geoDataSource = new HikariDataSource(configGeo);
        }
        return geoDataSource;
    }

    public  void updateDataStore(String  user,String password,String connStr){
        if(geoDataSource!=null) {
            geoDataSource.close();
            geoDataSource=null;
        }
        geoDataSource = fetchHikariDataSource(user,password,connStr);

    }

    public  void destoryDataStore(){
        if(geoDataSource!=null) {
            geoDataSource.close();
            geoDataSource=null;
        }
    }

    /**
     * 需要保证连接的数据库为postgres
     * updateDataStore 调用更改当前数据库为postgres
     * 本方法创建的原因为 有的时候 drop database 不能起作用，必须要把上面的连接全部去掉
     * @param dbName 数据库名
     */
    public  void DeleteDatabase(String dbName) throws Exception{
        String format = String.format("select  pg_terminate_backend (pg_stat_activity.pid) from pg_stat_activity where pg_stat_activity.datname = '%s'", dbName);
        String  deleteSql= String.format("drop database   if exists %s", dbName);
        try (Connection connection = geoDataSource.getConnection()
             ; Statement statement = connection.createStatement()) {
            statement.execute(format);
            boolean execute = statement.execute(deleteSql);
            if (execute) {
                System.out.println(String.format("database %s drop successfully!!", dbName));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public  void ExecuteNaiveSQL(List<String> sqls) throws Exception {
        try (Connection connection = geoDataSource.getConnection()
        ) {
            System.out.println(Thread.currentThread().getName() + "executeSQL");
            Statement statement = connection.createStatement();
            for (String sql : sqls) {
                statement.addBatch(sql);
            }
            statement.executeBatch();
            statement.clearBatch();
        }
    }


    public  void ExecuteScriptSQL(String sql) throws Exception{
        try(Connection connection = geoDataSource.getConnection()){
            Statement statement = connection.createStatement();
            statement.execute(sql);
        }
    }


    public  void ExecuteTransactionalNaiveSQL(List<String> sqls) throws Exception {
        try (Connection connection = geoDataSource.getConnection()
        ) {
            connection.setAutoCommit(false);
            System.out.println(Thread.currentThread().getName() + "executeSQL");
            Statement statement = connection.createStatement();
            for (String sql : sqls) {
                statement.addBatch(sql);
            }
            statement.executeBatch();
            connection.commit();
            statement.clearBatch();
        }
    }
}
