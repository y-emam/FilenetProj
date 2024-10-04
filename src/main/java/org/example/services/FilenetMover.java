//package org.example.services;
//
//import com.filenet.api.collection.ReferentialContainmentRelationshipSet;
//import com.filenet.api.constants.AutoUniqueName;
//import com.filenet.api.constants.DefineSecurityParentage;
//import com.filenet.api.constants.RefreshMode;
//import com.filenet.api.core.Document;
//import com.filenet.api.core.Factory;
//import com.filenet.api.core.Folder;
//import com.filenet.api.core.ObjectStore;
//import com.filenet.api.util.Id;
//import org.example.config.FilenetConfig;
//import org.example.utils.FilenetConnection;
//
//public class FilenetMover {
//    private final FilenetConnection connection;
//
//    public FilenetMover(FilenetConfig config) {
//        this.connection = new FilenetConnection(config);
//        System.out.println("Connected to Filenet successfully");
//    }
//
//    private Folder getFolderByPath(ObjectStore objectStore, String folderPath) {
//        return Factory.Folder.fetchInstance(objectStore, folderPath, null);
//    }
//
//    public Document getDocumentById(ObjectStore objectStore, String documentPath) {
//        return Factory.Document.fetchInstance(objectStore, documentPath, null);
//    }
//
//    public void moveDocument(String documentPath, String destinationFolderPath) {
//        try {
//            // connect to filenet
//            ObjectStore objectStore = this.connection.connect();
//
//            // Fetch the document, source folder, and destination folder
//            Document document = getDocumentById(objectStore, documentPath);
//            Folder destinationFolder = getFolderByPath(objectStore, destinationFolderPath);
//
//            // Unfile (remove) the document from the source folder
//            Factory.ReferentialContainmentRelationship rcr = sourceFolder.unfile(document);
//
//            // File the document in the destination folder
//            ReferentialContainmentRelationship newRel = destinationFolder.file(document, AutoUniqueName.AUTO_UNIQUE, null, DefineSecurityParentage.DO_NOT_DEFINE_SECURITY_PARENTAGE);
//
//            // Save the changes
//            document.save(RefreshMode.REFRESH);
//            destinationFolder.save(RefreshMode.REFRESH);
//
//            System.out.println("Document moved successfully!");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
