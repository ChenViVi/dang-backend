package com.yellowzero.backend.service;

import com.yellowzero.backend.model.entity.Music;

import java.util.List;

public interface MusicService {
    List<Music> getList();
    List<Music> getListByTag(int id);
    void update(Music music);
}
