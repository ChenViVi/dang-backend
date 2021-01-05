package com.yellowzero.backend.service.impl;

import com.yellowzero.backend.model.entity.MusicTag;
import com.yellowzero.backend.repository.MusicTagRepository;
import com.yellowzero.backend.service.MusicTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicTagServiceImpl implements MusicTagService {

    @Autowired
    private MusicTagRepository musicTagRepository;

    @Override
    public List<MusicTag> getList() {
        return musicTagRepository.findAll();
    }
}
