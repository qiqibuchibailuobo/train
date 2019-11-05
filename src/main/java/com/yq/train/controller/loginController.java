package com.yq.train.controller;

import com.yq.train.dto.UserDTO;
import com.yq.train.mapper.AdminMapper;
import com.yq.train.mapper.StudentMapper;
import com.yq.train.mapper.TeacherMapper;
import com.yq.train.model.AdminExample;
import com.yq.train.model.StudentExample;
import com.yq.train.model.TeacherExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
                       Model model,
                       HttpSession session){
        //requestbody接收前端传来的json数据
        //responsebody将后台数据序列化成json传到前端
        //通过session获取当前用户
        AdminExample adminExample = new AdminExample();
        StudentExample studentExample = new StudentExample();
        TeacherExample teacherExample = new TeacherExample();
        if (adminExample.createCriteria().andAdminNameEqualTo(userDTO.getUsername())!=null){
            if(adminExample.createCriteria().andAdminPwdEqualTo(userDTO.getPassword())!=null){

            }
        }
        if(studentExample.createCriteria().andUserNameEqualTo(userDTO.getUsername())!=null){

        }
        if(teacherExample.createCriteria().andUserNameEqualTo(userDTO.getUsername())!=null){

        }


        return userDTO;
    }
}
