module gateway
{
    requires sms.api;
    requires java.sql;
    requires gg.jte;
    requires gg.jte.runtime;
    requires static spark.core;
    requires gg.jte.extension.api;
    requires com.google.gson;
    exports gateway.database;
    exports gateway.template;
    opens gateway.dto to com.google.gson;
    opens gateway.database to com.google.gson,gg.jte;
    uses api.SmsDriver;

}