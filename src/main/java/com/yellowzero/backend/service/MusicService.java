package com.yellowzero.backend.service;

import com.yellowzero.backend.model.entity.Music;

import java.util.List;

public interface MusicService {
    List<Music> getList();
    List<Music> getList(int tagId);
    void update(Music music);
}
