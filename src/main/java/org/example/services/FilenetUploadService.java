package org.example.services;

import com.filenet.api.core.*;
import com.filenet.api.constants.*;
import com.filenet.api.property.Properties;
import com.filenet.api.collection.ContentElementList;
import org.example.config.FilenetConfig;
import org.example.utils.FilenetConnection;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class FilenetUploadService {

    private final FilenetConnection connection;

    public FilenetUploadService(FilenetConfig config) {
        this.connection = new FilenetConnection(config);
        System.out.println("Connected to Filenet successfully");
    }

    public void uploadDocument(File file) {
        try (InputStream fileInputStream = new FileInputStream(file)) {
            // get objectStore
            ObjectStore objectStore = this.connection.connect();

            Folder folder = Factory.Folder.fetchInstance(objectStore, "/Customers", null);

            // Create a new document object
            Document document = Factory.Document.createInstance(objectStore, "Document");

            // Set document properties (e.g., the document title)
            Properties properties = document.getProperties();
            properties.putValue("DocumentTitle", file.getName());

            // Create the content for the document
            ContentElementList contentList = Factory.ContentElement.createList();
            ContentTransfer contentTransfer = Factory.ContentTransfer.createInstance();
            contentTransfer.setCaptureSource(fileInputStream);
            contentTransfer.set_ContentType("application/txt");
            contentList.add(contentTransfer);

            // Set content to the document and save it
            document.set_ContentElements(contentList);
            document.checkin(AutoClassify.DO_NOT_AUTO_CLASSIFY, CheckinType.MAJOR_VERSION);
            document.save(RefreshMode.REFRESH);

            ReferentialContainmentRelationship relationship = folder.file(document, AutoUniqueName.AUTO_UNIQUE, document.get_Name(), DefineSecurityParentage.DO_NOT_DEFINE_SECURITY_PARENTAGE);
            relationship.save(RefreshMode.REFRESH);

            System.out.println("Document uploaded: " + file.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
