package com.yellowzero.backend.service.impl;

import com.yellowzero.backend.model.entity.ImageTag;
import com.yellowzero.backend.repository.ImageRepository;
import com.yellowzero.backend.repository.ImageTagRepository;
import com.yellowzero.backend.service.ImageTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageTagServiceImpl implements ImageTagService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageTagRepository imageTagRepository;

    @Override
    public List<ImageTag> getList() {
        List<ImageTag> tagList = imageTagRepository.findByDisable(0);
        for (ImageTag tag : tagList)
            tag.setCount(imageRepository.countByTagId(tag.getId()));
        return tagList;
    }

    @Override
    public List<ImageTag> getList(int imageId) {
        List<ImageTag> tagList = imageTagRepository.findByImageId(imageId);
        for (ImageTag tag : tagList)
            tag.setCount(imageRepository.countByTagId(tag.getId()));
        return tagList;
    }
}
