package com.example.voiceprocedures.Main_Components;

import java.io.Serializable;

public class SpeakerClass implements Serializable {

    String speaker;
    String text;
    int mainspeakerlinkedto;

    public int getMainspeakerlinkedto() {
        return mainspeakerlinkedto;
    }

    public void setMainspeakerlinkedto(int mainspeakerlinkedto) {
        this.mainspeakerlinkedto = mainspeakerlinkedto;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
