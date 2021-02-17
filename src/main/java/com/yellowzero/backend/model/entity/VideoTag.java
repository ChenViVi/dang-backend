package com.yellowzero.backend.model.entity;

import javax.persistence.*;

@Entity
@Table(name="video_tag")
public class VideoTag {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

