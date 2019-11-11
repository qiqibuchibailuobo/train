package com.yq.train.dto;

import lombok.Data;

import java.util.Date;

@Data
public class StudentDTO {
    private Integer id;
    private String iname;
    private String userName;
    private String userPwd;
    private Integer tel;
    private Integer addTeacher;
    private Date gmtCreate;
    private Date gmtModified;
    private String headportraitUrl;
    private boolean verificationCode;
    private String verifyInput;
    private String msg;
    private int type;
    private String userPwd2;
}
