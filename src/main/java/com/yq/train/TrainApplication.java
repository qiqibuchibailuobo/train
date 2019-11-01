package com.yq.train;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.yq.train.mapper")
public class TrainApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrainApplication.class, args);
    }

}
