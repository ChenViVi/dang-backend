package com.yellowzero.backend.service;

import com.yellowzero.backend.model.entity.ImageTag;

import java.util.List;

public interface ImageTagService {
    List<ImageTag> getList();
    List<ImageTag> getList(int imageId);
}
