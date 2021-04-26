package com.udacity.jwdnd.course1.cloudstorage.model;

public class Files {
    private Integer fileId;
    private String fileName;
    private String contenttype;
    private String filesize;
    private Integer userid;
    private byte [] fileData;

    public Files (Integer fileId, String fileName, String contenttype, String filesize, Integer userid, byte [] fileData) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.contenttype = contenttype;
        this.filesize = filesize;
        this.userid = userid;
        this.fileData = fileData;
    }

    public Integer getfileId() {return fileId; }

    public void setfileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setfileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContenttype() {
        return contenttype;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize= filesize;
    }

    public Integer getUserId() {return userid;}

    public void setUserId(Integer userid) {
        this.userid = userid;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte [] fileData) {
        this.fileData = fileData;
    }

}
