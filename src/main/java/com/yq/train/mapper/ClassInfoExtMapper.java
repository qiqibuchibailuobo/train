package com.yq.train.mapper;

import com.yq.train.dto.TeacherStudentDTO;
import com.yq.train.model.ClassInfo;

import java.util.List;

public interface ClassInfoExtMapper {
    Integer teacherStudentCountBySearch(TeacherStudentDTO teacherStudentDTO);

    List<ClassInfo> selectByTeacherStudentSearch(TeacherStudentDTO teacherStudentDTO);
    Integer AllStudentCountBySearch(TeacherStudentDTO teacherStudentDTO);

    List<ClassInfo> selectByAllStudentSearch(TeacherStudentDTO teacherStudentDTO);
}
