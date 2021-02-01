package com.yellowzero.backend;

import cn.hutool.core.util.ReUtil;
import cn.hutool.cron.task.Task;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yellowzero.backend.service.ImageTagService;
import com.yellowzero.backend.util.DBUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ImageTask implements Task {

    @Autowired
    ImageTagService imageTagService;

    @Override
    public void execute() {
        String uid = "7532254335";
        String containerId = "1076037532254335";
        String regexTag = "#[^#]+#";
        String mainTag = "黄龄图集";

        String userUrl = String.format("https://m.weibo.cn/api/container/getIndex?type=uid&value=%s&containerid=%s&page=%d",
                uid,
                containerId, 1);
        String response = HttpRequest.
                get(userUrl)
                .header("Referer", String.format("https://m.weibo.cn/u/%s", uid))
                .header("MWeibo-Pwa","1")
                .header("X-Requested-With", "XMLHttpRequest")
                .header("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1")
                .execute().body();
        JSONArray cardsJson = JSON.parseObject(response).getJSONObject("data").getJSONArray("cards");
        for (int i = 0; i < cardsJson.size(); i++) {
            JSONObject cardJson = cardsJson.getJSONObject(i);
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
                for (String tag : tags)
                    DBUtil.saveImageTag(tag);
            }
        }
    }
}
