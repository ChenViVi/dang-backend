package com.yellowzero.backend.repository;

import com.yellowzero.backend.model.entity.Image;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    @Override
    Optional<Image> findById(Integer integer);

    @Query(value = "SELECT * FROM image " +
            "JOIN user_weibo ON image.user_weibo_id = user_weibo.id " +
            "JOIN image_tag_join ON image.id = image_tag_join.image_id " +
            "WHERE image_tag_join.tag_id = :tagId " +
            "AND image.disable = 0 " +
            "AND image.id > :startId " +
            "ORDER BY image.time DESC " +
            "LIMIT :size", nativeQuery = true)
    List<Image> findByTag(int tagId, int startId, int size);

    @Query(value = "SELECT * FROM image " +
            "JOIN user_weibo ON image.user_weibo_id = user_weibo.id " +
            "JOIN image_tag_join ON image.id = image_tag_join.image_id " +
            "WHERE image.id > :startId " +
            "AND image.disable = 0 " +
            "ORDER BY image.time DESC " +
            "LIMIT :size", nativeQuery = true)
    List<Image> findAll(int startId, int size);

    @Query(value = "SELECT COUNT(image.id) FROM image " +
            "JOIN image_tag_join ON image.id = image_tag_join.image_id " +
            "WHERE image_tag_join.tag_id = :tagId", nativeQuery = true)
    long countByTagId(int tagId);
}
