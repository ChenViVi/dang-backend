package com.yellowzero.backend.service.impl;

import com.yellowzero.backend.model.entity.Image;
import com.yellowzero.backend.repository.ImageRepository;
import com.yellowzero.backend.repository.ImageTagRepository;
import com.yellowzero.backend.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageTagRepository imageTagRepository;

    @Override
    public List<Image> getList(int page, int size) {
        List<Image> images = imageRepository.findAll(PageRequest.of(page, size, Sort.by("id").descending())).getContent();
        for (Image image : images)
            image.setTags(imageTagRepository.findByImageId(image.getId()));
        return images;
    }

    @Override
    public List<Image> getList(int tagId, int page, int size) {
        List<Image> images = imageRepository.findByTag(tagId, PageRequest.of(page, size, Sort.by("id").descending())).getContent();
        for (Image image : images)
            image.setTags(imageTagRepository.findByImageId(image.getId()));
        return images;
    }
}
