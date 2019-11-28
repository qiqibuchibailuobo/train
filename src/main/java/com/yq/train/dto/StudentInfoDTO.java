package com.yq.train.dto;

import com.yq.train.model.Student;
import lombok.Data;

@Data
public class StudentInfoDTO {
    private ClassInfoDTO classInfoDTO;
    private Student student;
}
