package com.yellowzero.backend.service.impl;

import com.yellowzero.backend.model.entity.Schedule;
import com.yellowzero.backend.repository.ScheduleRepository;
import com.yellowzero.backend.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public List<Schedule> getList() {
        return scheduleRepository.findAll(Sort.by("time").descending());
    }
}
