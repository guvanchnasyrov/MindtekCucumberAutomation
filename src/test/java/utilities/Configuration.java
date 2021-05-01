package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Configuration {

    private static Properties properties;
    private static FileInputStream input;

    static {
        String path = System.getProperty("user.dir") + "/src/test/resources/configurations/Configuration.properties";
        try {
            input = new FileInputStream(path);
            properties = new Properties();
            properties.load(input);
        } catch (FileNotFoundException e) {
            System.out.println("Path for properties file is invalid.");
        } catch (IOException e) {
            System.out.println("Failed to load properties file.");
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                System.out.println("Had exception while tried to close input object.");
            }
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

}
