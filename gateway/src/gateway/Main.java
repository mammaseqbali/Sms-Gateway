package gateway;

import api.SmsDriver;
import gateway.database.Database;
import gateway.database.JdbcMessageRepository;
import gateway.database.MessageRepository;
import gateway.migration.MigrationRunner;
import gateway.service.MessageService;
import gateway.template.*;

public class Main
{
    public static void main(String[] args) {
        try {
            // Step 1 - connect
            DbServices db = new DbServices();
            db.connect();

            // Step 2 - run migrations
            new MigrationRunner(db.getConnection()).run();

            // Step 3 - load driver
            SmsDriver driver = DriverFactory.createDriver();

            // Step 4 - start web server
            Database database = new Database(db.getConnection());
            MessageRepository repository = new JdbcMessageRepository(db.getConnection());
            MessageService service = new MessageService(repository,driver);
            new WebServer(database, service).start();

            // NO db.close() — server needs the connection alive!

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
