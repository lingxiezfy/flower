package com.fy.flower.sample.service.common;

import com.fy.flower.sample.dao.ClazzDao;
import com.fy.flower.sample.model.Clazz;
import com.ly.train.flower.common.annotation.FlowerService;
import com.ly.train.flower.common.service.container.ServiceContext;
import com.ly.train.flower.common.service.impl.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@FlowerService
public class ClazzService extends AbstractService<Integer,List<Clazz>> {

    @Autowired
    ClazzDao clazzDao;

    @Override
    public List<Clazz> doProcess(Integer message, ServiceContext context) throws Throwable {
        return clazzDao.getByStudentId(message);
    }
}
