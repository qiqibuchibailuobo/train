package com.yq.train.controller;

import com.yq.train.dto.*;
import com.yq.train.mapper.ClassInfoMapper;
import com.yq.train.mapper.CourseMapper;
import com.yq.train.mapper.StudentMapper;
import com.yq.train.mapper.TeacherMapper;
import com.yq.train.model.*;
import com.yq.train.service.CourseService;
import com.yq.train.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
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
    @Autowired
    private ClassInfoMapper classInfoMapper;
    @Autowired
    private StudentMapper studentMapper;
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
            @RequestParam(name = "size",defaultValue = "999") Integer size ,
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
    @GetMapping("/course/{id}/Allstudents/{userType}")
    public String courseAllStudents(
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
            PaginationDTO pagination = studentService.AllStudents(search,page,size);
            model.addAttribute("pagination",pagination);
            model.addAttribute("search",search);
            CourseDTO courseDTO = courseService.getById(id);
            model.addAttribute("course",courseDTO);
            return "allStudents";
        }else {
            Teacher teacher = (Teacher) request.getSession().getAttribute("user");
            if(teacher.getId() == course.getTeachingId()){
                if(search == ""){
                    search = null;
                }
                PaginationDTO pagination = studentService.AllStudents(search,page,size);
                model.addAttribute("pagination",pagination);
                model.addAttribute("search",search);
                CourseDTO courseDTO = courseService.getById(id);
                model.addAttribute("course",courseDTO);
                return "allStudents";
            }else {
                model.addAttribute("msg","您无权限！");
                return "msg";
            }
        }
    }
    /**
     * 批量移除学生
     */
    @PostMapping("/studentDeletes")
    @ResponseBody
    public Object batchDeletes(@RequestBody DeleteStudentDTO deleteStudentDTO) {
        String[] strs = deleteStudentDTO.getIds().split(",");
        List<Integer> delList = new ArrayList<>();
        for (String str : strs) {
            delList.add(Integer.parseInt(str));
        }
        for(int i = 0;i<delList.size();i++){
            Integer studentId =  delList.get(i);

            ClassInfo classInfo = classInfoMapper.selectByPrimaryKey(studentId);
            Course course = courseMapper.selectByPrimaryKey(classInfo.getCourseId());
            int a = course.getStudentCount();
            course.setStudentCount(a-1);
            Date date = new Date();
            course.setGmtModified(date);
            courseMapper.updateByPrimaryKeySelective(course);

            classInfo.setTeacherId(0);
            classInfo.setStatus(0);
            classInfo.setRemnantCourse(0);
            classInfo.setCourseId(0);
            classInfoMapper.updateByPrimaryKeySelective(classInfo);

        }
        return deleteStudentDTO;
    }

    /**
     * 批量预约学生
     */
    @PostMapping("/appointmentStudent")
    @ResponseBody
    public Object appointmentStudent(@RequestBody DeleteStudentDTO deleteStudentDTO,HttpServletRequest request) {
        String[] strs = deleteStudentDTO.getIds().split(",");
        List<Integer> delList = new ArrayList<>();
        for (String str : strs) {
            delList.add(Integer.parseInt(str));
        }
        for(int i = 0;i<delList.size();i++){
            Integer studentId =  delList.get(i);

            ClassInfo classInfo = classInfoMapper.selectByPrimaryKey(studentId);
            classInfo.setStatus(1);
            classInfoMapper.updateByPrimaryKeySelective(classInfo);

        }
        return deleteStudentDTO;
    }
    /**
     * 批量取消预约学生
     */
    @PostMapping("/unAppointmentStudent")
    @ResponseBody
    public Object unAppointmentStudent(@RequestBody DeleteStudentDTO deleteStudentDTO,HttpServletRequest request) {
        String[] strs = deleteStudentDTO.getIds().split(",");
        List<Integer> delList = new ArrayList<>();
        for (String str : strs) {
            delList.add(Integer.parseInt(str));
        }
        for(int i = 0;i<delList.size();i++){
            Integer studentId =  delList.get(i);

            ClassInfo classInfo = classInfoMapper.selectByPrimaryKey(studentId);
            classInfo.setStatus(0);
            classInfoMapper.updateByPrimaryKeySelective(classInfo);

        }
        return deleteStudentDTO;
    }
    /**
     * 修改学生预约状态
     */
    @PostMapping("/updateRemnantCourse")
    @ResponseBody
    public Object updateRemnantCourse(@RequestBody RemnantCourseDTO remnantCourseDTO) {
        ClassInfo classInfo = classInfoMapper.selectByPrimaryKey(remnantCourseDTO.getStudentId());
        if(remnantCourseDTO.getRemnantCourse()!=0){
            classInfo.setRemnantCourse(remnantCourseDTO.getRemnantCourse());
            classInfoMapper.updateByPrimaryKeySelective(classInfo);
            remnantCourseDTO.setType(0);
        }else {
            remnantCourseDTO.setType(1);
        }

        return remnantCourseDTO;
    }
    /**
     * 批量加入课堂
     */
    @PostMapping("/addStudentIntoCourse")
    @ResponseBody
    public Object addStudentIntoCourse(@RequestBody AddStudentIntoCourseDTO addStudentIntoCourseDTO,HttpServletRequest request) {
        String[] strs = addStudentIntoCourseDTO.getIds().split(",");
        List<Integer> delList = new ArrayList<>();
        for (String str : strs) {
            delList.add(Integer.parseInt(str));
        }
        for(int i = 0;i<delList.size();i++){
            Integer studentId =  delList.get(i);

            ClassInfo classInfo = classInfoMapper.selectByPrimaryKey(studentId);
            classInfo.setCourseId(addStudentIntoCourseDTO.getCourseId());
            Course course = courseMapper.selectByPrimaryKey(addStudentIntoCourseDTO.getCourseId());
            course.setStudentCount(course.getStudentCount()+1);
            classInfo.setTeacherId(course.getTeachingId());
            classInfo.setRemnantCourse(course.getClassHour());
            classInfo.setStatus(0);
            courseMapper.updateByPrimaryKey(course);
            classInfoMapper.updateByPrimaryKeySelective(classInfo);

        }
        return addStudentIntoCourseDTO;
    }

}
