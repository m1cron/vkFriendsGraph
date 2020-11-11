package ru.micron;

import java.io.*;
import java.util.Properties;

public class Config {
    private static final Config INSTANCE = new Config();
    protected static final String configPath = "./config/vk_graph.properties";
    private final File db_url;
    private final File db_user;
    private final File db_password;

    private Config() {
        try (InputStream is = new FileInputStream(configPath)) {
            Properties props = new Properties();
            props.load(is);
            db_url = new File(props.getProperty("db.url"));
            db_user = new File(props.getProperty("db.user"));
            db_password = new File(props.getProperty("db.password"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + configPath);
        }
    }

    public static Config get() {
        return INSTANCE;
    }

    public File getDb_url() {
        return db_url;
    }

    public File getDb_user() {
        return db_user;
    }

    public File getDb_password() {
        return db_password;
    }
}