package com.example.notesapp;

public class firebasemodel {

    private String title, content;

    public firebasemodel(){

    }

    public firebasemodel(String title, String content){

        this.content=content;
        this.title=title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

