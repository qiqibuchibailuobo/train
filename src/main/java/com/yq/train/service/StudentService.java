package com.yq.train.service;

import com.yq.train.dto.*;
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
public class StudentService {
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private CourseExtMapper courseExtMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private ClassInfoMapper classInfoMapper;
    @Autowired
    private ClassInfoExtMapper classInfoExtMapper;
    @Autowired
    private StudentMapper studentMapper;
    /**
     * 返回教师所有学生
     * @param search
     * @param page
     * @param size
     * @return
     */
    public PaginationDTO list(String search, Integer page, Integer size, Integer courseId) {

        if(StringUtils.isNotBlank(search)){
            String [] tags = StringUtils.split(search," ");
            search = Arrays.stream(tags).collect(Collectors.joining("|"));
        }


        PaginationDTO paginationDTO = new PaginationDTO();

        Integer totalPage;

        //QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        //CourseQueryDTO courseQueryDTO = new CourseQueryDTO();
//        TeacherCourseDTO teacherCourseDTO = new TeacherCourseDTO();
//        teacherCourseDTO.setSearch(search);
//        teacherCourseDTO.setTeachingId(teachingId);
        //courseQueryDTO.setSearch(search);
        //Integer totalCount = questionExtMapper.countBySearch(questionQueryDTO);
        TeacherStudentDTO teacherStudentDTO = new TeacherStudentDTO();

        teacherStudentDTO.setSearch(search);
        teacherStudentDTO.setCourseId(courseId);

        Integer totalCount = classInfoExtMapper.teacherStudentCountBySearch(teacherStudentDTO);
        if(totalCount != 0){
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
            CourseExample courseExample = new CourseExample();
//        courseExample.setOrderByClause("gmt_create desc");
            teacherStudentDTO.setSize(size);
            teacherStudentDTO.setPage(offset);
            //courseQueryDTO.setSize(size);

            //courseQueryDTO.setPage(offset);
            List<ClassInfo> classInfos = classInfoExtMapper.selectByTeacherStudentSearch(teacherStudentDTO);


            List<ClassInfoDTO> classInfoDTOS = new ArrayList<>();

            for (ClassInfo classInfo : classInfos) {
                //   System.out.println(course.getTeachingId());

                Teacher teacher = teacherMapper.selectByPrimaryKey(classInfo.getTeacherId());
                Student student = studentMapper.selectByPrimaryKey(classInfo.getStudentId());
                Course course = courseMapper.selectByPrimaryKey(classInfo.getCourseId());
                ClassInfoDTO classInfoDTO = new ClassInfoDTO();
//            BeanUtils.copyProperties(classInfo,classInfoDTO);
                classInfoDTO.setCourseId(course.getId());
                classInfoDTO.setStudentId(student.getId());
                classInfoDTO.setTeacherName(teacher.getiName());
                classInfoDTO.setStudentName(student.getiName());
                classInfoDTO.setCourseName(course.getCourseDescribe());
                classInfoDTO.setRemnantCourse(classInfo.getRemnantCourse());
                classInfoDTO.setClassTime(course.getClassTime());
                if(classInfo.getStatus() == 1){
                    classInfoDTO.setStatus("已预约");
                }else {
                    classInfoDTO.setStatus("未预约");
                }
//            courseDTO.setTeacher(teacher);
                classInfoDTOS.add(classInfoDTO);
            }
            paginationDTO.setData(classInfoDTOS);
        }else {
            paginationDTO.setData(null);
        }

        return paginationDTO;
    }
}
