package com.yellowzero.backend.model.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="user_weibo")
public class UserWeibo {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    private String name;
    private String avatar;

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserWeibo)) return false;
        UserWeibo userWeibo = (UserWeibo) o;
        return getId() == userWeibo.getId() &&
                Objects.equals(getName(), userWeibo.getName()) &&
                Objects.equals(getAvatar(), userWeibo.getAvatar());
    }
}

