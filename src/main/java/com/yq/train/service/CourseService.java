package com.yq.train.service;

import com.yq.train.dto.CourseDTO;
import com.yq.train.dto.CourseQueryDTO;
import com.yq.train.dto.PaginationDTO;
import com.yq.train.exception.CustomizeErrorCode;
import com.yq.train.exception.CustomizeException;
import com.yq.train.mapper.CourseExtMapper;
import com.yq.train.mapper.CourseMapper;
import com.yq.train.mapper.TeacherMapper;
import com.yq.train.model.Course;
import com.yq.train.model.CourseExample;
import com.yq.train.model.Teacher;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import java.util.stream.Collectors;

@Service
public class CourseService {
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private CourseExtMapper courseExtMapper;
    @Autowired
    private CourseMapper courseMapper;
    /**
     * 主页返回所有课程
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
        CourseQueryDTO courseQueryDTO = new CourseQueryDTO();

        courseQueryDTO.setSearch(search);
        //Integer totalCount = questionExtMapper.countBySearch(questionQueryDTO);

        Integer totalCount = courseExtMapper.countBySearch(courseQueryDTO);

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
        courseExample.setOrderByClause("gmt_create desc");
        courseQueryDTO.setSize(size);

        courseQueryDTO.setPage(offset);
        List<Course> courses = courseExtMapper.selectBySearch(courseQueryDTO);

        List<CourseDTO> courseDTOList = new ArrayList<>();

        for (Course course : courses) {
         //   System.out.println(course.getTeachingId());

            Teacher teacher = teacherMapper.selectByPrimaryKey(course.getTeachingId());
            CourseDTO courseDTO = new CourseDTO();
            BeanUtils.copyProperties(course,courseDTO);//将question的属性copy到questionDTO上
            courseDTO.setTeacher(teacher);

            courseDTOList.add(courseDTO);
        }
        paginationDTO.setData(courseDTOList);
        return paginationDTO;
    }

    public CourseDTO getById(int id) {
        Course course = courseMapper.selectByPrimaryKey(id);
        if (course == null){
            throw new CustomizeException(CustomizeErrorCode.COURSE_NOT_FOUND);
        }
        CourseDTO courseDTO = new CourseDTO();
        BeanUtils.copyProperties(course,courseDTO);
        Teacher teacher = teacherMapper.selectByPrimaryKey(course.getTeachingId());
        courseDTO.setTeacher(teacher);

        return courseDTO;
    }
}
