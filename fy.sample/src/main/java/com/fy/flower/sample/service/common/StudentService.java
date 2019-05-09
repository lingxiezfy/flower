package com.fy.flower.sample.service.common;

import com.fy.flower.sample.dao.StudentDao;
import com.fy.flower.sample.model.Student;
import com.ly.train.flower.common.annotation.FlowerService;
import com.ly.train.flower.common.service.Service;
import com.ly.train.flower.common.service.container.ServiceContext;
import com.ly.train.flower.common.service.impl.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;

@FlowerService
public class StudentService extends AbstractService<Integer,Student> {
    @Autowired
    StudentDao studentDao;

    @Override
    public Student doProcess(Integer message, ServiceContext context) throws Throwable {
        return studentDao.getById(message);
    }
}
