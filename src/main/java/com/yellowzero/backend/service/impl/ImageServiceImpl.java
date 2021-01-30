package com.yellowzero.backend.service.impl;

import com.yellowzero.backend.model.entity.Image;
import com.yellowzero.backend.repository.ImageRepository;
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

    @Override
    public List<Image> getList(int page, int size) {
        return imageRepository.findAll(PageRequest.of(page, size, Sort.by("id").descending())).getContent();
    }

    @Override
    public List<Image> getList(int tagId, int page, int size) {
        return imageRepository.findByTag(tagId, PageRequest.of(page, size, Sort.by("id").descending())).getContent();
    }
}
