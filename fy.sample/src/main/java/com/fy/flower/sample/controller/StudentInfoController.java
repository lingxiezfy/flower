package com.fy.flower.sample.controller;

import com.fy.flower.sample.service.BeginService;
import com.fy.flower.sample.service.EndService;
import com.fy.flower.sample.service.common.ClazzService;
import com.fy.flower.sample.service.common.CourseService;
import com.fy.flower.sample.service.common.StudentService;
import com.ly.train.flower.common.annotation.Flower;
import com.ly.train.flower.web.spring.FlowerController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

@RestController
@RequestMapping("/student")
@Flower(value = "StudentInfo", flowNumber = 32)
public class StudentInfoController extends FlowerController{
    @RequestMapping(value = "/info/{id}",method = RequestMethod.GET)
    public void getStudentInfo(@PathVariable Integer id,HttpServletRequest req) throws IOException{
        doProcess(id,req);
    }

    @Override
    public void buildFlower() {
        getServiceFlow().buildFlow(
                BeginService.class,
                Arrays.asList(StudentService.class, CourseService.class, ClazzService.class)
        );
        getServiceFlow().buildFlow(
                Arrays.asList(StudentService.class, CourseService.class, ClazzService.class),
                EndService.class
        );
    }
}
