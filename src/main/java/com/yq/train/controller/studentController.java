package com.yq.train.controller;

import com.yq.train.dto.ClassInfoDTO;
import com.yq.train.dto.StudentDTO;
import com.yq.train.dto.UpdateStudentDTO;
import com.yq.train.dto.UserDTO;
import com.yq.train.exception.CustomizeErrorCode;
import com.yq.train.exception.CustomizeException;
import com.yq.train.mapper.ClassInfoMapper;
import com.yq.train.mapper.CourseMapper;
import com.yq.train.mapper.StudentMapper;
import com.yq.train.mapper.TeacherMapper;
import com.yq.train.model.*;
import com.yq.train.tool.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

        ClassInfoDTO classInfoDTO = new ClassInfoDTO();
        classInfoDTO.setStudentName(student.getiName());

        if(classInfo.getStatus() == 1){
            classInfoDTO.setStatus("已预约");
        }else {
            classInfoDTO.setStatus("未预约");
        }
        if(classInfo.getTeacherId() == 0){
            classInfoDTO.setTeacherName("未选课");
        }else {
            teacherExample.createCriteria()
                    .andIdEqualTo(classInfo.getTeacherId());
            List<Teacher> teachers = teacherMapper.selectByExample(teacherExample);
            Teacher teacher = teachers.get(0);
            classInfoDTO.setTeacherName(teacher.getiName());
        }
        if(classInfo.getCourseId() == 0){
            classInfoDTO.setCourseName("未选课");
            classInfoDTO.setClassTime("未选课");
        }else {
            teacherExample.createCriteria()
                    .andIdEqualTo(classInfo.getTeacherId());
            List<Teacher> teachers = teacherMapper.selectByExample(teacherExample);
            Teacher teacher = teachers.get(0);
            classInfoDTO.setTeacherName(teacher.getiName());
            courseExample.createCriteria()
                    .andTeachingIdEqualTo(teacher.getId());
            List<Course> courses = courseMapper.selectByExample(courseExample);
            Course course = courses.get(0);
            classInfoDTO.setCourseName(course.getCourseDescribe());
            classInfoDTO.setClassTime(course.getClassTime());
        }
        classInfoDTO.setRemnantCourse(classInfo.getRemnantCourse());

        model.addAttribute("classInfoDTO",classInfoDTO);
        model.addAttribute("student",student);
        return "student";
    }

    @RequestMapping(value = "/studentUpdateImage")
    public String  studentUpdateInfoImage(
                                    MultipartFile file,
                                    HttpServletRequest request) throws IOException {
        Student student = (Student)request.getSession().getAttribute("user");
        /**
         * 上传图片
         */
        //图片上传成功后，将图片的地址写到数据库
        //保存图片的路径（这是存在我项目中的images下了，你们可以设置路径）
        String filePath = "D:\\MyGitHub\\images";
        //new File("D:\\MyGitHub\\train\\src\\main\\resources\\static"+student.getHeadportraitUrl()).delete();
        //获取原始图片的拓展名
        String originalFilename = file.getOriginalFilename();
        String imageName[] = originalFilename.split("\\.");
        //新的文件名字
        String newFileName = student.getUserName() + "." + imageName[1];
        //封装上传文件位置的全路径
        File targetFile = new File(filePath, newFileName);
        //把本地文件上传到封装上传文件位置的全路径
        file.transferTo(targetFile);
        //productModel.setImage(newFileName);
        student.setHeadportraitUrl(newFileName);
        Date date = new Date();
        student.setGmtModified(date);
        StudentExample studentExample = new StudentExample();
        studentExample.createCriteria()
                .andIdEqualTo(student.getId());
        studentMapper.updateByExampleSelective(student,studentExample);

        return "redirect:/student";
    }
//    @RequestMapping("unit/bill/showeinvoice")
//    @ResponseBody
//    public void showEInvoice(HttpServletRequest request,       HttpServletResponse response){
//        FileInputStream fis = null;
//        OutputStream os = null;
//        Student student = (Student)request.getSession().getAttribute("user");
//        String filepath = student.getHeadportraitUrl();     //path是你服务器上图片的绝对路径
//        File file = new File(filepath);
//        if(file.exists()){
//            try {
//                fis = new FileInputStream(file);
//                long size = file.length();
//                byte[] temp = new byte[(int) size];
//                fis.read(temp, 0, (int) size);
//                fis.close();
//                byte[] data = temp;
//                response.setContentType("image/png");
//                os = response.getOutputStream();
//                os.write(data);
//                os.flush();
//                os.close();
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

    @PostMapping("/studentUpdateInfo")
    @ResponseBody
    public Object studentUpdateInfo(@RequestBody UpdateStudentDTO updateStudentDTO,HttpServletRequest request) throws IOException {
       Student student = (Student)request.getSession().getAttribute("user");
       if(updateStudentDTO.getIname()==null){
           updateStudentDTO.setType(0);
       }else {
           if(!checkname(updateStudentDTO.getIname())){
               updateStudentDTO.setType(2);
           }else {
               student.setiName(updateStudentDTO.getIname());
//           student.setiName(updateStudentDTO.getName());
               student.setTel(updateStudentDTO.getTel());
               Date date = new Date();
               student.setGmtModified(date);
               StudentExample studentExample = new StudentExample();
               studentExample.createCriteria()
                       .andIdEqualTo(student.getId());
               studentMapper.updateByExampleSelective(student,studentExample);
               updateStudentDTO.setType(1);
           }

       }

        return updateStudentDTO;
    }
    @PostMapping("/studentUpdateInfoPwd")
    @ResponseBody
    public Object studentUpdateInfoPwd(@RequestBody UpdateStudentDTO updateStudentDTO,HttpServletRequest request) throws IOException, NoSuchAlgorithmException {
        Student student = (Student)request.getSession().getAttribute("user");
        if(updateStudentDTO.getUserPwd().equals("")||updateStudentDTO.getUserPwd2().equals("")&&updateStudentDTO.getUserPwd3().equals("")){
            updateStudentDTO.setType(0);
        }else {
            if(!student.getUserPwd().equals(updateStudentDTO.getUserPwd())){
                updateStudentDTO.setType(1);
            }else {
                if(!updateStudentDTO.getUserPwd2().equals(updateStudentDTO.getUserPwd3())){
                    updateStudentDTO.setType(2);
                }else {
                    MD5Utils md5Utils = new MD5Utils();

                    student.setUserPwd(md5Utils.toMD5(updateStudentDTO.getUserPwd2()));
                    Date date = new Date();
                    student.setGmtModified(date);
                    StudentExample studentExample = new StudentExample();
                    studentExample.createCriteria()
                            .andIdEqualTo(student.getId());
                    studentMapper.updateByExampleSelective(student,studentExample);
                    updateStudentDTO.setType(3);
                }
            }
        }
        return updateStudentDTO;
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
