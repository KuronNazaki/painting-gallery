package com.example.menudemo;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "paintings")
public class PaintingEntity implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Long id;
    private String name;
    private String artist;
    private String dateOfPublish;
    private int drawableId;

    public PaintingEntity(@NonNull Long id, String name, String artist, String dateOfPublish, int drawableId) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.dateOfPublish = dateOfPublish;
        this.drawableId = drawableId;
    }

    public PaintingEntity(@NonNull Long id, Painting painting) {
        super();
        this.id = id;
        this.name = painting.getName();
        this.artist = painting.getArtist();
        this.dateOfPublish = painting.getDateOfPublish();
        this.drawableId = painting.getDrawableId();
    }

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
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
