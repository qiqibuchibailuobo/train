package com.yq.train.dto;

import lombok.Data;

import java.util.Date;
@Data
public class TeacherDTO {
    private Integer id;
    private String userName;
    private String userPwd;
    private String teacherName;
    private Integer tel;
    private String teacherDescribe;
    private Date gmtCreate;
    private Date gmtModified;
    private Integer courseTotal;
    private String headportraitUrl;
}
