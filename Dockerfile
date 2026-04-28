FROM eclipse-temurin:24-jdk

WORKDIR /app

# Copy everything
COPY sms.api/ sms.api/
COPY sms.nexmo/ sms.nexmo/
COPY sms.twilio/ sms.twilio/
COPY gateway/ gateway/
COPY migrations/ migrations/


# Create mods directory
RUN mkdir -p mods

# Compile sms.api
RUN javac -d mods/sms.api \
  sms.api/src/module-info.java \
  sms.api/src/api/SmsDriver.java

# Compile sms.nexmo
RUN javac --module-path mods -d mods/sms.nexmo \
  sms.nexmo/src/module-info.java \
  sms.nexmo/src/Ndrivers/Nexmodriver.java

# Compile sms.twilio
RUN javac --module-path mods -d mods/sms.twilio \
  sms.twilio/src/module-info.java \
  sms.twilio/src/Tdrivers/Twiliodriver.java

# Compile gateway
RUN javac --module-path "mods:gateway/lib/gg.jte.jar:gateway/lib/gg.jte.runtime.jar:gateway/lib/gg.jte.extension.api.jar:gateway/lib/spark-core-2.9.4.jar:gateway/lib/gson-2.10.1.jar:gateway/lib/postgresql-42.7.7.jar:gateway/lib/jetty-server-9.4.51.v20230217.jar:gateway/lib/jetty-http-9.4.51.v20230217.jar:gateway/lib/jetty-io-9.4.51.v20230217.jar:gateway/lib/jetty-util-9.4.51.v20230217.jar:gateway/lib/javax.servlet-api-4.0.1.jar:gateway/lib/slf4j-api-2.0.12.jar:gateway/lib/slf4j-simple-2.0.12.jar" \
  -d mods/gateway \
  gateway/src/module-info.java \
  gateway/src/gateway/Main.java \
  gateway/src/gateway/DbServices.java \
  gateway/src/gateway/DriverFactory.java \
  gateway/src/gateway/SmsGateway.java \
  gateway/src/gateway/WebServer.java \
  gateway/src/gateway/database/Database.java \
  gateway/src/gateway/database/Message.java \
  gateway/src/gateway/database/MessageRepository.java \
  gateway/src/gateway/database/JdbcMessageRepository.java \
  gateway/src/gateway/service/MessageService.java \
  gateway/src/gateway/repository/ConfigRepository.java \
  gateway/src/gateway/repository/ConfigRepositoryImpl.java \
  gateway/src/gateway/repository/SmsLogRepository.java \
  gateway/src/gateway/repository/SmsLogRepositoryImpl.java \
  gateway/src/gateway/dto/SendMessageRequest.java \
  gateway/src/gateway/migration/MigrationRunner.java \
  gateway/src/gateway/template/TemplateConfig.java \
  gateway/src/gateway/template/TemplateEngineProvider.java \
  gateway/src/gateway/template/TemplateRenderer.java

# Run
CMD ["java", "--module-path", "mods:gateway/lib/gg.jte.jar:gateway/lib/gg.jte.runtime.jar:gateway/lib/gg.jte.extension.api.jar:gateway/lib/spark-core-2.9.4.jar:gateway/lib/gson-2.10.1.jar:gateway/lib/postgresql-42.7.7.jar:gateway/lib/jetty-server-9.4.51.v20230217.jar:gateway/lib/jetty-http-9.4.51.v20230217.jar:gateway/lib/jetty-io-9.4.51.v20230217.jar:gateway/lib/jetty-util-9.4.51.v20230217.jar:gateway/lib/javax.servlet-api-4.0.1.jar:gateway/lib/slf4j-api-2.0.12.jar:gateway/lib/slf4j-simple-2.0.12.jar", "--class-path", "gateway/lib/slf4j-api-2.0.12.jar:gateway/lib/slf4j-simple-2.0.12.jar:gateway/lib/spark-core-2.9.4.jar:gateway/lib/jetty-server-9.4.51.v20230217.jar:gateway/lib/jetty-http-9.4.51.v20230217.jar:gateway/lib/jetty-io-9.4.51.v20230217.jar:gateway/lib/jetty-util-9.4.51.v20230217.jar:gateway/lib/javax.servlet-api-4.0.1.jar", "--add-modules", "sms.twilio,sms.nexmo", "-m", "gateway/gateway.Main"]