package com.util.email.bean;

import org.apache.commons.lang.StringUtils;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.URLDataSource;
import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class AttachmentBean implements java.io.Serializable {
    private String sourceType;  //来源类型  FILE 本地文件  URL 网络文件 STREAM 流文件
    private String fileName;
    private String filePath;
    private URL fileUrl;

    private String contentType;
    private InputStream inputStream;

    private DataSource attachmentDs;

    public AttachmentBean(String fileName, String filePath) {
        this.sourceType =  "FILE";
        this.fileName = fileName;
        this.filePath = filePath;

        this.attachmentDs = null;
    }

    public AttachmentBean(String fileName, InputStream inputStream, String contentType){
        this.sourceType =  "STREAM";
        this.fileName = fileName;
        this.inputStream = inputStream;
        this.contentType = contentType;

        this.attachmentDs = null;
    }

    public AttachmentBean(String fileName, URL fileUrl) {
        this.sourceType = "URL";
        this.fileName = fileName;
        this.fileUrl = fileUrl;

        this.attachmentDs = null;
    }

    public DataSource getAttachmentDs() throws IOException {
        if (null == attachmentDs) {
            if (StringUtils.isBlank(sourceType)) return null;
            if ("FILE".equals(sourceType)) {
                attachmentDs = new FileDataSource(filePath);
            } else if("URL".equals(sourceType)) {
                attachmentDs = new URLDataSource(fileUrl);
            } else if("STREAM".equals(sourceType)) {
                attachmentDs = new ByteArrayDataSource(inputStream, contentType);
            }
        }
        return attachmentDs;
    }

    public String getSourceType() {
        return sourceType;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public URL getFileUrl() {
        return fileUrl;
    }
}

