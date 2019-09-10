package com.example.voiceprocedures.RecyclerView;

import java.io.Serializable;

public class Chapters implements Serializable {

    String chaptname, comtype, numberOfSubchapter;
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChaptname() {
        return chaptname;
    }

    public String getComtype() {
        return comtype;
    }

    public String getNumberOfSubchapter() {
        return numberOfSubchapter;
    }

    public void setChaptname(String chaptname) {
        this.chaptname = chaptname;
    }

    public void setComtype(String comtype) {
        this.comtype = comtype;
    }

    public void setNumberOfSubchapter(String numberOfSubchapter) {
        this.numberOfSubchapter = numberOfSubchapter;
    }
}
