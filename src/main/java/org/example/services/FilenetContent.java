package org.example.services;

import com.filenet.api.core.ContentTransfer;
import com.filenet.api.core.Document;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import org.example.config.FilenetConfig;
import org.example.utils.FilenetConnection;
import org.example.utils.FilenetException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class FilenetContent {

    private final FilenetConnection connection;

    public FilenetContent(FilenetConfig config) {
        this.connection = new FilenetConnection(config);
        System.out.println("Connected to Filenet successfully");
    }

    private Document getDocument(ObjectStore objectStore, String documentPath) {
        return  (Document) Factory.Document.fetchInstance(objectStore, documentPath, null);
    }

    public String getContent(String documentPath) throws FilenetException {
        StringBuilder documentContent = new StringBuilder();

        try {
            ObjectStore objectStore = this.connection.connect();

            Document doc = getDocument(objectStore, documentPath);
            Iterator<?> contentElements = doc.get_ContentElements().iterator();

            while (contentElements.hasNext()) {
                ContentTransfer content = (ContentTransfer) contentElements.next();

                // Get content as an InputStream
                InputStream inputStream = content.accessContentStream();

                // Use BufferedReader to read the InputStream into a String
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        documentContent.append(line).append("\n");
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return documentContent.toString();
    }
}
