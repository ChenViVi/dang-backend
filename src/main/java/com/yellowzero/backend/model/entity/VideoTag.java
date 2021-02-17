package com.yellowzero.backend.model.entity;

import javax.persistence.*;

@Entity
@Table(name="video_tag")
public class VideoTag {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private long bizId;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getBizId() {
        return bizId;
    }

    public void setBizId(long bizId) {
        this.bizId = bizId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

