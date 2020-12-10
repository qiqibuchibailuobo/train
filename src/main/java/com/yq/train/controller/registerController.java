package com.yq.train.controller;

import com.yq.train.dto.StudentDTO;
import com.yq.train.mapper.ClassInfoMapper;
import com.yq.train.mapper.StudentMapper;
import com.yq.train.model.ClassInfo;
import com.yq.train.model.Student;
import com.yq.train.model.StudentExample;
import com.yq.train.tool.MD5Utils;
import com.yq.train.tool.RandomValidateCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class registerController {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private ClassInfoMapper classInfoMapper;
    @GetMapping("/register")
    public String register(){

        return "register";
    }

    /**
     * 学生注册
     * @param studentDTO
     * @param request
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    @PostMapping("/studentRegister")
    @ResponseBody
    public Object studentRegister(@RequestBody StudentDTO studentDTO,HttpServletRequest request) throws IOException, NoSuchAlgorithmException {
        Student student = new Student();
        studentDTO.setIname(studentDTO.getIname().replace(" ",""));
        studentDTO.setUserName(studentDTO.getUserName().replace(" ",""));
        studentDTO.setUserPwd(studentDTO.getUserPwd().replace(" ",""));
        studentDTO.setUserPwd2(studentDTO.getUserPwd2().replace(" ",""));
        System.out.println(studentDTO.getIname());
        StudentExample studentExample = new StudentExample();
        studentExample.createCriteria()
                .andUserNameEqualTo(studentDTO.getUserName());
        List<Student> students = studentMapper.selectByExample(studentExample);
        if(students.size()>0){
            studentDTO.setMsg("账号已经存在！");
            studentDTO.setType(0);
            return studentDTO;
        }else if (!studentDTO.getUserPwd().equals(studentDTO.getUserPwd2())){
            studentDTO.setMsg("两次密码不一致！");
            studentDTO.setType(2);
            return studentDTO;
        }else if(!checkname(studentDTO.getIname())) {
            studentDTO.setType(3);
            return studentDTO;
        }

        else {
            student.setiName(studentDTO.getIname());
            student.setUserName(studentDTO.getUserName());

            MD5Utils md5Utils = new MD5Utils();
            String pwd = md5Utils.toMD5(studentDTO.getUserPwd());

            student.setUserPwd(pwd);
            student.setTel(studentDTO.getTel());
            student.setAddTeacher(0);
            Date date = new Date();
            student.setGmtCreate(date);
            student.setGmtModified(date);
            student.setHeadportraitUrl("nice.jpg");
            student.setUserType(1);
            ClassInfo classInfo = new ClassInfo();

            classInfo.setCourseId(0);
            classInfo.setRemnantCourse(0);
            classInfo.setStatus(0);
            classInfo.setStudentId(student.getId());
            classInfo.setTeacherId(0);
            studentDTO.setType(1);
            studentDTO.setMsg("注册成功！");
            String random = (String) request.getSession().getAttribute("RANDOMVALIDATECODEKEY");
            if(chek(studentDTO.getVerifyInput(),random)){
                studentMapper.insert(student);
                studentExample.createCriteria()
                        .andUserNameEqualTo(studentDTO.getUserName());
                List<Student> allstudents = studentMapper.selectByExample(studentExample);
                Student student1 = allstudents.get(0);
                classInfo.setStudentId(student1.getId());
                classInfoMapper.insert(classInfo);
            }
            studentDTO.setVerificationCode(chek(studentDTO.getVerifyInput(),random));

            return studentDTO;
        }

    }

    public static boolean chek(String verifyInput,String random){

        //从session中获取随机数
        String inputStr = verifyInput;
        //String random = (String) request.getSession().getAttribute("RANDOMVALIDATECODEKEY");
        if (random == null) {
            return false;
        }
        if (random.equals(inputStr)) {
            return true;
        } else {
            return false;
        }

    }
    public boolean checkname(String name)
    {
        int n = 0;
        for(int i = 0; i < name.length(); i++) {
            n = (int)name.charAt(i);
            if(!(19968 <= n && n <40869)) {
                return false;
            }
        }
        return true;
    }
}
