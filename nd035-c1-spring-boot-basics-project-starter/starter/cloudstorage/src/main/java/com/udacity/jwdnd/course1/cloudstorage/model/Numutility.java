package com.udacity.jwdnd.course1.cloudstorage.model;

public class Numutility {
    private Integer numid;
    private Integer dummy;

    public Numutility(Integer numid, Integer dummy){
        this.numid = numid;
        this.dummy = dummy;
    }

    public Integer getNumid(){
        return this.numid;
    }

    public void setNumid(Integer numid ) {
        this.numid = numid;
    }

    public Integer getDummy(){
        return this.dummy;
    }

    public void setDummy(Integer dummy ) {
        this.dummy = dummy;
    }

}


