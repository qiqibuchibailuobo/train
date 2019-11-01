package com.yq.train.dto;

import lombok.Data;

@Data
public class ClassInfoDTO {
    private Integer studentId;
    private Integer teacherId;
    private Integer courseId;
    private Integer remnantCourse;
    private String classTime;
    private Integer status;
}
