package com.yq.train.controller;

import com.yq.train.model.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class registerController {
    @GetMapping("/register")
    public String register(){

        return "register";
    }


    @PostMapping("/studentRegister")
    @ResponseBody
    public Object studentRegister(@RequestBody Student student) throws IOException {

        return null;
    }
}
