package com.yq.train.controller;

import com.yq.train.dto.*;
import com.yq.train.exception.CustomizeErrorCode;
import com.yq.train.exception.CustomizeException;
import com.yq.train.mapper.ClassInfoMapper;
import com.yq.train.mapper.CourseMapper;
import com.yq.train.mapper.StudentMapper;
import com.yq.train.mapper.TeacherMapper;
import com.yq.train.model.*;
import com.yq.train.service.CourseService;
import com.yq.train.service.TeacherService;
import com.yq.train.tool.MD5Utils;
import org.apache.poi.ss.formula.functions.T;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class teacherController {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private CourseService courseService;
    @Autowired
    private ClassInfoMapper classInfoMapper;

    /**
     * 教师登录主界面
     * @param model
     * @param request
     * @param page1
     * @param size1
     * @param search1
     * @return
     */
    @GetMapping("/teacher")
    public String loginTeacher(Model model,
                               HttpServletRequest request,
                               @RequestParam(name = "page1",defaultValue = "1") Integer page1,
                               @RequestParam(name = "size1",defaultValue = "6") Integer size1 ,
                               @RequestParam(name = "search1",required = false) String  search1 ){
        if(search1 == ""){
            search1 = null;
        }
        Teacher teacher = (Teacher)request.getSession().getAttribute("user");
        PaginationDTO pagination = teacherService.list(search1,page1,size1,teacher.getId());
        model.addAttribute("pagination1",pagination);
        model.addAttribute("search1",search1);
        return "teacher";
    }

    /**
     * 教师更新头像
     * @param file
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/teacherUpdateImage")
    public String  teacherUpdateInfoImage(
            MultipartFile file,
            HttpServletRequest request) throws IOException {

        Teacher teacher = (Teacher)request.getSession().getAttribute("user");
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
        String newFileName = teacher.getUserName() + "." + imageName[1];
        //封装上传文件位置的全路径
        File targetFile = new File(filePath, newFileName);
        //把本地文件上传到封装上传文件位置的全路径
        file.transferTo(targetFile);
        //productModel.setImage(newFileName);
        teacher.setHeadportraitUrl(newFileName);
        Date date = new Date();
        teacher.setGmtModified(date);
        TeacherExample teacherExample = new TeacherExample();
        teacherExample.createCriteria()
                .andIdEqualTo(teacher.getId());
        teacherMapper.updateByExampleSelective(teacher,teacherExample);

        return "redirect:/teacher";
    }

    /**
     * 教师更新信息
     * @param updateTeacherDTO
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping("/teacherUpdateInfo")
    @ResponseBody
    public Object teacherUpdateInfo(@RequestBody UpdateTeacherDTO updateTeacherDTO, HttpServletRequest request) throws IOException {
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");
        if(updateTeacherDTO.getIname()==null){
            updateTeacherDTO.setType(0);
        }else {
            if(!checkTeacherName(updateTeacherDTO.getIname())){
                updateTeacherDTO.setType(2);
            }else {
                teacher.setiName(updateTeacherDTO.getIname());
//           student.setiName(updateStudentDTO.getName());
                teacher.setTel(updateTeacherDTO.getTel());
                teacher.setTeacherDescribe(updateTeacherDTO.getTeacherDescribe());
                Date date = new Date();
                teacher.setGmtModified(date);
                TeacherExample studentExample = new TeacherExample();
                studentExample.createCriteria()
                        .andIdEqualTo(teacher.getId());
                teacherMapper.updateByExampleSelective(teacher,studentExample);
                updateTeacherDTO.setType(1);
            }

        }

        return updateTeacherDTO;
    }
    public boolean checkTeacherName(String name)
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

    /**
     * 教师更新密码
     * @param updateTeacherDTO
     * @param request
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    @PostMapping("/teacherUpdateInfoPwd")
    @ResponseBody
    public Object teacherUpdateInfoPwd(@RequestBody UpdateTeacherDTO updateTeacherDTO,HttpServletRequest request) throws IOException, NoSuchAlgorithmException {
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");
       // MD5Utils md5Utils = new MD5Utils();
        if(updateTeacherDTO.getUserPwd().equals("")||updateTeacherDTO.getUserPwd2().equals("")&&updateTeacherDTO.getUserPwd3().equals("")){
            updateTeacherDTO.setType(0);
        }else {
            MD5Utils md5Utils = new MD5Utils();
            String a= md5Utils.toMD5(updateTeacherDTO.getUserPwd());
            if(!teacher.getUserPwd().equals(a)){
                updateTeacherDTO.setType(1);
            }else {
                if(!updateTeacherDTO.getUserPwd2().equals(updateTeacherDTO.getUserPwd3())){
                    updateTeacherDTO.setType(2);
                }else {
                    if(updateTeacherDTO.getUserPwd().length()<=4||updateTeacherDTO.getUserPwd().length()>10){
                        updateTeacherDTO.setType(4);
                    }else {
                       // MD5Utils md5Utils = new MD5Utils();
                        teacher.setUserPwd(md5Utils.toMD5(updateTeacherDTO.getUserPwd2()));
                        //teacher.setUserPwd(updateTeacherDTO.getUserPwd2());
                        Date date = new Date();
                        teacher.setGmtModified(date);
                        TeacherExample teacherExample = new TeacherExample();
                        teacherExample.createCriteria()
                                .andIdEqualTo(teacher.getId());
                        teacherMapper.updateByExampleSelective(teacher,teacherExample);
                        updateTeacherDTO.setType(3);
                    }

                }
            }
        }
        return updateTeacherDTO;
    }

    /**
     * 教师添加课程
     * @param file1
     * @param request
     * @param model
     * @param courseDescribe
     * @param price
     * @param classTime
     * @param classHour
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/addCourse" )
    public String  addCourse(
            MultipartFile file1,
            HttpServletRequest request,
            Model model,
            @RequestParam(value = "courseDescribe") String courseDescribe,
            @RequestParam(value = "price") String price,
            @RequestParam(value = "classTime") String classTime,
            @RequestParam(value = "classHour") String classHour) throws IOException {

        Teacher teacher = (Teacher)request.getSession().getAttribute("user");
        CourseExample courseExample = new CourseExample();
        courseExample.createCriteria()
                .andCourseDescribeEqualTo(courseDescribe);
        List<Course> courses = courseMapper.selectByExample(courseExample);
        if(courses.size() <= 0){
            if(courseDescribe == null || price == null || classTime == null){
                model.addAttribute("msg","课程信息不能为空");
                return "msg";
            }else {
                if(!isNumeric(price)||!isNumeric(classHour)){
                    model.addAttribute("msg","请输入数字！");
                    return "msg";
                }else {
                    try {
                        int classHour1 = Integer.parseInt(classHour);
                        String filePath = "D:\\MyGitHub\\images";
                        Course course = new Course();
                        course.setCourseDescribe(courseDescribe);
                        course.setPrice(price);
                        course.setClassTime(classTime);
                        course.setStudentCount(0);
                        course.setTeachingId(teacher.getId());
                        course.setClassHour(classHour1);
                        String originalFilename = file1.getOriginalFilename();
                        if(originalFilename == null || originalFilename.equals("")){
                            course.setHeadportraitUrl("1.png");
                        }else {
                            String imageName[] = originalFilename.split("\\.");
                            //新的文件名字
                            String newFileName = teacher.getiName() + course.getCourseDescribe() + "." + imageName[1];
                            //封装上传文件位置的全路径
                            File targetFile = new File(filePath, newFileName);
                            //把本地文件上传到封装上传文件位置的全路径
                            file1.transferTo(targetFile);
                            //productModel.setImage(newFileName);
                            course.setHeadportraitUrl(newFileName);
                        }

                        Date date = new Date();
                        course.setGmtCreate(date);
                        course.setGmtModified(date);
                        courseMapper.insert(course);
                        teacher.setCourseTotal(teacher.getCourseTotal()+1);
                        teacherMapper.updateByPrimaryKeySelective(teacher);
                        model.addAttribute("msg","课程添加成功");
                    } catch (Exception e){
                        throw new CustomizeException(CustomizeErrorCode.NOT_NUMBER);
                    }


                }

            }
        }else {
            model.addAttribute("msg","已有该课程！");
            return "msg";
        }
        return "redirect:/teacher";
    }
    public static boolean isNumeric(String str){
        for (int i = str.length();--i>=0;){
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }

    /**
     * 教师修改自己的课程
     * @param updateCourseDTO
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/updateCourse")
    @ResponseBody
    public Object updateCourse(@RequestBody UpdateCourseDTO updateCourseDTO, HttpServletRequest request) throws IOException {

        Course course = new Course();
        CourseExample courseExample = new CourseExample();
        courseExample.createCriteria()
                .andIdEqualTo(updateCourseDTO.getCourseId());
        if(updateCourseDTO.getUserType() == 0){
            courseService.updateCourse(updateCourseDTO,course,courseExample);

        }else {
            Teacher teacher = (Teacher) request.getSession().getAttribute("user");
            if(teacher.getId() == updateCourseDTO.getTeachingId()){
                courseService.updateCourse(updateCourseDTO,course,courseExample);
            }else {
                updateCourseDTO.setType(0);
            }
        }

        return updateCourseDTO;
    }

    /**
     * 教师修改自己课程的展示图
     * @param file
     * @param request
     * @param courseId
     * @param teachingId
     * @param userType
     * @param model
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/courseUpdateImage")
    public String  courseUpdateImage(
            MultipartFile file,
            HttpServletRequest request,
            @RequestParam(value = "courseId") Integer courseId,
            @RequestParam(value = "teachingId")Integer teachingId,
            @RequestParam(value = "userType")Integer userType,
            Model model) throws IOException {
        Course course = new Course();
        CourseExample courseExample = new CourseExample();
        courseExample.createCriteria()
                .andIdEqualTo(courseId);
        List<Course> courses = courseMapper.selectByExample(courseExample);
        Course course1 = courses.get(0);
        if(userType == 0){
            //Admin admin  = (Admin) request.getSession().getAttribute("user");
            TeacherExample teacherExample = new TeacherExample();
            teacherExample.createCriteria()
                    .andIdEqualTo(teachingId);

            List<Teacher> teachers = teacherMapper.selectByExample(teacherExample);
            Teacher teacher = teachers.get(0);
            String originalFilename = file.getOriginalFilename();
            if(originalFilename == null || originalFilename.equals("")){
                model.addAttribute("msg","图片不能为空！");
                return "msg";
            }else {
                courseService.updateCourseImg(file,course,teacher,courseExample,course1,originalFilename);
                return "redirect:/admin";
            }
        }else {
            Teacher teacher = (Teacher) request.getSession().getAttribute("user");
            if(teacher.getId() == teachingId ){
                String originalFilename = file.getOriginalFilename();
                if(originalFilename == null || originalFilename.equals("")){
                    model.addAttribute("msg","图片不能为空！");
                    return "msg";
                }else {
                    courseService.updateCourseImg(file,course,teacher,courseExample,course1,originalFilename);
                }
            }else {
                model.addAttribute("msg","您无权限修改！");
                return "msg";

            }
            return "redirect:/teacher";
        }

       // return "redirect:/teacher";
    }

    /**
     * 教师删除自己的课程
     * @param updateCourseDTO
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/deleteCourse")
    @ResponseBody
    public Object deleteCourse(@RequestBody UpdateCourseDTO updateCourseDTO, HttpServletRequest request) throws IOException {

        CourseExample courseExample = new CourseExample();
        courseExample.createCriteria()
                .andIdEqualTo(updateCourseDTO.getCourseId());
        if(updateCourseDTO.getUserType() == 0){
            Course course = courseMapper.selectByPrimaryKey(updateCourseDTO.getCourseId());
            Teacher teacher = teacherMapper.selectByPrimaryKey(course.getTeachingId());
            int a = teacher.getCourseTotal();
            teacher.setCourseTotal(a-1);
            teacherMapper.updateByPrimaryKeySelective(teacher);
            courseMapper.deleteByExample(courseExample);
            ClassInfoExample classInfoExample = new ClassInfoExample();
            classInfoExample.createCriteria()
                    .andCourseIdEqualTo(updateCourseDTO.getCourseId());
            List<ClassInfo> classInfos = classInfoMapper.selectByExample(classInfoExample);
            for(int i = 0;i<classInfos.size();i++){
                ClassInfo classInfo = classInfos.get(i);
                classInfo.setCourseId(0);
                classInfo.setRemnantCourse(0);
                classInfo.setStatus(0);
                classInfo.setTeacherId(0);
                classInfoMapper.updateByPrimaryKeySelective(classInfo);
            }
            updateCourseDTO.setType(3);
        }else {
            Teacher teacher = (Teacher) request.getSession().getAttribute("user");
            if(teacher.getId() == updateCourseDTO.getTeachingId()){
                int a = teacher.getCourseTotal();
                teacher.setCourseTotal(a-1);
                teacherMapper.updateByPrimaryKeySelective(teacher);
                courseMapper.deleteByExample(courseExample);
                ClassInfoExample classInfoExample = new ClassInfoExample();
                classInfoExample.createCriteria()
                        .andCourseIdEqualTo(updateCourseDTO.getCourseId());
                List<ClassInfo> classInfos = classInfoMapper.selectByExample(classInfoExample);
                for(int i = 0;i<classInfos.size();i++){
                    ClassInfo classInfo = classInfos.get(i);
                    classInfo.setCourseId(0);
                    classInfo.setRemnantCourse(0);
                    classInfo.setStatus(0);
                    classInfo.setTeacherId(0);
                    classInfoMapper.updateByPrimaryKeySelective(classInfo);
                }
                updateCourseDTO.setType(1);
            }else {
                updateCourseDTO.setType(0);
            }
        }

        return updateCourseDTO;
    }

    /**
     * 教师添加自己的学生
     * @param studentDTO
     * @param request
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    @PostMapping(value = "/addStudent")
    @ResponseBody
    public Object addStudent(@RequestBody StudentDTO studentDTO, HttpServletRequest request) throws IOException, NoSuchAlgorithmException {
        Teacher teacher = (Teacher)request.getSession().getAttribute("user");
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
                MD5Utils md5Utils = new MD5Utils();
                student.setUserPwd(md5Utils.toMD5("123456"));
                student.setTel(studentDTO.getTel());
                student.setAddTeacher(0);
                Date date = new Date();
                student.setGmtCreate(date);
                student.setGmtModified(date);
                student.setHeadportraitUrl("nice.jpg");
                student.setUserType(1);
                student.setAddTeacher(teacher.getId());
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
