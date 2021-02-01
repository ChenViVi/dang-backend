package com.yellowzero.backend.util;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;

import com.yellowzero.backend.model.entity.ImageTag;

import java.sql.SQLException;
import java.util.List;

public class DBUtil {
    public static void saveImageTag(String name) {
        try {
            List<ImageTag> tags = Db.use().find(Entity.create("image_tag")
                    .set("name", name), ImageTag.class);
            if (tags != null && !tags.isEmpty()) {
                ImageTag tag = tags.get(0);
                Db.use().update(Entity.create()
                                .set("name", tag.getName()),
                        Entity.create("image_tag").set("id", tag.getId()));
            } else {
                Db.use().insert(Entity.create("image_tag")
                        .set("name", name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
