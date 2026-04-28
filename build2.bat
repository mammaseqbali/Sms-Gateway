@echo off
setlocal

set JAVA_HOME=C:\Users\ieqbl\.jdks\openjdk-26.0.1
set LIB=gateway\lib
set MODS=mods

if not exist %MODS% mkdir %MODS%

echo Compiling sms.api...
"%JAVA_HOME%\bin\javac" -d %MODS%\sms.api ^
  sms.api\src\module-info.java ^
  sms.api\src\api\SmsDriver.java
if errorlevel 1 ( echo Failed at sms.api & exit /b 1 )

echo Compiling sms.nexmo...
"%JAVA_HOME%\bin\javac" --module-path "%MODS%" -d %MODS%\sms.nexmo ^
  sms.nexmo\src\module-info.java ^
  sms.nexmo\src\Ndrivers\Nexmodriver.java
if errorlevel 1 ( echo Failed at sms.nexmo & exit /b 1 )

echo Compiling sms.twilio...
"%JAVA_HOME%\bin\javac" --module-path "%MODS%" -d %MODS%\sms.twilio ^
  sms.twilio\src\module-info.java ^
  sms.twilio\src\Tdrivers\Twiliodriver.java
if errorlevel 1 ( echo Failed at sms.twilio & exit /b 1 )

echo Compiling gateway...
"%JAVA_HOME%\bin\javac" --module-path "%MODS%;%LIB%\gg.jte.jar;%LIB%\gg.jte.runtime.jar;%LIB%\gg.jte.extension.api.jar;%LIB%\spark-core-2.9.4.jar;%LIB%\gson-2.10.1.jar;%LIB%\postgresql-42.7.7.jar;%LIB%\jetty-server-9.4.51.v20230217.jar;%LIB%\jetty-http-9.4.51.v20230217.jar;%LIB%\jetty-io-9.4.51.v20230217.jar;%LIB%\jetty-util-9.4.51.v20230217.jar;%LIB%\javax.servlet-api-4.0.1.jar;%LIB%\slf4j-api-2.0.12.jar;%LIB%\slf4j-simple-2.0.12.jar" ^
  -d %MODS%\gateway ^
  gateway\src\module-info.java ^
  gateway\src\gateway\Main.java ^
  gateway\src\gateway\DbServices.java ^
  gateway\src\gateway\DriverFactory.java ^
  gateway\src\gateway\SmsGateway.java ^
  gateway\src\gateway\WebServer.java ^
  gateway\src\gateway\database\Database.java ^
  gateway\src\gateway\database\Message.java ^
  gateway\src\gateway\database\MessageRepository.java ^
  gateway\src\gateway\database\JdbcMessageRepository.java ^
  gateway\src\gateway\service\MessageService.java ^
  gateway\src\gateway\repository\ConfigRepository.java ^
  gateway\src\gateway\repository\ConfigRepositoryImpl.java ^
  gateway\src\gateway\repository\SmsLogRepository.java ^
  gateway\src\gateway\repository\SmsLogRepositoryImpl.java ^
  gateway\src\gateway\dto\SendMessageRequest.java ^
  gateway\src\gateway\migration\MigrationRunner.java ^
  gateway\src\gateway\template\TemplateConfig.java ^
  gateway\src\gateway\template\TemplateEngineProvider.java ^
  gateway\src\gateway\template\TemplateRenderer.java
if errorlevel 1 ( echo Failed at gateway & exit /b 1 )

echo All modules compiled successfully.

set DB_URL=jdbc:postgresql://localhost:5432/mydb
set DB_USER=Ieqbli
set DB_PASS=Mamali@2005

echo Running...
"%JAVA_HOME%\bin\java" --module-path "%MODS%;%LIB%\gg.jte.jar;%LIB%\gg.jte.runtime.jar;%LIB%\gg.jte.extension.api.jar;%LIB%\spark-core-2.9.4.jar;%LIB%\gson-2.10.1.jar;%LIB%\postgresql-42.7.7.jar;%LIB%\jetty-server-9.4.51.v20230217.jar;%LIB%\jetty-http-9.4.51.v20230217.jar;%LIB%\jetty-io-9.4.51.v20230217.jar;%LIB%\jetty-util-9.4.51.v20230217.jar;%LIB%\javax.servlet-api-4.0.1.jar;%LIB%\slf4j-api-2.0.12.jar;%LIB%\slf4j-simple-2.0.12.jar" ^
     --add-modules sms.twilio,sms.nexmo ^
     -m gateway/gateway.Main

endlocal