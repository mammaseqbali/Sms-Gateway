package gateway;

import gateway.repository.ConfigRepository;
import gateway.repository.ConfigRepositoryImpl;
import gateway.repository.SmsLogRepository;
import gateway.repository.SmsLogRepositoryImpl;
import java.sql.*;

public class DbServices
{
    private Connection connection;
    private ConfigRepository configRepository;
    private SmsLogRepository smsLogRepository;

    public void connect() throws SQLException
    {
//        String url = "jdbc:postgresql://127.0.0.1:5432/mydb";
//        connection = DriverManager.getConnection(url, "Ieqbli", "Mamali@2005");

        String url = System.getenv("DB_URL");
        String username = System.getenv("DB_USER");
        String password = System.getenv("DB_PASS");

        if (url == null)      throw new RuntimeException("DB_URL environment variable is not set");
        if (username == null) throw new RuntimeException("DB_USER environment variable is not set");
        if (password == null) throw new RuntimeException("DB_PASS environment variable is not set");

        connection = DriverManager.getConnection(url, username, password);

        configRepository = new ConfigRepositoryImpl(connection);
        smsLogRepository = new SmsLogRepositoryImpl(connection);
        System.out.println("Connected to database!");
    }

    public Connection getConnection()
    {
        return connection;
    }

    public ConfigRepository getConfigRepository()
    {
        return configRepository;
    }

    public SmsLogRepository getSmsLogRepository()
    {
        return smsLogRepository;
    }

    public void close() throws SQLException
    {
        if (connection != null && !connection.isClosed())
        {
            connection.close();
            System.out.println("Database connection closed.");
        }
    }
}
