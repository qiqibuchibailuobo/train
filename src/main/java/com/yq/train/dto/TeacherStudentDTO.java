package com.yq.train.dto;

import lombok.Data;

@Data
public class TeacherStudentDTO {
    private String search;
    private Integer page;
    private Integer size;
    private Integer courseId;
}
