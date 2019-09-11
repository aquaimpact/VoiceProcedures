package com.example.voiceprocedures.RecyclerView;

import java.io.Serializable;


public class Subchapt implements Serializable {

    String subchaptname;
    String chaptlinked;
    String noOfSects;
    String ID;
    String transid;

    public String getTransid() {
        return transid;
    }

    public void setTransid(String transid) {
        this.transid = transid;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSubchaptname() {
        return subchaptname;
    }

    public void setSubchaptname(String subchaptname) {
        this.subchaptname = subchaptname;
    }

    public String getChaptlinked() {
        return chaptlinked;
    }

    public void setChaptlinked(String chaptlinked) {
        this.chaptlinked = chaptlinked;
    }

    public String getNoOfSects() {
        return noOfSects;
    }

    public void setNoOfSects(String noOfSects) {
        this.noOfSects = noOfSects;
    }





}
