package org.example.services;

import com.filenet.api.collection.ContentElementList;
import com.filenet.api.core.*;
import org.example.config.FilenetConfig;
import org.example.utils.FilenetConnection;
import org.example.utils.FilenetException;

import java.io.*;

public class FilenetDownloadService {

    private final FilenetConnection connection;

    public FilenetDownloadService(FilenetConfig config) {
        this.connection = new FilenetConnection(config);
        System.out.println("Connected to Filenet successfully");
    }

    public void getDocument(String documentId, String outputFilePath) throws FilenetException {
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;

        try {
            // Step 1: Connect to the Object Store
            ObjectStore objectStore = this.connection.connect();

            // Step 2: Fetch the document by ID
            Document document = Factory.Document.fetchInstance(objectStore, documentId, null);
            if (document != null) {
                // Step 3: Check if document has content
                ContentElementList contentList = document.get_ContentElements();
                if (contentList.isEmpty()) {
                    throw new FilenetException("Document does not contain any content.", null);
                }

                // Step 4: Get the content of the document
                ContentTransfer contentTransfer = (ContentTransfer) contentList.getFirst();
                inputStream = contentTransfer.accessContentStream();

                // Step 5: Write content to the specified output file
                fileOutputStream = new FileOutputStream(outputFilePath);
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, bytesRead);
                }

                System.out.println("Document content successfully saved to: " + outputFilePath);
            } else {
                System.out.println("Document not found.");
            }

        } catch (Exception e) {
            throw new FilenetException("Error while retrieving the document", e);
        } finally {
            // Close the streams
            try {
                if (inputStream != null) inputStream.close();
                if (fileOutputStream != null) fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
