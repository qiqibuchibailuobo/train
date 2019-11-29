package com.yq.train.dto;

import lombok.Data;

@Data
public class TeacherQueryDTO {
    private String search;
    private Integer page;
    private Integer size;
}
