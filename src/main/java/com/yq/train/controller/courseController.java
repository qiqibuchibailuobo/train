package com.yq.train.controller;

import com.yq.train.dto.CourseDTO;
import com.yq.train.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller()
public class courseController {
    @Autowired
    private CourseService courseService;

    @GetMapping("/course/{id}")
    public String course(
            @PathVariable(name = "id") int id,
            Model model){

        CourseDTO courseDTO = courseService.getById(id);
        model.addAttribute("course",courseDTO);
        return "course";
    }
}
