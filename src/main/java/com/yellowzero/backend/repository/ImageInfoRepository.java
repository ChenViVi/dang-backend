package com.yellowzero.backend.repository;

import com.yellowzero.backend.model.entity.ImageInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageInfoRepository extends JpaRepository<ImageInfo, Integer> {
}
