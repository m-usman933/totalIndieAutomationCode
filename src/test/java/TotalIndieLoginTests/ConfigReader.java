package TotalIndieLoginTests;

import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    Properties props = new Properties();

    public ConfigReader() throws IOException {
        props.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
    }

    public String getEmail() {
        return props.getProperty("email");
    }

    public String getPassword() {
        return props.getProperty("password");
    }
    public String getGmailPassword(){return props.getProperty("gmail.password");}
}

