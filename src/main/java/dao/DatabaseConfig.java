package dao;

import java.io.InputStream;
import java.util.Properties;
import java.io.IOException;
/**
 * @Author: Tsuna
 * @Date: 2023-04-21-15:41
 * @Description:
 */
public class DatabaseConfig {
    private static DatabaseConfig instance;

    private String dbUrl;
    private String dbDriver;
    private String dbUsername;
    private String dbPassword;

    private DatabaseConfig() {
        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            if (inputStream == null) {
                throw new RuntimeException("Cannot find db.properties file");
            }

            properties.load(inputStream);

            dbUrl = properties.getProperty("db.url");
            dbDriver = properties.getProperty("db.driver");
            dbUsername = properties.getProperty("db.username");
            dbPassword = properties.getProperty("db.password");
        } catch (IOException e) {
            throw new RuntimeException("Cannot load db.properties file", e);
        }
    }

    public static DatabaseConfig getInstance() {
        if (instance == null) {
            instance = new DatabaseConfig();
        }
        return instance;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getDbDriver() {
        return dbDriver;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }
}

