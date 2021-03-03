package com.yellowzero.backend.util;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;

import com.yellowzero.backend.model.entity.*;

import java.sql.SQLException;
import java.util.List;

public class DBUtil {
    public static ImageTag saveImageTag(String name) {
        try {
            List<ImageTag> tags = Db.use().find(Entity.create("image_tag")
                    .set("name", name), ImageTag.class);
            if (tags == null || tags.isEmpty()) {
                Db.use().insert(Entity.create("image_tag")
                        .set("name", name));
            }
            List<ImageTag> result = Db.use().find(Entity.create("image_tag")
                    .set("name", name), ImageTag.class);
            return result.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ImageInfo saveImageInfo(ImageInfo imageInfo) {
        try {
            List<ImageInfo> imageInfos = Db.use().find(Entity.create("image_info")
                    .set("url", imageInfo.getUrl()), ImageInfo.class);
            if (imageInfos == null || imageInfos.isEmpty()) {
                Db.use().insert(Entity.create("image_info")
                        .set("url", imageInfo.getUrl())
                        .set("width", imageInfo.getWidth())
                        .set("height", imageInfo.getHeight()));
            }
            List<ImageInfo> result = Db.use().find(Entity.create("image_info")
                    .set("url", imageInfo.getUrl()), ImageInfo.class);
            return result.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Image findImage(String pid) {
        try {
            List<Image> result = Db.use().find(Entity.create("image")
                    .set("pid", pid), Image.class);
            if (result == null || result.size() ==0)
                return null;
            return result.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Image saveImage(Image image) {
        try {
            List<Image> images = Db.use().find(Entity.create("image")
                    .set("pid", image.getPid()), Image.class);
            if (images == null || images.isEmpty()) {
                Db.use().insert(Entity.create("image")
                        .set("pid", image.getPid())
                        .set("weibo_id", image.getWeiboId())
                        .set("repost_id", image.getRepostId())
                        .set("user_weibo_id", image.getUser().getId())
                        .set("image_info_small_id", image.getImageInfoSmall().getId())
                        .set("image_info_large_id", image.getImageInfoLarge().getId())
                        .set("text", image.getText())
                        .set("time", image.getTime()));
                List<Image> result = Db.use().find(Entity.create("image")
                        .set("pid", image.getPid()), Image.class);
                return findImage(result.get(0).getPid());
            } else {
                Image dbImage = images.get(0);
                if (!image.equals(dbImage))
                    Db.use().update(Entity.create()
                                    .set("pid", image.getPid())
                                    .set("weibo_id", image.getWeiboId())
                                    .set("repost_id", image.getRepostId())
                                    .set("user_weibo_id", image.getUser().getId())
                                    .set("image_info_large_id", image.getImageInfoSmall().getId())
                                    .set("image_info_large_id", image.getImageInfoLarge().getId())
                                    .set("text", image.getText())
                                    .set("time", image.getTime()),
                            Entity.create("image").set("id", dbImage.getId()));
                image.setId(dbImage.getId());
                return image;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void saveImageTagJoin(int imageId, int tagId) {
        try {
            List<ImageTagJoin> joins = Db.use().find(Entity.create("image_tag_join")
                    .set("image_id", imageId)
                    .set("tag_id", tagId), ImageTagJoin.class);
            if (joins == null || joins.isEmpty()) {
                Db.use().insert(Entity.create("image_tag_join")
                        .set("image_id", imageId)
                        .set("tag_id", tagId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void saveUserWeibo(UserWeibo user) {
        try {
            List<UserWeibo> users = Db.use().find(Entity.create("user_weibo")
                    .set("id", user.getId()), UserWeibo.class);
            if (users == null || users.isEmpty()) {
                Db.use().insert(Entity.create("user_weibo")
                        .set("id", user.getId())
                        .set("name", user.getName())
                        .set("avatar", user.getAvatar()));
            } else {
                UserWeibo dbUserWeibo = users.get(0);
                if (!user.equals(dbUserWeibo))
                    Db.use().update(Entity.create()
                            .set("id", user.getId())
                            .set("name", user.getName())
                            .set("avatar", user.getAvatar()),
                            Entity.create("user_weibo").set("id", user.getId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Image saveImageAndInfo(Image image) {
        ImageInfo imageInfoSmall = saveImageInfo(image.getImageInfoSmall());
        image.setImageInfoSmall(imageInfoSmall);
        ImageInfo imageInfoLarge = saveImageInfo(image.getImageInfoLarge());
        image.setImageInfoLarge(imageInfoLarge);
        return saveImage(image);
    }

    public static void saveImageAndTags(List<Image> images, List<String> names, boolean saveImage) {
        for (Image image : images) {
            if (saveImage)
                image = saveImageAndInfo(image);
            else
                image = findImage(image.getPid());
            for (String name : names) {
                ImageTag tag = saveImageTag(name);
                if (tag == null || image == null)
                    continue;
                saveImageTagJoin(image.getId(), tag.getId());
            }
        }
    }
}
