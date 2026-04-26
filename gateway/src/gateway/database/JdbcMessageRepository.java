package gateway.database;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class JdbcMessageRepository implements MessageRepository
{

   private final Connection connection;

   public JdbcMessageRepository(Connection connection)
   {
      this.connection = connection;
   }

    @Override
    public void save(Message message)
    {
        String sql = """
            INSERT INTO messages (receiver, content, status, driver, created_at)
            VALUES (?, ?, ?, ?, ?)
        """;

        try(PreparedStatement statement = connection.prepareStatement(sql))

        {
            statement.setString(1, message.getReceiver());
            statement.setString(2,message.getContent());
            statement.setString(3, message.getStatus());
            statement.setString(4, message.getDriver());
            statement.executeUpdate();


        }
        catch (SQLException e)
        {
            throw new RuntimeException("Failed to save message", e);
        }
    }
    @Override
    public List<Message> findAll(int page, int size)
    {
        String sql = """
            SELECT * FROM messages
            ORDER BY created_at DESC
            LIMIT ? OFFSET ?
        """;

        List<Message> messages = new ArrayList<>();

        int offset = (page - 1) * size;

        try(PreparedStatement statement = connection.prepareStatement(sql))

        {

            statement.setInt(1,size);
            statement.setInt(2, offset);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next())
            {
                Message m = new Message(

                        resultSet.getString("receiver"),
                        resultSet.getString("content"),
                        resultSet.getString("status"),
                        resultSet.getString("driver"),
                        resultSet.getTimestamp("created_at").toLocalDateTime()
                );


                messages.add(m);
            }

        }
        catch (SQLException e)
        {
            throw new RuntimeException("Failed to load messages", e);
        }
        return messages;
    }
}

