package com.yellowzero.backend.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String weiboId;
    private String url;
    @ManyToOne(targetEntity = UserWeibo.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_weibo_id", insertable = false, updatable = false)
    private UserWeibo user;
    @Transient
    private List<ImageTag> tags;
    private String text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWeiboId() {
        return weiboId;
    }

    public void setWeiboId(String weiboId) {
        this.weiboId = weiboId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public UserWeibo getUser() {
        return user;
    }

    public void setUser(UserWeibo user) {
        this.user = user;
    }

    public List<ImageTag> getTags() {
        return tags;
    }

    public void setTags(List<ImageTag> tags) {
        this.tags = tags;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
