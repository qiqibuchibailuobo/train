package com.yq.train.controller;

import com.alibaba.fastjson.JSONObject;
import com.yq.train.dto.UserDTO;
import com.yq.train.mapper.AdminMapper;
import com.yq.train.mapper.StudentMapper;
import com.yq.train.mapper.TeacherMapper;
import com.yq.train.model.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class loginController {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private StudentMapper studentMapper;




    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Object post(@RequestBody UserDTO userDTO,
                       HttpServletRequest request,
                     HttpServletResponse response,
                       Model model,
                       HttpSession session) throws IOException {
        //requestbody接收前端传来的json数据
        //responsebody将后台数据序列化成json传到前端
        //通过session获取当前用户
        StudentExample studentExample = new StudentExample();
        TeacherExample teacherExample = new TeacherExample();
        AdminExample adminExample = new AdminExample();
        if(userDTO.getUsername()!=null&&userDTO.getPassword()!=null){
            adminExample.createCriteria()
                    .andAdminNameEqualTo(userDTO.getUsername())
                    .andAdminPwdEqualTo(userDTO.getPassword());
            List<Admin> admins = adminMapper.selectByExample(adminExample);

            studentExample.createCriteria()
                    .andUserNameEqualTo(userDTO.getUsername())
                    .andUserPwdEqualTo(userDTO.getPassword());
            List<Student> students = studentMapper.selectByExample(studentExample);

            teacherExample.createCriteria()
                    .andUserNameEqualTo(userDTO.getUsername())
                    .andUserPwdEqualTo(userDTO.getPassword());
            List<Teacher> teachers = teacherMapper.selectByExample(teacherExample);

            if(admins.size()>0){
                Admin admin = admins.get(0);
                userDTO.setMsg("管理员登录成功");
                userDTO.setType(0);
                request.getSession()
                        .setAttribute("user",admin);
                return userDTO;
            }
            if(students.size()>0){
                request.getSession()
                        .setAttribute("role",null);
                Student student = students.get(0);

            }
            if(teachers.size()>0){
                Teacher teacher = teachers.get(0);

            }

            if(admins.size()<=0&&students.size()<=0&&teachers.size()<=0){
                session.setAttribute("error","账号或密码错误");

            }
        }



        return userDTO;
    }

}
