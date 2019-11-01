package com.yq.train.dto;

import lombok.Data;

import java.util.Date;

@Data
public class StudentDTO {
    private Integer id;
    private String userName;
    private String userPwd;
    private String studentName;
    private Integer tel;
    private Integer addTeacher;
    private Date gmtCreate;
    private Date gmtModified;
    private String headportraitUrl;
}
