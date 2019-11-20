package com.yq.train.controller;

import com.yq.train.dto.CourseDTO;
import com.yq.train.dto.PaginationDTO;
import com.yq.train.dto.UpdateCourseDTO;
import com.yq.train.mapper.CourseMapper;
import com.yq.train.mapper.TeacherMapper;
import com.yq.train.model.Course;
import com.yq.train.model.CourseExample;
import com.yq.train.model.Teacher;
import com.yq.train.model.TeacherExample;
import com.yq.train.service.CourseService;
import com.yq.train.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller()
public class courseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private StudentService studentService;
    @GetMapping("/course/{id}")
    public String course(
            @PathVariable(name = "id") int id,
            Model model){

        CourseDTO courseDTO = courseService.getById(id);
        model.addAttribute("course",courseDTO);
        return "course";
    }
    @GetMapping("/course/{id}/students/{userType}")
    public String courseStudents(
            HttpServletRequest request,
            @PathVariable(name = "id") int id,
            @PathVariable(value = "userType")int userType,
            @RequestParam(name = "page",defaultValue = "1") Integer page,
            @RequestParam(name = "size",defaultValue = "6") Integer size ,
            @RequestParam(name = "search",required = false) String  search,
            Model model){
        CourseExample courseExample = new CourseExample();
        courseExample.createCriteria()
                .andIdEqualTo(id);
        Course course = courseMapper.selectByPrimaryKey(id);


        if(userType == 0){
            if(search == ""){
                search = null;
            }
            PaginationDTO pagination = studentService.list(search,page,size,course.getId());
            model.addAttribute("pagination",pagination);
            model.addAttribute("search",search);
            CourseDTO courseDTO = courseService.getById(id);
            model.addAttribute("course",courseDTO);
            return "students";
        }else {
            Teacher teacher = (Teacher) request.getSession().getAttribute("user");
            if(teacher.getId() == course.getTeachingId()){
                if(search == ""){
                    search = null;
                }
                PaginationDTO pagination = studentService.list(search,page,size,course.getId());
                model.addAttribute("pagination",pagination);
                model.addAttribute("search",search);
                CourseDTO courseDTO = courseService.getById(id);
                model.addAttribute("course",courseDTO);
                return "students";
            }else {
                model.addAttribute("msg","您无权限！");
                return "msg";
            }
        }
    }

}
