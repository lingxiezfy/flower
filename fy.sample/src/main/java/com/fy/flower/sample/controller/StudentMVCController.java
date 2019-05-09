package com.fy.flower.sample.controller;

import com.fy.flower.sample.service.StudentMvcService;
import com.fy.flower.sample.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentMVCController {
    @Autowired
    StudentMvcService studentMvcService;

    @RequestMapping(value = "/info/mvc/{id}",method = RequestMethod.GET)
    public Response<List<Object>> getInfo(@PathVariable Integer id){
        return studentMvcService.getInfo(id);
    }
}
