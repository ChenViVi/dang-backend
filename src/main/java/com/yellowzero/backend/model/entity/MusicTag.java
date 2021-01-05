package com.yellowzero.backend.model.entity;

import javax.persistence.*;

@Entity
@Table(name="music_tag")
public class MusicTag {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

