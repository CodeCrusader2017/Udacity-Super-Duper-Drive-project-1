package com.udacity.jwdnd.course1.cloudstorage.model;

public class Credential {
    private Integer credentialsId;
    private String url;
    private String username;
    public String key;
    private String password;
    private Integer userid;

    public Credential(Integer credentialsId, String url, String username, String key, String password, Integer userid) {
        this.credentialsId = credentialsId;
        this.url = url;
        this.username = username;
        this.key = key;
        this.password = password;
        this.userid = userid;

    }

    public Integer getCredentialsId(){return credentialsId;}

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getKey() {
        return key;
    }

    public String getPassword() {
        return password;
    }

    public Integer getUserId() {
        return userid;
    }

    public void setCredentialsId(Integer credentialsId){this.credentialsId = credentialsId; }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserId(Integer userid ) {this.userid = userid; }

}

