package com.yq.train.dto;

import lombok.Data;

@Data
public class UpdateTeacherDTO {
    private int id;
    private String iname;
    private Integer tel;
    private String teacherDescribe;
    private String userPwd;
    private String userPwd2;
    private String userPwd3;
    private int type;
}
