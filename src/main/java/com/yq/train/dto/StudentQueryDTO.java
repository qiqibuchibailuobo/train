package com.yq.train.dto;

import lombok.Data;

@Data
public class StudentQueryDTO {
    private String search;
    private Integer page;
    private Integer size;
}
