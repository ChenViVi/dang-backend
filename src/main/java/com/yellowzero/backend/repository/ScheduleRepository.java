package com.yellowzero.backend.repository;

import com.yellowzero.backend.model.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

}
