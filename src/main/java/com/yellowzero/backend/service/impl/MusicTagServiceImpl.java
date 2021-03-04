package com.yellowzero.backend.service.impl;

import com.yellowzero.backend.model.entity.Music;
import com.yellowzero.backend.model.entity.MusicTag;
import com.yellowzero.backend.repository.MusicRepository;
import com.yellowzero.backend.repository.MusicTagRepository;
import com.yellowzero.backend.service.MusicTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicTagServiceImpl implements MusicTagService {

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private MusicTagRepository musicTagRepository;

    @Override
    public List<MusicTag> getList() {
        List<MusicTag> tagList = musicTagRepository.findByDisable(false);
        for (MusicTag tag : tagList) {
            tag.setCount(musicRepository.countByTagId(tag.getId()));
            List<Music> musicList = musicRepository.findFirstById(tag.getId());
            if (musicList.size() > 0) {
                Music music = musicList.get(0);
                tag.setCover(music.getCover());
            }
        }
        return tagList;
    }
}
