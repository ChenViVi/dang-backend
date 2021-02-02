package com.yellowzero.backend.util;

import cn.hutool.core.util.ReUtil;
import cn.hutool.cron.task.Task;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yellowzero.backend.model.entity.Image;
import com.yellowzero.backend.model.entity.UserWeibo;

import java.util.ArrayList;
import java.util.List;

public class ImageTask implements Task {

    public void execute() {

        long uid = 7532254335L;
        long containerId = 1076037532254335L;

        String regexTag = "#[^#]+#";
        String mainTag = "黄龄图集";

        int listPage = 1;
        while (true) {
            String listUrl = String.format("https://m.weibo.cn/api/container/getIndex?type=uid&value=%d&containerid=%d&page=%d",
                    uid,
                    containerId, listPage++);
            String listResponse = HttpRequest.
                    get(listUrl)
                    .header("Referer", String.format("https://m.weibo.cn/u/%s", uid))
                    .header("MWeibo-Pwa","1")
                    .header("X-Requested-With", "XMLHttpRequest")
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.104 Safari/537.36")
                    .execute().body();
            JSONObject listJson = JSON.parseObject(listResponse);
            if (listJson.getInteger("ok") != 1)
                return;
            JSONArray cardsJson = listJson.getJSONObject("data").getJSONArray("cards");
            if (cardsJson == null || cardsJson.size() == 0)
                break;
            //解析每条微博
            for (int weiboIndex = 0; weiboIndex < cardsJson.size(); weiboIndex++) {
                JSONObject cardJson = cardsJson.getJSONObject(weiboIndex);
                JSONObject blogJson = cardJson.getJSONObject("mblog");
                String text = blogJson.getString("text");
                List<String> tags = ReUtil.findAll(regexTag, text, 0);
                boolean hasMainTag = false;
                for (int tagIndex = 0; tagIndex < tags.size(); tagIndex++) {
                    String tag = tags.get(tagIndex).replaceAll("#", "");
                    tags.set(tagIndex, tag);
                    if (!hasMainTag && tag.equals(mainTag))
                        hasMainTag = true;
                }
                if (hasMainTag) {
                    long repostId = blogJson.getLong("id");
                    JSONObject userJson;
                    JSONArray pics;
                    JSONObject repostJson = blogJson.getJSONObject("retweeted_status");
                    //如果是原创微博
                    if (repostJson == null) {
                        userJson = blogJson.getJSONObject("user");
                        pics = blogJson.getJSONArray("pics");
                    }
                    else {
                        repostId = repostJson.getLong("id");
                        userJson = repostJson.getJSONObject("user");
                        pics = repostJson.getJSONArray("pics");
                    }
                    if (pics == null || pics.size() == 0) {
                        continue;
                    }
                    //保存微博用户
                    if (userJson == null)
                        continue;
                    String detailUrl = String.format("https://m.weibo.cn/statuses/show?id=%d", repostId);
                    String detailResponse = HttpRequest.
                            get(listUrl)
                            .header("Referer", String.format("https://m.weibo.cn/u/%s", uid))
                            .header("MWeibo-Pwa","1")
                            .header("X-Requested-With", "XMLHttpRequest")
                            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.104 Safari/537.36")
                            .execute().body();
                    JSONObject detailJson = JSON.parseObject(detailResponse);
                    if (detailJson.getInteger("ok") != 1)
                        return;
                    String repostText = detailJson.getJSONObject("data").getString("text");
                    UserWeibo user = new UserWeibo();
                    user.setId(userJson.getLong("id"));
                    user.setName(userJson.getString("screen_name"));
                    user.setAvatar(userJson.getString("profile_image_url"));
                    DBUtil.saveUserWeibo(user);
                    ArrayList<Image> images = new ArrayList<>();
                    for (int picIndex = 0; picIndex < pics.size(); picIndex++) {
                        JSONObject pic = pics.getJSONObject(picIndex);
                        Image image = new Image();
                        image.setPid(pic.getString("pid"));
                        image.setWeiboId(repostId);
                        image.setUrlSmall(pic.getString("url"));
                        image.setUrlLarge(pic.getJSONObject("large").getString("url"));
                        image.setUser(user);
                        image.setText(repostText);
                        images.add(image);
                    }
                    //保存正文中的tag
                    DBUtil.saveImageAndTags(images, tags, true);
                    //解析每条微博评论
                    int commentPage = 1;
                    long id = blogJson.getLong("id");
                    while (true) {
                        String commentUrl = String.format("https://m.weibo.cn/api/comments/show?id=%d&page=%d", id, commentPage++);
                        String commentResponse = HttpRequest.
                                get(commentUrl)
                                .header("Referer", "https://m.weibo.cn/")
                                .header("MWeibo-Pwa","1")
                                .header("X-Requested-With", "XMLHttpRequest")
                                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.104 Safari/537.36")
                                .execute().body();
                        JSONObject commentJson = JSON.parseObject(commentResponse);
                        if (commentJson.getInteger("ok") != 1)
                            break;
                        JSONArray commentListJson = commentJson.getJSONObject("data").getJSONArray("data");
                        if (commentListJson == null || commentListJson.size() == 0)
                            break;
                        for (int commentIndex = 0; commentIndex < commentListJson.size(); commentIndex++) {
                            String commentText = commentListJson.getJSONObject(commentIndex).getString("text");
                            List<String> commentTags = ReUtil.findAll(regexTag, commentText, 0);
                            //保存评论中的tag
                            for (int commentTagIndex = 0; commentTagIndex < commentTags.size(); commentTagIndex++) {
                                commentTags.set(commentTagIndex, commentTags.get(commentTagIndex).replaceAll("#", ""));
                            }
                            DBUtil.saveImageAndTags(images, commentTags, false);
                        }
                    }
                }
            }
        }
    }
}
