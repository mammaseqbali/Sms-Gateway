package gateway;

import static spark.Spark.*;
import gateway.database.Database;
import gateway.database.Message;
import gateway.template.TemplateEngineProvider;
import gg.jte.output.StringOutput;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WebServer
{
    private final Database database;
    public WebServer(Database database)
    {
        this.database = database;
    }
    public void start()
    {
        port(8080);
        get("/", (request, response) ->
        {
            List<Message> messages = database.getMessages();
            Map<String, Object> model = new HashMap<>();
            model.put("messages", messages);

            var output = new StringOutput();
            var engine = TemplateEngineProvider.getEngine();
            engine.render("index.jte", model, output);


            response.type("text/html");
            return output.toString();
        });
    }
}
