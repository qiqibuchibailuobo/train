package com.yq.train.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String password;
    private int type;
    private String msg;
}
