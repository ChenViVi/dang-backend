package com.yellowzero.backend;

import cn.hutool.core.util.ReUtil;
import cn.hutool.cron.task.Task;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yellowzero.backend.util.DBUtil;

import java.util.List;

public class ImageTask implements Task {

    public void execute() {

        long uid = 7532254335L;
        long containerId = 1076037532254335L;

        String regexTag = "#[^#]+#";
        String mainTag = "黄龄图集";

        int listPage = 1;
        while (true) {
            System.out.println("page = " + listPage);
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
            System.out.println(listResponse);
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
                JSONArray pics;
                JSONObject repostJson = cardJson.getJSONObject("retweeted_status");
                //如果是原创微博
                if (repostJson == null)
                    pics = blogJson.getJSONArray("pics");
                else
                    pics = repostJson.getJSONArray("pics");
                if (pics == null || pics.size() == 0)
                    continue;
                long id = blogJson.getLong("id");
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
                    //保存正文中的tag
                    for (String tag : tags)
                        DBUtil.saveImageTag(tag);
                    //解析每条微博评论
                    int commentPage = 1;
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
                            for (String tag : commentTags)
                                DBUtil.saveImageTag(tag.replaceAll("#", ""));
                        }
                    }
                }
            }
        }
    }
}
