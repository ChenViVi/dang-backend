package com.yellowzero.backend.model.entity;

import javax.persistence.*;

@Entity
@Table(name="image_tag_join")
public class ImageTagJoin {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int imageId;
    private int tagId;

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }
}

