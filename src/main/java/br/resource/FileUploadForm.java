package br.resource;

import java.io.InputStream;

import org.jboss.resteasy.annotations.jaxrs.FormParam;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

public class FileUploadForm {

    @FormParam("file")
    @PartType("application/octet-stream")
    private InputStream file;

    @FormParam("fileName")
    @PartType("text/plain")
    private String fileName;

    @FormParam("contentType")
    @PartType("text/plain")
    private String contentType;

    // Getters e Setters
    public InputStream getFile() {
        return file;
    }

    public void setFile(InputStream file) {
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}