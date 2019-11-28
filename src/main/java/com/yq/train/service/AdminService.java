package com.yq.train.service;

import com.yq.train.dto.*;
import com.yq.train.exception.CustomizeErrorCode;
import com.yq.train.exception.CustomizeException;
import com.yq.train.mapper.*;
import com.yq.train.model.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private StudentExtMapper studentExtMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private ClassInfoMapper classInfoMapper;
    @Autowired
    private CourseMapper courseMapper;
    /**
     * 管理员返回所有学生
     * @param page
     * @param size
     * @return
     */
    public PaginationDTO list(String search, Integer page, Integer size) {

        if(StringUtils.isNotBlank(search)){
            String [] tags = StringUtils.split(search," ");
            search = Arrays.stream(tags).collect(Collectors.joining("|"));
        }


        PaginationDTO paginationDTO = new PaginationDTO();

        Integer totalPage;

        //QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
       // CourseQueryDTO courseQueryDTO = new CourseQueryDTO();
        StudentQueryDTO studentQueryDTO = new StudentQueryDTO();
        studentQueryDTO.setSearch(search);
        //Integer totalCount = questionExtMapper.countBySearch(questionQueryDTO);

        Integer totalCount = studentExtMapper.countBySearch(studentQueryDTO);

        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage, page);

        //分页size*(page-1)
        Integer offset = size * (page - 1);
        //List<Question> questions = questionMapper.list(offset,size);
        StudentExample studentExample = new StudentExample();
//        studentExample.setOrderByClause("gmt_create desc");

//        CourseExample courseExample = new CourseExample();
//        courseExample.setOrderByClause("gmt_create desc");
        studentQueryDTO.setSize(size);

        studentQueryDTO.setPage(offset);
        List<Student> students = studentExtMapper.selectBySearch(studentQueryDTO);

        List<AllStudentsDTO> allStudentsDTOS = new ArrayList<>();

        for (Student student : students) {
            //   System.out.println(course.getTeachingId());

            //Teacher teacher = teacherMapper.selectByPrimaryKey(course.getTeachingId());
            Admin admin = adminMapper.selectByPrimaryKey(1);
            AllStudentsDTO allStudentsDTO = new AllStudentsDTO();
            BeanUtils.copyProperties(student,allStudentsDTO);

            allStudentsDTO.setAdmin(admin);
            allStudentsDTO.setStudent(student);
            allStudentsDTOS.add(allStudentsDTO);
        }
        paginationDTO.setData(allStudentsDTOS);
        return paginationDTO;
    }
    public StudentInfoDTO getById(int id) {
        Student student = studentMapper.selectByPrimaryKey(id);
        ClassInfo classInfo = classInfoMapper.selectByPrimaryKey(id);
        if (student == null){
            throw new CustomizeException(CustomizeErrorCode.NOT_STUDENT);
        }
        ClassInfoDTO classInfoDTO = new ClassInfoDTO();
        classInfoDTO.setStudentName(student.getiName());
        if (classInfo.getCourseId()==0){
            classInfoDTO.setCourseName("未选课");
            classInfoDTO.setClassTime("未选课");
            classInfoDTO.setRemnantCourse(0);
        }else {
            Course course = courseMapper.selectByPrimaryKey(classInfo.getCourseId());
            classInfoDTO.setCourseName(course.getCourseDescribe());
            classInfoDTO.setClassTime(course.getClassTime());
            classInfoDTO.setRemnantCourse(classInfo.getRemnantCourse());
        }
        if(classInfo.getTeacherId() == 0){
            classInfoDTO.setTeacherName("未选课");
        }else {
            Teacher teacher = teacherMapper.selectByPrimaryKey(classInfo.getTeacherId());
            classInfoDTO.setTeacherName(teacher.getiName());
        }
        if(classInfo.getStatus() == 0){
            classInfoDTO.setStatus("未预约");
        }else {
            classInfoDTO.setStatus("已预约");
        }
        StudentInfoDTO studentInfoDTO = new StudentInfoDTO();
        studentInfoDTO.setStudent(student);
        studentInfoDTO.setClassInfoDTO(classInfoDTO);

        return studentInfoDTO;
    }
}
