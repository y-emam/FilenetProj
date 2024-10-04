package org.example;

import org.example.config.FilenetConfig;
import org.example.services.FilenetContent;
import org.example.services.FilenetDownloadService;
import org.example.services.FilenetUploadService;

import java.io.File;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws Exception {
        try {
            FilenetConfig filenetConfig = new FilenetConfig();

            if (Objects.equals(args[0], "download")) {
                String documentId = "{B0604D92-0000-C118-81CB-1B660CCBD90E}";
                String outputFilePath = "src/main/java/org/example/test.xlsx";

                FilenetDownloadService filenetDownloadService = new FilenetDownloadService(filenetConfig);
                filenetDownloadService.getDocument(documentId, outputFilePath);
            } else if (Objects.equals(args[0], "upload")) {

                File fileToUpload = new File("src/main/java/org/example/yasserDoc.txt");
                FilenetUploadService filenetUploadService = new FilenetUploadService(filenetConfig);
                filenetUploadService.uploadDocument(fileToUpload);

            } else if (Objects.equals(args[0], "content")) {

                FilenetContent filenetContent = new FilenetContent(filenetConfig);
                String content = filenetContent.getContent("/Customers/yasserDoc.txt");
                System.out.println("Document Content: " + content);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}