package com.yq.train.dto;

import com.yq.train.model.Teacher;
import lombok.Data;

import java.util.Date;

@Data
public class CourseDTO {
    private Integer id;
    private Integer teachingId;
    private String courseDescribe;
    private Integer price;
    private Integer studentCount;
    private Date gmtCreate;
    private Date gmtModified;
    private String headportraitUrl;
    private Teacher teacher;
}
