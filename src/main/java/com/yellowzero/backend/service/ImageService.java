package com.yellowzero.backend.service;

import com.yellowzero.backend.model.entity.Image;

import java.util.List;

public interface ImageService {
    Image getOne(int imageId);
    List<Image> getList(int page, int size);
    List<Image> getList(int tagId, int page, int size);
}
