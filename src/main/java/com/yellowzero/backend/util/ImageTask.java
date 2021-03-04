package com.yellowzero.backend.util;

import cn.hutool.core.util.ReUtil;
import cn.hutool.cron.task.Task;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.yellowzero.backend.model.entity.Image;
import com.yellowzero.backend.model.entity.ImageInfo;
import com.yellowzero.backend.model.entity.UserWeibo;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ImageTask implements Task {

    long uid = 7532254335L;
    long containerId = 1076037532254335L;
    String regexTag = "#[^#]+#";
    String regexPic = "“[0-9]”";
    String mainTag = "黄龄图集";
    SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);

    public void execute() {
        int listPage = 1;
        while (true) {
            String listUrl = String.format("https://m.weibo.cn/api/container/getIndex?type=uid&value=%d&containerid=%d&page=%d",
                    uid,
                    containerId, listPage++);
            String listResponse = startRequest(HttpRequest.
                    get(listUrl)
                    .header("Referer", String.format("https://m.weibo.cn/u/%d", uid))
                    .header("MWeibo-Pwa","1")
                    .header("X-Requested-With", "XMLHttpRequest")
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.104 Safari/537.36"), 1000);
            if (listResponse == null)
                return;
            JSONObject listJson;
            try {
                listJson = JSON.parseObject(listResponse);
            }
            catch (JSONException e) {
                return;
            }
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
                int mainTagIndex = -1;
                for (int tagIndex = 0; tagIndex < tags.size(); tagIndex++) {
                    String tag = tags.get(tagIndex).replaceAll("#", "");
                    tags.set(tagIndex, tag);
                    if (tag.equals(mainTag))
                        mainTagIndex = tagIndex;
                    if (!hasMainTag && tag.equals(mainTag))
                        hasMainTag = true;
                }
                //移除主标签
                if (mainTagIndex != -1)
                        tags.remove(mainTagIndex);
                if (hasMainTag) {
                    long weiboId,repostId;
                    weiboId = repostId = blogJson.getLong("id");
                    String time = blogJson.getString("created_at");
                    JSONObject userJson;
                    JSONArray pics;
                    JSONObject repostJson = blogJson.getJSONObject("retweeted_status");
                    String repostText;
                    //如果是原创微博
                    if (repostJson == null) {
                        userJson = blogJson.getJSONObject("user");
                        pics = blogJson.getJSONArray("pics");
                        repostText = blogJson.getString("text");
                    }
                    else {
                        weiboId = repostJson.getLong("id");
                        time = repostJson.getString("created_at");
                        userJson = repostJson.getJSONObject("user");
                        pics = repostJson.getJSONArray("pics");
                        repostText = repostJson.getString("text");
                    }
                    if (pics == null || pics.size() == 0)
                        continue;
                    //保存微博用户
                    if (userJson == null)
                        continue;
                    String detailUrl = String.format("https://m.weibo.cn/statuses/show?id=%d", weiboId);
                    String detailResponse = startRequest(HttpRequest.
                            get(detailUrl)
                            .header("Referer", String.format("https://m.weibo.cn/u/%d", uid))
                            .header("MWeibo-Pwa","1")
                            .header("X-Requested-With", "XMLHttpRequest")
                            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.104 Safari/537.36"), 8000);
                    if (detailResponse == null)
                        return;
                    JSONObject detailJson;
                    try {
                        detailJson = JSON.parseObject(detailResponse);
                        if (detailJson.getInteger("ok") == 1)
                            repostText = detailJson.getJSONObject("data").getString("text");
                    }
                    catch (JSONException e) {

                    }
                    Timestamp timestamp = null;
                    try {
                        timestamp = new Timestamp(dateFormat.parse(time).getTime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    UserWeibo user = new UserWeibo();
                    user.setId(userJson.getLong("id"));
                    user.setName(userJson.getString("screen_name"));
                    user.setAvatar(userJson.getString("profile_image_url"));
                    ArrayList<Image> images = new ArrayList<>();
                    List<String> picIndexs = ReUtil.findAll(regexPic, text, 0);
                    if (picIndexs.size() > 0)
                        for (String picIndexStr : picIndexs) {
                            int picIndex = Integer.parseInt(picIndexStr.replace("“", "").replace("”", "")) - 1;
                            if (picIndex >= 0 && picIndex < pics.size()) {
                                Image image = new Image();
                                JSONObject pic = pics.getJSONObject(picIndex);
                                setImageInfo(image, pic);
                                image.setPid(pic.getString("pid"));
                                image.setWeiboId(weiboId);
                                image.setRepostId(repostId);
                                image.setUser(user);
                                image.setText(repostText);
                                image.setTime(timestamp);
                                images.add(image);
                            }
                        }
                    else
                        for (int picIndex = 0; picIndex < pics.size(); picIndex++) {
                            Image image = new Image();
                            JSONObject pic = pics.getJSONObject(picIndex);
                            setImageInfo(image, pic);
                            image.setPid(pic.getString("pid"));
                            image.setWeiboId(weiboId);
                            image.setRepostId(repostId);
                            image.setUser(user);
                            image.setText(repostText);
                            image.setTime(timestamp);
                            images.add(image);
                        }
                    if (images.size() == 0)
                        continue;
                    DBUtil.saveUserWeibo(user);
                    //保存正文中的tag
                    DBUtil.saveImageAndTags(images, tags, true);
                    //解析每条微博评论
                    int commentPage = 1;
                    long id = blogJson.getLong("id");
                    while (true) {
                        String commentUrl = String.format("https://m.weibo.cn/api/comments/show?id=%d&page=%d", id, commentPage++);
                        String commentResponse = startRequest(HttpRequest.
                                get(commentUrl)
                                .header("Referer", "https://m.weibo.cn/")
                                .header("MWeibo-Pwa","1")
                                .header("X-Requested-With", "XMLHttpRequest")
                                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.104 Safari/537.36"), 8000);
                        if (commentResponse == null)
                            return;
                        JSONObject commentJson;
                        try {
                            commentJson = JSON.parseObject(commentResponse);
                        }
                        catch (JSONException e) {
                            break;
                        }
                        JSONArray commentListJson = commentJson.getJSONObject("data").getJSONArray("data");
                        if (commentListJson == null || commentListJson.size() == 0)
                            break;
                        for (int commentIndex = 0; commentIndex < commentListJson.size(); commentIndex++) {
                            String commentText = commentListJson.getJSONObject(commentIndex).getString("text");
                            List<String> commentTags = ReUtil.findAll(regexTag, commentText, 0);
                            //保存评论中的tag
                            mainTagIndex = -1;
                            for (int commentTagIndex = 0; commentTagIndex < commentTags.size(); commentTagIndex++) {
                                String commentTag = commentTags.get(commentTagIndex).replaceAll("#", "");
                                commentTags.set(commentTagIndex, commentTag);
                                if (commentTag.equals(mainTag))
                                    mainTagIndex = commentTagIndex;
                            }
                            //移除主标签
                            if (mainTagIndex != -1)
                                commentTags.remove(mainTagIndex);
                            DBUtil.saveImageAndTags(images, commentTags, false);
                        }
                    }
                }
            }
        }
    }

    private void setImageInfo(Image image, JSONObject pic) {
        ImageInfo imageInfoLarge = new ImageInfo();
        JSONObject largePicJson = pic.getJSONObject("large");
        imageInfoLarge.setUrl(largePicJson.getString("url"));
        Object largoGeo = largePicJson.get("geo");
        if (largoGeo instanceof JSONObject) {
            JSONObject largeGeoJson = (JSONObject)largoGeo;
            imageInfoLarge.setWidth(largeGeoJson.getInteger("width"));
            imageInfoLarge.setHeight(largeGeoJson.getInteger("height"));
        }
        image.setImageInfoLarge(imageInfoLarge);
        ImageInfo imageInfoSmall = new ImageInfo();
        imageInfoSmall.setUrl(pic.getString("url"));
        Object geo = pic.get("geo");
        if (geo instanceof JSONObject) {
            JSONObject smallGeoJson = (JSONObject)geo;
            imageInfoSmall.setWidth(smallGeoJson.getInteger("width"));
            imageInfoSmall.setHeight(smallGeoJson.getInteger("height"));
        }
        image.setImageInfoSmall(imageInfoSmall);
    }

    private String startRequest(HttpRequest httpRequest, long time){
        try {
            Thread.sleep(time);
            System.out.println(httpRequest.getUrl());
            return httpRequest.execute().body();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
