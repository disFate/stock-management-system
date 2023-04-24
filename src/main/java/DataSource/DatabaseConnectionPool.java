package DataSource;

/**
 * @Author: Tsuna
 * @Date: 2023-04-24-12:37
 * @Description:
 */

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnectionPool {

    private static HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(DatabaseConfig.getInstance().getDbUrl());
        config.setUsername(DatabaseConfig.getInstance().getDbUsername());
        config.setPassword(DatabaseConfig.getInstance().getDbPassword());

        // Optional: additional pool configuration
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);

        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    // Optional: close the datasource if needed
    public static void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
