package com.yq.train.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class indexController {
    @RequestMapping("/")
    public String login(){
        return "hello";
    }
}
