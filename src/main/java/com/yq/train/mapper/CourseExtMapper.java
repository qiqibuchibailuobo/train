package com.yq.train.mapper;

import com.yq.train.dto.CourseQueryDTO;
import com.yq.train.dto.TeacherCourseDTO;
import com.yq.train.model.Course;

import java.util.List;

public interface CourseExtMapper {
    Integer countBySearch(CourseQueryDTO courseQueryDTO);

    List<Course> selectBySearch(CourseQueryDTO courseQueryDTO);

    Integer teacherCourseCountBySearch(TeacherCourseDTO teacherCourseDTO);

    List<Course> selectByTeacherCourseSearch(TeacherCourseDTO teacherCourseDTO);

}
