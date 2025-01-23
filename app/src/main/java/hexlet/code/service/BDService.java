package hexlet.code.service;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import hexlet.code.App;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.stream.Collectors;

@Slf4j
public class BDService {

    public static HikariDataSource isConnectDB() throws SQLException {
        var hikariConfig = new HikariConfig();

        String jdbcDatabaseUrl = System.getenv("JDBC_DATABASE_URL");

        if (jdbcDatabaseUrl == null || jdbcDatabaseUrl.isEmpty()) {
            jdbcDatabaseUrl = "jdbc:h2:mem:hexlet;DB_CLOSE_DELAY=-1;";
        }

        hikariConfig.setJdbcUrl(jdbcDatabaseUrl);

        var dataSource = new HikariDataSource(hikariConfig);
        var url = App.class.getClassLoader().getResourceAsStream("schema.sql");
        var sql = new BufferedReader(new InputStreamReader(url))
                .lines().collect(Collectors.joining("\n"));

        log.info(sql);

        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute(sql);
        }

        return dataSource;
    }
}
