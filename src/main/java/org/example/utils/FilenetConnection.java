package org.example.utils;

import com.filenet.api.core.*;
import com.filenet.api.util.UserContext;
import org.example.config.FilenetConfig;

import javax.security.auth.Subject;

public class FilenetConnection {

    private final String uri;
    private final String username;
    private final String password;
    private final String objectStoreName;
    private final String stanza;

    public FilenetConnection(FilenetConfig config) {
        this.uri = config.getUri();
        this.username = config.getUsername();
        this.password = config.getPassword();
        this.objectStoreName = config.getObjectStoreName();
        this.stanza = config.getStanza();
    }

    public ObjectStore connect() throws FilenetException {
        try {
            // Create a connection to the FileNet server
            Connection connection = Factory.Connection.getConnection(uri, null);  // You can also use connect with parameters if needed
            UserContext userContext = UserContext.get();
            Subject subject = UserContext.createSubject(connection, username, password, null);
            userContext.pushSubject(subject);

            // Retrieve the Object Store
            Domain domain = Factory.Domain.fetchInstance(connection, null, null);
            return Factory.ObjectStore.fetchInstance((Domain) domain, objectStoreName, null);
        } catch (Exception e) {
            throw new FilenetException("Failed to connect to Filenet", e);
        }
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
