package com.yq.train.controller;

import com.yq.train.dto.ClassInfoDTO;
import com.yq.train.dto.UserDTO;
import com.yq.train.mapper.ClassInfoMapper;
import com.yq.train.mapper.CourseMapper;
import com.yq.train.mapper.StudentMapper;
import com.yq.train.mapper.TeacherMapper;
import com.yq.train.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class studentController {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private ClassInfoMapper classInfoMapper;
    @GetMapping("/student")
    public String loginStudent(HttpServletRequest request, Model model){
        Student student =(Student) request.getSession().getAttribute("user");
        StudentExample studentExample = new StudentExample();
        TeacherExample teacherExample = new TeacherExample();
        CourseExample courseExample = new CourseExample();
        ClassInfoExample classInfoExample = new ClassInfoExample();



        classInfoExample.createCriteria()
                .andStudentIdEqualTo(student.getId());
        List<ClassInfo> classInfos = classInfoMapper.selectByExample(classInfoExample);
        ClassInfo classInfo = classInfos.get(0);

        teacherExample.createCriteria()
                .andIdEqualTo(classInfo.getTeacherId());
        List<Teacher> teachers = teacherMapper.selectByExample(teacherExample);
        Teacher teacher = teachers.get(0);

        courseExample.createCriteria()
                .andTeachingIdEqualTo(teacher.getId());
        List<Course> courses = courseMapper.selectByExample(courseExample);
        Course course = courses.get(0);

        ClassInfoDTO classInfoDTO = new ClassInfoDTO();
        classInfoDTO.setStudentName(student.getiName());
        classInfoDTO.setTeacherName(teacher.getiName());
        classInfoDTO.setCourseName(course.getCourseDescribe());
        classInfoDTO.setClassTime(course.getClassTime());
        if(classInfo.getStatus() == 1){
            classInfoDTO.setStatus("已预约");
        }else {
            classInfoDTO.setStatus("未预约");
        }

        classInfoDTO.setRemnantCourse(classInfo.getRemnantCourse());

        model.addAttribute("classInfoDTO",classInfoDTO);
        return "student";
    }
}
