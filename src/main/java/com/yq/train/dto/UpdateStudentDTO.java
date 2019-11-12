package com.yq.train.dto;

import lombok.Data;

@Data
public class UpdateStudentDTO {
    private String iname;
    private Integer tel;
    private String userPwd;
    private String userPwd2;
    private String userPwd3;
    private int type;
    
}
