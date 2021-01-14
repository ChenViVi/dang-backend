package com.yellowzero.backend.service.impl;

import com.yellowzero.backend.model.entity.Music;
import com.yellowzero.backend.repository.MusicRepository;
import com.yellowzero.backend.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicServiceImpl implements MusicService {

    @Autowired
    private MusicRepository musicTagRepository;

    @Override
    public List<Music> getList() {
        return musicTagRepository.findAll();
    }

    @Override
    public List<Music> getListByTag(int id) {
        return musicTagRepository.findByTag(id);
    }

    @Override
    public void update(Music music) {
        musicTagRepository.save(music);
    }
}
