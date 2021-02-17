package com.yellowzero.backend.service.impl;

import com.yellowzero.backend.model.entity.VideoTag;
import com.yellowzero.backend.repository.VideoTagRepository;
import com.yellowzero.backend.service.VideoTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoTagServiceImpl implements VideoTagService {

    @Autowired
    private VideoTagRepository musicTagRepository;

    @Override
    public List<VideoTag> getList() {
        return musicTagRepository.findAll();
    }
}
