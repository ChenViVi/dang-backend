package com.yellowzero.backend.repository;

import com.yellowzero.backend.model.entity.Music;
import com.yellowzero.backend.model.entity.MusicTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MusicRepository extends JpaRepository<Music, Integer> {
    @Query(value = "SELECT * FROM music JOIN music_tag_join ON music.id = music_tag_join.music_id WHERE music_tag_join.tag_id = :tagId ORDER BY music.id DESC", nativeQuery = true)
    List<Music> findByTag(int tagId);
}
