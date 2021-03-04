package com.yellowzero.backend.service.impl;

import com.yellowzero.backend.model.entity.Image;
import com.yellowzero.backend.repository.ImageInfoRepository;
import com.yellowzero.backend.repository.ImageRepository;
import com.yellowzero.backend.repository.ImageTagRepository;
import com.yellowzero.backend.service.ImageService;
import com.yellowzero.backend.util.OffsetBasedPageRequest;
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
    private ImageInfoRepository imageInfoRepository;


    @Autowired
    private ImageTagRepository imageTagRepository;

    @Override
    public Image getOne(int imageId) {
        Image image = imageRepository.findById(imageId).orElse(null);
        if (image == null)
            return null;
        image.setImageInfoSmall(imageInfoRepository.findById(image.getImageInfoSmallId()).orElse(null));
        image.setImageInfoLarge(imageInfoRepository.findById(image.getImageInfoLargeId()).orElse(null));
        image.setTags(imageTagRepository.findByImageId(image.getId()));
        return image;
    }

    @Override
    public Image save(Image image) {
        return imageRepository.saveAndFlush(image);
    }

    @Override
    public List<Image> getList(int offset, int size) {
        List<Image> images = imageRepository.findByDisable(false, new OffsetBasedPageRequest(offset, size, Sort.by("time").descending())).getContent();
        for (Image image : images) {
            image.setImageInfoSmall(imageInfoRepository.findById(image.getImageInfoSmallId()).orElse(null));
            image.setImageInfoLarge(imageInfoRepository.findById(image.getImageInfoLargeId()).orElse(null));
            image.setTags(imageTagRepository.findByImageId(image.getId()));
        }
        return images;
    }

    @Override
    public List<Image> getList(int tagId, int offset, int size) {
        List<Image> images = imageRepository.findByTag(tagId, new OffsetBasedPageRequest(offset, size)).getContent();
        for (Image image : images) {
            image.setImageInfoSmall(imageInfoRepository.findById(image.getImageInfoSmallId()).orElse(null));
            image.setImageInfoLarge(imageInfoRepository.findById(image.getImageInfoLargeId()).orElse(null));
            image.setTags(imageTagRepository.findByImageId(image.getId()));
        }
        return images;
    }
}
