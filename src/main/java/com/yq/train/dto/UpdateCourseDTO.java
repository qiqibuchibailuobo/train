package com.yq.train.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateCourseDTO {
    private int courseId;
    private int teachingId;
    private String courseDescribe;
    private String price;
    private int type;
    private String classTime;
    private int userType;
}
