package com.fy.flower.sample.dao;

import com.fy.flower.sample.model.Clazz;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClazzDao {
    List<Clazz> getByStudentId(@Param("id") int id);
}
