package com.yellowzero.backend.service;

import com.yellowzero.backend.model.entity.Image;

import java.util.List;

public interface ImageService {
    Image getOne(int imageId);
    Image save(Image image);
    List<Image> getList(int offset, int size);
    List<Image> getList(int tagId, int offset, int size);
}
