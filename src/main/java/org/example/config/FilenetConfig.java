package org.example.config;

import java.io.InputStream;
import java.util.Properties;

public class FilenetConfig {
    private final String uri;
    private final String username;
    private final String password;
    private final String objectStoreName;
    private final String stanza;

    public FilenetConfig() throws Exception {
        Properties properties = new Properties();
        InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties");
        if (input == null) {
            throw new Exception("Unable to find application.properties");
        }
        properties.load(input);

        this.uri = properties.getProperty("filenet.uri");
        this.username = properties.getProperty("filenet.username");
        this.password = properties.getProperty("filenet.password");
        this.objectStoreName = properties.getProperty("filenet.objectStoreName");
        this.stanza = properties.getProperty("filenet.stanza");
    }

    public String getUri() {
        return uri;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getObjectStoreName() {
        return objectStoreName;
    }

    public String getStanza() {
        return stanza;
    }
}
