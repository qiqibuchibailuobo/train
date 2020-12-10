package com.yq.train.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yq.train.dto.PaginationDTO;
import com.yq.train.dto.UserDTO;
import com.yq.train.mapper.AdminMapper;
import com.yq.train.mapper.StudentMapper;
import com.yq.train.mapper.TeacherMapper;
import com.yq.train.model.*;
import com.yq.train.service.CourseService;
import com.yq.train.tool.MD5Utils;
import com.yq.train.tool.RandomValidateCodeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class loginController {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private CourseService courseService;
//    private final static Logger logger = LoggerFactory.getLogger(Picverifyaction.class);


    /**
     * 登录
     * @param userDTO
     * @param request
     * @param response
     * @param model
     * @param session
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
//    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @PostMapping("/login")
    @ResponseBody
    public Object post(@RequestBody UserDTO userDTO,
                       HttpServletRequest request,
                     HttpServletResponse response,
                       Model model,
                       HttpSession session) throws IOException, NoSuchAlgorithmException {
        //requestbody接收前端传来的json数据
        //responsebody将后台数据序列化成json传到前端
        //通过session获取当前用户
        userDTO.setUsername(userDTO.getUsername().replace(" ",""));
        userDTO.setPassword(userDTO.getPassword().replace(" ",""));
        StudentExample studentExample = new StudentExample();
        TeacherExample teacherExample = new TeacherExample();
        AdminExample adminExample = new AdminExample();
        MD5Utils md5Utils = new MD5Utils();


        if(userDTO.getUsername()!=null&&userDTO.getPassword()!=null){
            adminExample.createCriteria()
                    .andINameEqualTo(userDTO.getUsername())
                    .andAdminPwdEqualTo(md5Utils.toMD5(userDTO.getPassword()));
            List<Admin> admins = adminMapper.selectByExample(adminExample);

            studentExample.createCriteria()
                    .andUserNameEqualTo(userDTO.getUsername())
                    .andUserPwdEqualTo(md5Utils.toMD5(userDTO.getPassword()));
            List<Student> students = studentMapper.selectByExample(studentExample);

            teacherExample.createCriteria()
                    .andUserNameEqualTo(userDTO.getUsername())
                    .andUserPwdEqualTo(md5Utils.toMD5(userDTO.getPassword()));

            List<Teacher> teachers = teacherMapper.selectByExample(teacherExample);

            if(admins.size()>0){
                Admin admin = admins.get(0);
                userDTO.setMsg("管理员登录成功");
                userDTO.setType(0);
                request.getSession()
                        .setAttribute("user",admin);
                String random = (String) request.getSession().getAttribute("RANDOMVALIDATECODEKEY");
                userDTO.setVerificationCode(chek(userDTO.getVerifyInput(),random));
                //String json= JSON.toJSONString(userDTO);//关键
                return userDTO;
            }
            if(students.size()>0){
                Student student = students.get(0);
                userDTO.setMsg("学生登录成功");
                userDTO.setType(1);
                request.getSession()
                        .setAttribute("user",student);
                String random = (String) request.getSession().getAttribute("RANDOMVALIDATECODEKEY");
                userDTO.setVerificationCode(chek(userDTO.getVerifyInput(),random));
                return userDTO;
            }
            if(teachers.size()>0){
                Teacher teacher = teachers.get(0);
                userDTO.setMsg("教师登录成功");
                userDTO.setType(2);
                request.getSession()
                        .setAttribute("user",teacher);
                String random = (String) request.getSession().getAttribute("RANDOMVALIDATECODEKEY");
                userDTO.setVerificationCode(chek(userDTO.getVerifyInput(),random));
                return userDTO;
            }

            if(admins.size()<=0&&students.size()<=0&&teachers.size()<=0){
               // session.setAttribute("error","账号或密码错误");
                userDTO.setMsg("账号或密码错误");
                userDTO.setType(3);
                return userDTO;
            }

        }

        return null;
    }
    /**
     * 生成验证码
     */
    @RequestMapping(value = "/getVerify")
    public void getVerify(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
        try {
            randomValidateCode.getRandcode(request, response);//输出验证码图片方法
        } catch (Exception e) {
//            logger.error("获取验证码失败>>>>   ", e);
            e.printStackTrace();
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


    /**
     * 注销
     * @param request
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return "redirect:/";
    }


}
