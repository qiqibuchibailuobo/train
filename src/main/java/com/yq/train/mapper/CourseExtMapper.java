package com.yq.train.mapper;

import com.yq.train.dto.CourseQueryDTO;
import com.yq.train.model.Course;

import java.util.List;

public interface CourseExtMapper {
    Integer countBySearch(CourseQueryDTO courseQueryDTO);

    List<Course> selectBySearch(CourseQueryDTO courseQueryDTO);

}
