package com.example.menudemo;

import java.io.Serializable;

public class Painting implements Serializable {
    private String name;
    private String artist;
    private String dateOfPublish;
    private int drawableId;

    public Painting(String name, String artist, String dateOfPublish, int drawableId) {
        this.name = name;
        this.artist = artist;
        this.dateOfPublish = dateOfPublish;
        this.drawableId = drawableId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDateOfPublish() {
        return dateOfPublish;
    }

    public void setDateOfPublish(String dateOfPublish) {
        this.dateOfPublish = dateOfPublish;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }
}
