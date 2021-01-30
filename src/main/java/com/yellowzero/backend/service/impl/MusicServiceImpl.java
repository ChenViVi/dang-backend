package com.yellowzero.backend.service.impl;

import com.yellowzero.backend.model.entity.Music;
import com.yellowzero.backend.repository.MusicRepository;
import com.yellowzero.backend.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicServiceImpl implements MusicService {

    @Autowired
    private MusicRepository musicRepository;

    @Override
    public List<Music> getList() {
        return musicRepository.findAll(Sort.by("id").descending());
    }

    @Override
    public List<Music> getList(int tagId) {
        return musicRepository.findByTag(tagId);
    }

    @Override
    public void update(Music music) {
        musicRepository.save(music);
    }
}
