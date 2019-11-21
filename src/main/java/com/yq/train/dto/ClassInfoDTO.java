package com.yq.train.dto;

import com.yq.train.model.ClassInfo;
import lombok.Data;

@Data
public class ClassInfoDTO {
    private int courseId;
    private int studentId;
    private String studentName;
    private String teacherName;
    private String courseName;
    private Integer remnantCourse;
    private String classTime;
    private String status;

}
