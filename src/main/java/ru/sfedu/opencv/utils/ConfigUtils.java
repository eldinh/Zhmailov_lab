package ru.sfedu.opencv.utils;

import lombok.SneakyThrows;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtils {

    private static final String DEFAULT_CONFIG_PATH = System.getProperty("user.dir") + "/src/main/resources/config.properties";
    private static final Properties PROPERTIES = new Properties();

    public static String getConfigProperty(String key) {
        return PROPERTIES.getProperty(key);
    }

    static {
        loadConfiguration();
    }

    @SneakyThrows
    private static void loadConfiguration() {
        File nf = new File(DEFAULT_CONFIG_PATH);
        try (InputStream in = new FileInputStream(nf)) {
            PROPERTIES.load(in);
        }
    }
}
