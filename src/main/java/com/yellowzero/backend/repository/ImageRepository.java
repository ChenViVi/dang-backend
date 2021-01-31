package com.yellowzero.backend.repository;

import com.yellowzero.backend.model.entity.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    @Query(value = "SELECT * FROM image " +
            "JOIN image_tag_join ON image.id = image_tag_join.image_id " +
            "JOIN user_weibo ON image.user_weibo_id = user_weibo.id " +
            "WHERE image_tag_join.tag_id = :tagId ORDER BY image.id DESC", nativeQuery = true)
    Page<Image> findByTag(int tagId, Pageable pageable);
}
