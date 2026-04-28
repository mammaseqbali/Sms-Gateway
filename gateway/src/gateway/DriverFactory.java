package gateway;

import api.SmsDriver;

import java.util.ServiceLoader;

public class DriverFactory
{
    public static SmsDriver createDriver() {
        String driverName = System.getenv("SMS_DRIVER");
        if (driverName == null) {
            throw new RuntimeException("SMS_DRIVER environment variable not set");
        }

        ServiceLoader<SmsDriver> loader = ServiceLoader.load(SmsDriver.class);
        for (SmsDriver driver : loader)
        {
            if(driver.getName().equalsIgnoreCase(driverName))
            {
                return driver;
            }
        }
        throw new RuntimeException("SMS_DRIVER environment variable not found");
    }

}
