package com.example.voiceprocedures.Main_Components;

import java.io.Serializable;

public class AnswererClass implements Serializable {

    String speaker;
    String text;
    int linedtospeak;


    public int getLinedtospeak() {
        return linedtospeak;
    }

    public void setLinedtospeak(int linedtospeak) {
        this.linedtospeak = linedtospeak;
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
