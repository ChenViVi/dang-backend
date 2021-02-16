package com.yellowzero.backend.service.impl;

import com.yellowzero.backend.model.entity.App;
import com.yellowzero.backend.repository.AppRepository;
import com.yellowzero.backend.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppServiceImpl implements AppService {

    @Autowired
    private AppRepository appRepository;

    @Override
    public List<App> getList() {
        return appRepository.findAll(Sort.by("id").descending());
    }
}
