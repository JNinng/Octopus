package top.ninng.octopus.entry;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 系统基础信息
 *
 * @Author OhmLaw
 * @Version 1.0
 */
@Component
public class Info implements Serializable {

    @Value("${cache.type}")
    public String cacheType;
    @Value("${spring.datasource.url}")
    public String databaseUrl;
    @Value("${spring.datasource.username}")
    public String databaseUserName = "1";
    @Value("${spring.datasource.password}")
    public String databasePassword;

    @Override
    public String toString() {
        return "Info{" +
                "cacheType='" + cacheType + '\'' +
                ", databaseUrl='" + databaseUrl + '\'' +
                ", databaseUserName='" + databaseUserName + '\'' +
                ", databasePassword='" + databasePassword + '\'' +
                '}';
    }

    public void setCacheType(String cacheType) {
        this.cacheType = cacheType;
    }

    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public void setDatabaseUserName(String databaseUserName) {
        this.databaseUserName = databaseUserName;
    }
}
