package com.fy.flower.sample.service;

import com.ly.train.flower.common.annotation.FlowerService;
import com.ly.train.flower.common.service.Service;
import com.ly.train.flower.common.service.container.ServiceContext;

@FlowerService
public class BeginService implements Service<Integer, Integer> {
    @Override
    public Integer process(Integer message, ServiceContext context) throws Throwable {
        return message;
    }
}
