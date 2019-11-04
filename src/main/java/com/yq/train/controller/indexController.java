package com.yq.train.controller;

import com.yq.train.dto.PaginationDTO;
import com.yq.train.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Controller
public class indexController {
    @Autowired
    private CourseService courseService;

    @GetMapping("/")
    public String login(Model model,
                        HttpServletRequest request,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size ,
                        @RequestParam(name = "search",required = false) String  search ){
        PaginationDTO pagination = courseService.list(search,page,size);
        model.addAttribute("pagination",pagination);
        model.addAttribute("search",search);
        return "index";
    }
}
