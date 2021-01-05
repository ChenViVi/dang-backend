package com.yellowzero.backend.repository;

import com.yellowzero.backend.model.entity.MusicTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicTagRepository extends JpaRepository<MusicTag, Integer> {

}
