package com.yellowzero.backend.controller;

import com.yellowzero.backend.model.JsonResult;
import com.yellowzero.backend.model.Status;
import com.yellowzero.backend.service.VideoTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/video")
public class VideoController {
    @Autowired
    VideoTagService videoTagService;

    @RequestMapping(value = "/tags", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult list() {
        return new JsonResult(Status.SUCCESS, videoTagService.getList());
    }
}