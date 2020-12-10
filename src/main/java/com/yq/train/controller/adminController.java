package com.yq.train.controller;

import com.yq.train.dto.*;
import com.yq.train.exception.CustomizeErrorCode;
import com.yq.train.exception.CustomizeException;
import com.yq.train.mapper.ClassInfoMapper;
import com.yq.train.mapper.CourseMapper;
import com.yq.train.mapper.StudentMapper;
import com.yq.train.mapper.TeacherMapper;
import com.yq.train.model.*;
import com.yq.train.service.AdminService;
import com.yq.train.service.CourseService;
import com.yq.train.service.TeacherService;
import com.yq.train.tool.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
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
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private TeacherService teacherService;

    @GetMapping("/admin")
    public String loginAdmin(Model model,
                             @RequestParam(name = "page", defaultValue = "1") Integer page,
                             @RequestParam(name = "size", defaultValue = "6") Integer size,
                             @RequestParam(name = "search", required = false) String search) {
        if (search == "") {
            search = null;
        }
        PaginationDTO pagination = courseService.list(search, page, size);
        model.addAttribute("pagination", pagination);
        model.addAttribute("search", search);
        return "admin";
    }

    @GetMapping("/allStudents/{userType}")
    public String allStudents(
            HttpServletRequest request,
            @PathVariable(value = "userType") int userType,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "6") Integer size,
            @RequestParam(name = "search", required = false) String search,
            Model model) {
        if (userType == 0) {
            if (search == "") {
                search = null;
            }
            PaginationDTO pagination = adminService.list(search, page, size);
            model.addAttribute("pagination", pagination);
            model.addAttribute("search", search);
            return "adminStudents";
        } else {
            model.addAttribute("msg", "您无权限！");
            return "msg";
        }
    }

    @GetMapping("/adminStudent/{id}")
    public String course(
            @PathVariable(name = "id") int id,
            Model model) {
        StudentInfoDTO studentInfoDTO = adminService.getById(id);
        model.addAttribute("student", studentInfoDTO);
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
        for (int i = 0; i < delList.size(); i++) {
            Integer studentId = delList.get(i);

            ClassInfo classInfo = classInfoMapper.selectByPrimaryKey(studentId);
            if (classInfo.getCourseId() != 0) {
                Course course = courseMapper.selectByPrimaryKey(classInfo.getCourseId());
                int a = course.getStudentCount();
                course.setStudentCount(a - 1);
                Date date = new Date();
                course.setGmtModified(date);
                courseMapper.updateByPrimaryKeySelective(course);
            }


            studentMapper.deleteByPrimaryKey(studentId);
            classInfoMapper.deleteByPrimaryKey(studentId);


        }
        return deleteStudentDTO;
    }

    /**
     * 管理员修改学生信息
     * @param studentDTO
     * @return
     */
    @PostMapping("/adminStudentUpdateInfo")
    @ResponseBody
    public Object adminStudentUpdateInfo(@RequestBody StudentDTO studentDTO){
        Student student = studentMapper.selectByPrimaryKey(studentDTO.getId());
        student.setiName(studentDTO.getIname());
        student.setTel(studentDTO.getTel());
        Date date = new Date();
        student.setGmtModified(date);
        studentMapper.updateByPrimaryKeySelective(student);
        studentDTO.setType(1);
        return studentDTO;
    }

    /**
     * 管理员添加学生
     * @param studentDTO
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    @PostMapping(value = "/adminAddStudent")
    @ResponseBody
    public Object adminAddStudent(@RequestBody StudentDTO studentDTO) throws IOException, NoSuchAlgorithmException {
        Student student = new Student();
        if (studentDTO.getIname().equals("") || studentDTO.getIname() == null) {
            studentDTO.setType(1);
        } else {
            StudentExample studentExample = new StudentExample();
            studentExample.createCriteria()
                    .andUserNameEqualTo(studentDTO.getIname());
            List<Student> allstudent = studentMapper.selectByExample(studentExample);
            if (allstudent.size() > 0) {
                studentDTO.setType(2);
            } else {
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

    /**
     * 管理员页面显示所有教师
     * @param request
     * @param userType
     * @param page
     * @param size
     * @param search
     * @param model
     * @return
     */
    @GetMapping("/allTeachers/{userType}")
    public String allTeachers(
            HttpServletRequest request,
            @PathVariable(value = "userType") int userType,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "6") Integer size,
            @RequestParam(name = "search", required = false) String search,
            Model model) {

        if (userType == 0) {
            if (search == "") {
                search = null;
            }
            PaginationDTO pagination = adminService.listByTeacher(search, page, size);
            model.addAttribute("pagination", pagination);
            model.addAttribute("search", search);
            return "adminTeachers";
        } else {
            model.addAttribute("msg", "您无权限！");
            return "msg";
        }
    }

    /**
     * 批量移除教师
     */
    @PostMapping("/deleteTeacher")
    @ResponseBody
    public Object deleteTeacher(@RequestBody DeleteStudentDTO deleteStudentDTO) {
        String[] strs = deleteStudentDTO.getIds().split(",");
        List<Integer> delList = new ArrayList<>();
        for (String str : strs) {
            delList.add(Integer.parseInt(str));
        }
        for (int i = 0; i < delList.size(); i++) {
            Integer teacherId = delList.get(i);
            CourseExample courseExample = new CourseExample();
            courseExample.createCriteria()
                    .andTeachingIdEqualTo(teacherId);
            List<Course> courses = courseMapper.selectByExample(courseExample);
            if (courses.size() > 0) {
                for (int j = 0; j < courses.size(); j++) {
                    ClassInfoExample classInfoExample = new ClassInfoExample();
                    classInfoExample.createCriteria()
                            .andCourseIdEqualTo(courses.get(j).getId());
                    List<ClassInfo> classInfos = classInfoMapper.selectByExample(classInfoExample);
                    if (classInfos.size() > 0) {
                        for (int k = 0; k < classInfos.size(); k++) {
                            ClassInfo classInfo = classInfoMapper.selectByPrimaryKey(classInfos.get(k).getStudentId());
                            classInfo.setTeacherId(0);
                            classInfo.setRemnantCourse(0);
                            classInfo.setCourseId(0);
                            classInfo.setStatus(0);
                            classInfoMapper.updateByPrimaryKeySelective(classInfo);
                        }
                    }
                    courseMapper.deleteByPrimaryKey(courses.get(j).getId());
                }
            }
            teacherMapper.deleteByPrimaryKey(teacherId);


        }
        return deleteStudentDTO;
    }

    /**
     * 管理员添加教师
     * @param studentDTO
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    @PostMapping(value = "/adminAddTeacher")
    @ResponseBody
    public Object adminAddTeacher(@RequestBody StudentDTO studentDTO) throws IOException, NoSuchAlgorithmException {
        Teacher teacher = new Teacher();
        if (studentDTO.getIname().equals("") || studentDTO.getIname() == null) {
            studentDTO.setType(1);
        } else {
            TeacherExample teacherExample = new TeacherExample();
            teacherExample.createCriteria()
                    .andUserNameEqualTo(studentDTO.getIname());
            List<Teacher> teachers = teacherMapper.selectByExample(teacherExample);
            if (teachers.size() > 0) {
                studentDTO.setType(2);
            } else {
                teacher.setiName(studentDTO.getIname());
                teacher.setUserName(studentDTO.getIname());
                MD5Utils md5Utils = new MD5Utils();
                teacher.setUserPwd(md5Utils.toMD5("123456"));
                teacher.setTel(studentDTO.getTel());
                teacher.setTeacherDescribe("这个人很懒，没有描述。");
                Date date = new Date();
                teacher.setGmtCreate(date);
                teacher.setGmtModified(date);
                teacher.setHeadportraitUrl("nice.jpg");
                teacher.setUserType(2);
                teacher.setCourseTotal(0);
                teacherMapper.insert(teacher);
                studentDTO.setType(0);
            }
        }

        return studentDTO;
    }

    /**
     * 管理员查看详细教师界面
     * @param model
     * @param request
     * @param id
     * @param page1
     * @param size1
     * @param search1
     * @return
     */
    @GetMapping("/adminTeachers/{id}")
    public String adminTeachers(Model model,
                                HttpServletRequest request,
                                @PathVariable(name = "id") int id,
                                @RequestParam(name = "page1", defaultValue = "1") Integer page1,
                                @RequestParam(name = "size1", defaultValue = "6") Integer size1,
                                @RequestParam(name = "search1", required = false) String search1) {
        if (search1 == "") {
            search1 = null;
        }
        Teacher teacher = teacherMapper.selectByPrimaryKey(id);
        PaginationDTO pagination = teacherService.list(search1, page1, size1, id);
        model.addAttribute("pagination1", pagination);
        model.addAttribute("search1", search1);
        model.addAttribute("teacher", teacher);
        return "adminTeacher";
    }

    /**
     * 管理员添加课程
     * @param file1
     * @param request
     * @param model
     * @param id
     * @param courseDescribe
     * @param price
     * @param classTime
     * @param classHour
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/adminAddCourse")
    public String adminAddCourse(
            MultipartFile file1,
            HttpServletRequest request,
            Model model,
            @RequestParam(name = "teacherId") int id,
            @RequestParam(value = "courseDescribe") String courseDescribe,
            @RequestParam(value = "price") String price,
            @RequestParam(value = "classTime") String classTime,
            @RequestParam(value = "classHour") String classHour) throws IOException {

        Teacher teacher = teacherMapper.selectByPrimaryKey(id);
        CourseExample courseExample = new CourseExample();
        courseExample.createCriteria()
                .andCourseDescribeEqualTo(courseDescribe);
        List<Course> courses = courseMapper.selectByExample(courseExample);
        if (courses.size() <= 0) {
            if (courseDescribe == null || price == null || classTime == null) {
                model.addAttribute("msg", "课程信息不能为空");
                return "msg";
            } else {
                if (!isNumeric(price) || !isNumeric(classHour)) {
                    model.addAttribute("msg", "请输入数字！");
                    return "msg";
                } else {
                    try {
                        int classHour1 = Integer.parseInt(classHour);
                        String filePath = "D:\\MyGitHub\\images";
                        Course course = new Course();
                        course.setCourseDescribe(courseDescribe);
                        course.setPrice(price);
                        course.setClassTime(classTime);
                        course.setStudentCount(0);
                        course.setTeachingId(id);
                        course.setClassHour(classHour1);
                        String originalFilename = file1.getOriginalFilename();
                        if (originalFilename == null || originalFilename.equals("")) {
                            course.setHeadportraitUrl("1.png");
                        } else {
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

                        teacher.setCourseTotal(teacher.getCourseTotal() + 1);
                        teacherMapper.updateByPrimaryKeySelective(teacher);
                        model.addAttribute("msg", "课程添加成功");
                    } catch (Exception e) {
                        throw new CustomizeException(CustomizeErrorCode.NOT_NUMBER);
                    }


                }

            }
        } else {
            model.addAttribute("msg", "已有该课程！");
            return "msg";
        }
        return "redirect:/admin";
    }

    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 管理员修改教师信息
     * @param updateTeacherDTO
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping("/adminTeacherUpdateInfo")
    @ResponseBody
    public Object adminTeacherUpdateInfo(@RequestBody UpdateTeacherDTO updateTeacherDTO, HttpServletRequest request) throws IOException {
        Teacher teacher = teacherMapper.selectByPrimaryKey(updateTeacherDTO.getId());


        teacher.setiName(updateTeacherDTO.getIname());
//           student.setiName(updateStudentDTO.getName());
        teacher.setTel(updateTeacherDTO.getTel());
        teacher.setTeacherDescribe(updateTeacherDTO.getTeacherDescribe());
        Date date = new Date();
        teacher.setGmtModified(date);
        TeacherExample studentExample = new TeacherExample();
        studentExample.createCriteria()
                .andIdEqualTo(teacher.getId());
        teacherMapper.updateByExampleSelective(teacher, studentExample);
        updateTeacherDTO.setType(1);
        return updateTeacherDTO;
    }

    public boolean checkTeacherName(String name) {
        int n = 0;
        for (int i = 0; i < name.length(); i++) {
            n = (int) name.charAt(i);
            if (!(19968 <= n && n < 40869)) {
                return false;
            }
        }
        return true;
    }
}
