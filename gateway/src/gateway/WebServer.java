package gateway;

import static spark.Spark.*;
import gateway.database.Database;
import gateway.database.Message;
import gateway.service.MessageService;
import gateway.template.TemplateEngineProvider;
import gg.jte.output.StringOutput;
import com.google.gson.Gson;
import gateway.dto.SendMessageRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WebServer
{
    private final Database database;
    private final MessageService service;
    public WebServer(Database database, MessageService service)
    {
        this.database = database;
        this.service = service;
    }
    public void start()
    {
        port(8080);
        get("/health", (request, response) -> {
            response.type("application/json");
            return "{\"status\": \"ok\"}";
        });
        post("/send", (request, response) -> {
            response.type("application/json");

            Gson gson = new Gson();
            SendMessageRequest req = gson.fromJson(request.body(), SendMessageRequest.class);

            Message result = service.sendMessage(req);

            return gson.toJson(result);
        });
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
