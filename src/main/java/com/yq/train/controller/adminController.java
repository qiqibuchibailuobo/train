package com.yq.train.controller;

import com.yq.train.dto.*;
import com.yq.train.mapper.ClassInfoMapper;
import com.yq.train.mapper.CourseMapper;
import com.yq.train.mapper.StudentMapper;
import com.yq.train.model.ClassInfo;
import com.yq.train.model.Course;
import com.yq.train.model.Student;
import com.yq.train.model.StudentExample;
import com.yq.train.service.AdminService;
import com.yq.train.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class adminController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private ClassInfoMapper classInfoMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private StudentMapper studentMapper;
    @GetMapping("/admin")
    public String loginAdmin(Model model,
                             @RequestParam(name = "page",defaultValue = "1") Integer page,
                             @RequestParam(name = "size",defaultValue = "6") Integer size ,
                             @RequestParam(name = "search",required = false) String  search){
        if(search == ""){
            search = null;
        }
        PaginationDTO pagination = courseService.list(search,page,size);
        model.addAttribute("pagination",pagination);
        model.addAttribute("search",search);
        return "admin";
    }
    @GetMapping("/allStudents/{userType}")
    public String courseStudents(
            HttpServletRequest request,
            @PathVariable(value = "userType")int userType,
            @RequestParam(name = "page",defaultValue = "1") Integer page,
            @RequestParam(name = "size",defaultValue = "6") Integer size ,
            @RequestParam(name = "search",required = false) String  search,
            Model model){
        if(userType == 0){
            if(search == ""){
                search = null;
            }
            PaginationDTO pagination = adminService.list(search,page,size);
            model.addAttribute("pagination",pagination);
            model.addAttribute("search",search);
            return "adminStudents";
        }else {
            model.addAttribute("msg","您无权限！");
            return "msg";
        }
    }
    @GetMapping("/adminStudent/{id}")
    public String course(
            @PathVariable(name = "id") int id,
            Model model){
        StudentInfoDTO studentInfoDTO = adminService.getById(id);
        model.addAttribute("student",studentInfoDTO);
        return "adminStudent";
    }
    /**
     * 批量移除学生
     */
    @PostMapping("/deleteStudent")
    @ResponseBody
    public Object deleteStudent(@RequestBody DeleteStudentDTO deleteStudentDTO) {
        String[] strs = deleteStudentDTO.getIds().split(",");
        List<Integer> delList = new ArrayList<>();
        for (String str : strs) {
            delList.add(Integer.parseInt(str));
        }
        for(int i = 0;i<delList.size();i++){
            Integer studentId =  delList.get(i);

            ClassInfo classInfo = classInfoMapper.selectByPrimaryKey(studentId);
            if(classInfo.getCourseId() != 0){
                Course course = courseMapper.selectByPrimaryKey(classInfo.getCourseId());
                int a = course.getStudentCount();
                course.setStudentCount(a-1);
                Date date = new Date();
                course.setGmtModified(date);
                courseMapper.updateByPrimaryKeySelective(course);
            }


            studentMapper.deleteByPrimaryKey(studentId);
            classInfoMapper.deleteByPrimaryKey(studentId);


        }
        return deleteStudentDTO;
    }
    @PostMapping("/adminStudentUpdateInfo")
    @ResponseBody
    public Object adminStudentUpdateInfo(@RequestBody StudentDTO studentDTO) throws IOException {
      Student student =  studentMapper.selectByPrimaryKey(studentDTO.getId());
        student.setiName(studentDTO.getIname());
        student.setTel(studentDTO.getTel());
        Date date = new Date();
        student.setGmtModified(date);
        studentMapper.updateByPrimaryKeySelective(student);
        studentDTO.setType(1);
        return studentDTO;
    }
    @PostMapping(value = "/adminAddStudent")
    @ResponseBody
    public Object adminAddStudent(@RequestBody StudentDTO studentDTO) throws IOException {
        Student student = new Student();
        if(studentDTO.getIname().equals("")||studentDTO.getIname() == null){
            studentDTO.setType(1);
        }else {
            StudentExample studentExample = new StudentExample();
            studentExample.createCriteria()
                    .andUserNameEqualTo(studentDTO.getIname());
            List<Student> allstudent = studentMapper.selectByExample(studentExample);
            if(allstudent.size()>0){
                studentDTO.setType(2);
            }else {
                student.setiName(studentDTO.getIname());
                student.setUserName(studentDTO.getIname());
                student.setUserPwd("123456");
                student.setTel(studentDTO.getTel());
                student.setAddTeacher(0);
                Date date = new Date();
                student.setGmtCreate(date);
                student.setGmtModified(date);
                student.setHeadportraitUrl("nice.jpg");
                student.setUserType(1);
                student.setAddTeacher(0);
                ClassInfo classInfo = new ClassInfo();

                classInfo.setCourseId(0);
                classInfo.setRemnantCourse(0);
                classInfo.setStatus(0);
                classInfo.setStudentId(student.getId());
                classInfo.setTeacherId(0);

                studentMapper.insert(student);
                studentExample.createCriteria()
                        .andUserNameEqualTo(studentDTO.getIname());
                List<Student> allstudents = studentMapper.selectByExample(studentExample);
                Student student1 = allstudents.get(0);
                classInfo.setStudentId(student1.getId());
                classInfoMapper.insert(classInfo);
                studentDTO.setType(0);
            }
        }

        return studentDTO;
    }
}
