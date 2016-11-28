package paygate.controllers;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.event.ActionEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@Named(value = "fileUpload")
@SessionScoped
public class FileUpload implements Serializable {
    
    private UploadedFile file;

    public FileUpload() {
    }
    
    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void uplode(ActionEvent event) {
        System.out.println("File Is : " + file.getFileName());
        System.out.println("File Details : " + new String(file.getContents()));
    }

    public void handleFileUpload(FileUploadEvent event) {
        UploadedFile uf = event.getFile();
        System.out.println("Called....");
        //System.out.println("File Details : "+new String(uf.getContents()));

    }
}
