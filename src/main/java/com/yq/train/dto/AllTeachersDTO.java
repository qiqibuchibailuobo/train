package com.yq.train.dto;

import com.yq.train.model.Admin;
import com.yq.train.model.Teacher;
import lombok.Data;

@Data
public class AllTeachersDTO {
    private Teacher teacher;
    private Admin admin;
}
