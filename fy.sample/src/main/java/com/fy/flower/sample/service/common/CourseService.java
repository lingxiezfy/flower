package com.fy.flower.sample.service.common;

import com.fy.flower.sample.dao.CourseDao;
import com.fy.flower.sample.model.Course;
import com.ly.train.flower.common.annotation.FlowerService;
import com.ly.train.flower.common.service.Service;
import com.ly.train.flower.common.service.container.ServiceContext;
import com.ly.train.flower.common.service.impl.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@FlowerService
public class CourseService extends AbstractService<Integer,List<Course>> {
    @Autowired
    CourseDao courseDao;

    @Override
    public List<Course> doProcess(Integer message, ServiceContext context) throws Throwable {
        return courseDao.getByStudentId(message);
    }
}
