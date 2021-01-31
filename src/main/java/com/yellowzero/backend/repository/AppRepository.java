package com.yellowzero.backend.repository;

import com.yellowzero.backend.model.entity.App;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRepository extends JpaRepository<App, Integer> {

}
