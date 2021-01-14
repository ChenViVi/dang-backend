package com.yellowzero.backend.controller;

import cn.hutool.core.io.FileUtil;
import com.yellowzero.backend.model.JsonResult;
import com.yellowzero.backend.model.Status;
import com.yellowzero.backend.model.entity.Music;
import com.yellowzero.backend.model.entity.MusicTag;
import com.yellowzero.backend.repository.MusicRepository;
import com.yellowzero.backend.repository.MusicTagRepository;
import com.yellowzero.backend.service.MusicService;
import com.yellowzero.backend.service.MusicTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/music")
public class MusicController {
    @Autowired
    MusicService musicService;
    @Autowired
    MusicTagService musicTagService;

    @RequestMapping(value = "/tags", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult list() {
        List<MusicTag> tags = musicTagService.getList();
        return new JsonResult(Status.SUCCESS, tags);
    }

    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult type(@RequestParam(name = "tag_id") int id) {
        return new JsonResult(Status.SUCCESS, musicService.getListByTag(id));
    }

    @RequestMapping(value = "/test", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult test() {
        for (Music music : musicService.getList()) {
            music.setUrl("http://yellowzero.wblnb.com/"+music.getUrl());
            musicService.update(music);
        }
        return new JsonResult(Status.SUCCESS);
    }
}