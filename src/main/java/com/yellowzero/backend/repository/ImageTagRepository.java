package com.yellowzero.backend.repository;

import com.yellowzero.backend.model.entity.ImageTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageTagRepository extends JpaRepository<ImageTag, Integer> {
    @Query(value = "SELECT * FROM image_tag " +
            "JOIN image_tag_join ON image_tag.id = image_tag_join.tag_id " +
            "WHERE image_tag.disable = false " +
            "AND image_tag_join.image_id = :imageId", nativeQuery = true)
    List<ImageTag> findByImageId(int imageId);
    
    List<ImageTag> findByDisable(boolean disable);
}
