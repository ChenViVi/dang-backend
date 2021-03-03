package com.yellowzero.backend.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
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
    private long repostId;
    @JsonIgnore
    private int disable;
    @JsonIgnore
    private int imageInfoSmallId;
    @JsonIgnore
    private int imageInfoLargeId;
    @Transient
    private ImageInfo imageInfoSmall;
    @Transient
    private ImageInfo imageInfoLarge;
    @ManyToOne(targetEntity = UserWeibo.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_weibo_id", insertable = false, updatable = false)
    private UserWeibo user;
    @Transient
    private List<ImageTag> tags;
    private String text;
    private Timestamp time;
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

    public long getRepostId() {
        return repostId;
    }

    public void setRepostId(long repostId) {
        this.repostId = repostId;
    }

    public int getDisable() {
        return disable;
    }

    public void setDisable(int disable) {
        this.disable = disable;
    }

    public int getImageInfoSmallId() {
        return imageInfoSmallId;
    }

    public void setImageInfoSmallId(int imageInfoSmallId) {
        this.imageInfoSmallId = imageInfoSmallId;
    }

    public int getImageInfoLargeId() {
        return imageInfoLargeId;
    }

    public void setImageInfoLargeId(int imageInfoLargeId) {
        this.imageInfoLargeId = imageInfoLargeId;
    }

    public ImageInfo getImageInfoSmall() {
        return imageInfoSmall;
    }

    public void setImageInfoSmall(ImageInfo imageInfoSmall) {
        this.imageInfoSmall = imageInfoSmall;
    }

    public ImageInfo getImageInfoLarge() {
        return imageInfoLarge;
    }

    public void setImageInfoLarge(ImageInfo imageInfoLarge) {
        this.imageInfoLarge = imageInfoLarge;
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

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
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
                Objects.equals(getRepostId(), image.getRepostId()) &&
                Objects.equals(getImageInfoSmall(), image.getImageInfoSmall()) &&
                Objects.equals(getImageInfoLarge(), image.getImageInfoLarge()) &&
                Objects.equals(getText(), image.getText()) &&
                Objects.equals(getTime(), image.getTime());
    }
}
