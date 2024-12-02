package utilities.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyFileReader {

    private final Properties properties;

    /**
     * Default constructor to load the default configuration file.
     */
    public PropertyFileReader() {
        this("src/main/java/utilities/config/Config.properties");
    }

    /**
     * Constructor to load a custom properties file.
     *
     * @param filePath path to the properties file
     */
    public PropertyFileReader(String filePath) {
        properties = new Properties();
        loadProperties(filePath);
    }

    /**
     * Loads the properties file from the specified file path.
     *
     * @param filePath path to the properties file
     */
    private void loadProperties(String filePath) {
        try (InputStream input = new FileInputStream(filePath)) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file: " + filePath, e);
        }
    }

    /**
     * Retrieves a property value as a String.
     *
     * @param key the key for the property
     * @return the value of the property
     */
    public String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            throw new RuntimeException("Property not found or empty for key: " + key);
        }
        return value.trim();
    }

    /**
     * Retrieves a property value as an int.
     *
     * @param key the key for the property
     * @return the integer value of the property
     */
    public int getIntProperty(String key) {
        String value = getProperty(key);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid integer format for key: " + key + ", value: " + value, e);
        }
    }

    /**
     * Retrieves a property value as a boolean.
     *
     * @param key the key for the property
     * @return the boolean value of the property
     */
    public boolean getBooleanProperty(String key) {
        String value = getProperty(key);
        return Boolean.parseBoolean(value);
    }

    /**
     * Utility method to get the username from the config file.
     *
     * @return the username value
     */
    public String getUsername() {
        return getProperty("username");
    }

    /**
     * Utility method to get the password from the config file.
     *
     * @return the password value
     */
    public String getPassword() {
        return getProperty("password");
    }

    /**
     * Utility method to get the home page URL from the config file.
     *
     * @return the home page URL value
     */
    public String getHomePageURL() {
        return getProperty("homePageUrl");
    }

    /**
     * Utility method to get the browser type from the config file.
     *
     * @return the browser value
     */
    public String getBrowser() {
        return getProperty("browser");
    }

    /**
     * Utility method to get the implicit wait duration in seconds.
     *
     * @return the implicit wait value
     */
    public int getImplicitWait() {
        return getIntProperty("implicitWait");
    }

    /**
     * Utility method to get the page load timeout duration in seconds.
     *
     * @return the page load timeout value
     */
    public int getPageLoadTimeout() {
        return getIntProperty("pageLoadTimeout");
    }

    /**
     * Utility method to get the default wait duration in seconds.
     *
     * @return the default wait duration value
     */
    public int getDefaultWaitDuration() {
        return getIntProperty("defaultWaitDuration");
    }

    /**
     * Utility method to get a custom timeout duration, defaulting to the specified fallback value if missing.
     *
     * @param key          the key for the property
     * @param defaultValue the default value if the property is not set
     * @return the timeout duration value
     */
    public int getTimeoutOrDefault(String key, int defaultValue) {
        try {
            return getIntProperty(key);
        } catch (RuntimeException e) {
            return defaultValue;
        }
    }
}
