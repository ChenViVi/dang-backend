package com.yellowzero.backend.controller;

import com.yellowzero.backend.model.JsonResult;
import com.yellowzero.backend.model.Status;
import com.yellowzero.backend.model.entity.Image;
import com.yellowzero.backend.model.entity.MusicTag;
import com.yellowzero.backend.service.ImageService;
import com.yellowzero.backend.service.ImageTagService;
import com.yellowzero.backend.service.MusicService;
import com.yellowzero.backend.service.MusicTagService;
import com.yellowzero.backend.util.ImageTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/image")
public class ImageController {
    @Autowired
    ImageService imageService;
    @Autowired
    ImageTagService imageTagService;

    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult list( @RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name="size",defaultValue = "20") int size) {
        return new JsonResult(Status.SUCCESS, imageService.getList(page, size));
    }

    @RequestMapping(value = "/tags", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult tags(@RequestParam(name = "image_id", required = false) Integer imageId) {
        if (imageId == null)
            return new JsonResult(Status.SUCCESS, imageTagService.getList());
        return new JsonResult(Status.SUCCESS, imageTagService.getList(imageId));
    }

    @RequestMapping(value = "/refresh", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult refresh() {
        new ImageTask().execute();
        return new JsonResult(Status.SUCCESS);
    }

    @RequestMapping(value = "/view", method = {RequestMethod.GET, RequestMethod.POST})
    public JsonResult refresh(@RequestParam(name = "image_id", required = false) int imageId) {
        Image image  = imageService.getOne(imageId);
        if (image == null)
            return new JsonResult(Status.NOT_FOUND);
        image.setViewCount(image.getViewCount() + 1);
        return new JsonResult(Status.SUCCESS.getCode(), imageService.save(image));
    }
}