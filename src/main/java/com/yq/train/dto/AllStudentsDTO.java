package com.yq.train.dto;

import com.yq.train.model.Admin;
import com.yq.train.model.Student;
import lombok.Data;

@Data
public class AllStudentsDTO {
    private Student student;
    private Admin admin;
}
