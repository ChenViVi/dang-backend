package com.yellowzero.backend.service.impl;

import com.yellowzero.backend.model.entity.ImageTag;
import com.yellowzero.backend.repository.ImageTagRepository;
import com.yellowzero.backend.service.ImageTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageTagServiceImpl implements ImageTagService {

    @Autowired
    private ImageTagRepository imageTagRepository;

    @Override
    public List<ImageTag> getList() {
        return imageTagRepository.findAll();
    }

    @Override
    public List<ImageTag> getList(int imageId) {
        return imageTagRepository.findByImageId(imageId);
    }
}
