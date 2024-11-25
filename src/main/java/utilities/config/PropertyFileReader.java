package utilities.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyFileReader {
    private static final String CONFIG_FILE = "src/main/java/utilities/config/Config.properties";
    private Properties properties;

    public PropertyFileReader() {
        loadProperties();
    }

    private void loadProperties() {
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            properties = new Properties();
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to load configuration file: " + CONFIG_FILE);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getUserName() {
        return properties.getProperty("username");
    }

    public String getPassword() {
        return properties.getProperty("password");
    }

    public String getHomePageURL() {
        return properties.getProperty("homePageUrl");
    }

    public String getBrowser(){
        return properties.getProperty("browser");
    }

    public int getImplicityWait() {
        String implicityWait = properties.getProperty("implicityWait");
        try {
            return Integer.parseInt(implicityWait);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid format for implicityWait in config file: " + implicityWait, e);
        }
    }
    public int getPageLoadTimeout() {
        String timeoutDuration = properties.getProperty("pageLoadTimeout");
        try {
            return Integer.parseInt(timeoutDuration);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid format for pageLoadTimeout in config file: " + timeoutDuration, e);
        }
    }

}


