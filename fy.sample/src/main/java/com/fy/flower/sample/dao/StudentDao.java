package com.fy.flower.sample.dao;

import com.fy.flower.sample.model.Student;
import org.apache.ibatis.annotations.Param;

public interface StudentDao {
    Student getById(@Param("id") int id);
}
