package com.fy.flower.sample.service;

import com.fy.flower.sample.dao.ClazzDao;
import com.fy.flower.sample.dao.CourseDao;
import com.fy.flower.sample.dao.StudentDao;
import com.fy.flower.sample.util.R;
import com.fy.flower.sample.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentMvcService {

    @Autowired
    ClazzDao clazzDao;
    @Autowired
    CourseDao courseDao;

    @Autowired
    StudentDao studentDao;

    public Response<List<Object>> getInfo(int id){
        List<Object> list = new ArrayList<>();
        list.add(studentDao.getById(id));
        list.add(courseDao.getByStudentId(id));
        list.add(clazzDao.getByStudentId(id));
        return R.ok(list);
    }
}
