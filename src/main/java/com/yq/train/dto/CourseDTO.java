package com.yq.train.dto;

import com.yq.train.model.Teacher;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class CourseDTO {
    private Integer id;
    private Integer teachingId;
    private String courseDescribe;
    private String price;
    private Integer studentCount;
    private Date gmtCreate;
    private Date gmtModified;
    private String headportraitUrl;
    private String classTime;
    private Teacher teacher;
}
