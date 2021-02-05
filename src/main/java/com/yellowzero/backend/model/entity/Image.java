package com.yellowzero.backend.model.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String pid;
    private long weiboId;
    private String urlSmall;
    private String urlLarge;
    @ManyToOne(targetEntity = UserWeibo.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_weibo_id", insertable = false, updatable = false)
    private UserWeibo user;
    @Transient
    private List<ImageTag> tags;
    private String text;
    private int viewCount;
    private int likeCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public long getWeiboId() {
        return weiboId;
    }

    public void setWeiboId(long weiboId) {
        this.weiboId = weiboId;
    }

    public String getUrlSmall() {
        return urlSmall;
    }

    public void setUrlSmall(String urlSmall) {
        this.urlSmall = urlSmall;
    }

    public String getUrlLarge() {
        return urlLarge;
    }

    public void setUrlLarge(String urlLarge) {
        this.urlLarge = urlLarge;
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

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Image)) return false;
        Image image = (Image) o;
        return Objects.equals(getPid(), image.getPid()) &&
                Objects.equals(getWeiboId(), image.getWeiboId()) &&
                Objects.equals(getUrlSmall(), image.getUrlSmall()) &&
                Objects.equals(getUrlLarge(), image.getUrlLarge()) &&
                Objects.equals(getText(), image.getText());
    }
}
