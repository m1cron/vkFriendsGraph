package ru.micron;

import java.io.*;
import java.util.Properties;

public class Config {
    private static final Config INSTANCE = new Config();
    protected static final String CONFIG_PATH = "./config/vk_graph.properties";
    private final String db_url;
    private final String db_user;
    private final String db_password;

    private Config() {
        try (InputStream is = new FileInputStream(CONFIG_PATH)) {
            Properties props = new Properties();
            props.load(is);
            db_url = props.getProperty("db.url");
            db_user = props.getProperty("db.user");
            db_password = props.getProperty("db.password");
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + CONFIG_PATH);
        }
    }

    public static Config get() {
        return INSTANCE;
    }

    public String getDb_url() {
        return db_url;
    }

    public String getDb_user() {
        return db_user;
    }

    public String getDb_password() {
        return db_password;
    }
}