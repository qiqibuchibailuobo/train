package com.yq.train.mapper;


import com.yq.train.dto.StudentQueryDTO;
import com.yq.train.model.Student;


import java.util.List;

public interface StudentExtMapper {
    Integer countBySearch(StudentQueryDTO studentQueryDTO);

    List<Student> selectBySearch(StudentQueryDTO studentQueryDTO);


}
