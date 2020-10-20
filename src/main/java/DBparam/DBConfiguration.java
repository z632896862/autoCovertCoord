package DBparam;

/**
 * @author ：xxx
 * @description：TODO
 * @date ：2020/10/20 16:01
 */
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@ApiModel(description = "数据库说明信息")
public class DBConfiguration implements Serializable {
    private static final long serialVersionUID=-601022758864871237L;
    @ApiModelProperty(notes = "ip",example = "192.168.2.222")
    private String host;
    @ApiModelProperty(example = "5432")
    private int port;
    @ApiModelProperty(example = "mas_9661bc7f2f754d5bba4d1e58464aae18gis")
    private String dbName;
    @ApiModelProperty(example = "postgres")
    private String user;
    @ApiModelProperty(example = "gis3857")
    private String password;
    private String schema;
    @ApiModelProperty(notes = "not common use,don't assign this!!!")
    private final List<String> lstNeedSetProperty= Arrays.asList("port", "host", "user", "password", "schema","dbName");

    public DBConfiguration(){

    }

    public DBConfiguration(String host, int port, String dbName, String user, String password) {
        this.host = host;
        this.port = port;
        this.dbName = dbName;
        this.user = user;
        this.password = password;
    }

    public DBConfiguration(Map<String, Object> mapCond){
        Field[] fields = this.getClass().getDeclaredFields();
        for(String key :lstNeedSetProperty){
            if(mapCond.containsKey(key)){
                for(Field field:fields){
                    if(field.getName().equals(key)){
                        try {
                            if("port".equals(field.getName())){
                                field.set(this, mapCond.get(key));
                            }
                            else{
                                field.set(this, mapCond.get(key).toString());
                            }

                        }
                        catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format("jdbc:postgresql_postGIS://%s:%d/%s", host,port,dbName);
    }


    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof DBConfiguration)) {
            return false;
        }
        DBConfiguration configuration = (DBConfiguration) obj;
        return (configuration.getHost().equals(host))&&
                (configuration.getPort()==port)&&
                (configuration.getDbName().equals(dbName))&&
                (configuration.getUser().equals(user))&&
                (configuration.getPassword().equals(password));
    }

    public static boolean isValid(DBConfiguration dbConfiguration){
        if (dbConfiguration==null) {
            return false;
        }

        return !StringUtils.isEmpty(dbConfiguration.getHost()) &&
                !StringUtils.isEmpty("" + dbConfiguration.getPort()) &&
                !StringUtils.isEmpty(dbConfiguration.getDbName()) &&
                !StringUtils.isEmpty(dbConfiguration.getUser()) &&
                !StringUtils.isEmpty(dbConfiguration.getPassword());
    }

}