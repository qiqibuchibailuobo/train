package com.yq.train.mapper;


import com.yq.train.dto.TeacherQueryDTO;
import com.yq.train.model.Teacher;

import java.util.List;

public interface TeacherExtMapper {
    Integer countBySearch(TeacherQueryDTO teacherQueryDTO);
    List<Teacher> selectBySearch(TeacherQueryDTO teacherQueryDTO);
}
