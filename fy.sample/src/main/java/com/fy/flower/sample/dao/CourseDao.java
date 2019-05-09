package com.fy.flower.sample.dao;

import com.fy.flower.sample.model.Course;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseDao {
    List<Course> getByStudentId(@Param("id") int id);
}
