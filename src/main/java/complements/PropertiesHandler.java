package complements;

import application.Main;
import complements.logger.LogHandler;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesHandler {

    private static final LogHandler LOG = new LogHandler(PropertiesHandler.class, "application_logs.txt");

    private static PropertiesHandler handler;

    private Properties properties;

    private PropertiesHandler() {}

    public static PropertiesHandler getInstance() {
        if (handler == null) {
            handler = new PropertiesHandler();
            handler.properties = handler.loadProperties();
        }
        return handler;
    }

    // funny fact: if this method throws an error, logger cannot write the log, because Properties are needed to write logs... xD
    private Properties loadProperties() {
        try {
            InputStream input = Main.class.getClassLoader().getResourceAsStream(System.getenv("PROP_FILE"));
            Properties prop = new Properties();
            prop.load(input);
            return prop;
        } catch (Exception e) {
            LOG.error("loadProperties()", e.getMessage());          // xD
            return null;
        }
    }

    public Properties getProperties() {
        return handler.properties;
    }

    public String getProperty(String propertyName) {
        String value = handler.properties.getProperty(propertyName);
        if (value != null && value.charAt(0) == '$') {
            return System.getenv(value.substring(1));
        }
        return handler.properties.getProperty(propertyName);
    }

}
