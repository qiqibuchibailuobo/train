package com.yq.train.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CourseDTO {
    private Integer id;
    private Integer teachingId;
    private String describe;
    private Integer price;
    private Integer studentCount;
    private Date gmtCreate;
    private Date gmtModified;
    private String headportraitUrl;
}
