package gateway.database;

import java.sql.*;
import java.util.*;

public class Database
{
    private final Connection connection;

    public Database(Connection connection)
    {
        this.connection = connection;
    }

    public List<Message> getMessages()
    {
        List<Message> messages = new ArrayList<>();
        String sql = """
    SELECT receiver, content, status, driver, created_at
    FROM messages
    ORDER BY id DESC
""";


        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql))
        {

            while (resultSet.next())
            {
                messages.add(new Message(
                        resultSet.getString("receiver"),      // receiver
                        resultSet.getString("content"),
                        resultSet.getString("status"),
                        resultSet.getString("driver"),
                        resultSet.getTimestamp("created_at").toLocalDateTime()
                ));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return messages;
    }
}
