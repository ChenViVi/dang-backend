package com.yellowzero.backend.service;

import com.yellowzero.backend.model.entity.MusicTag;
import com.yellowzero.backend.model.entity.VideoTag;

import java.util.List;

public interface VideoTagService {
    List<VideoTag> getList();
}
