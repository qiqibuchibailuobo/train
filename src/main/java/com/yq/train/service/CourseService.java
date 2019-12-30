package com.yq.train.service;

import com.yq.train.dto.CourseDTO;
import com.yq.train.dto.CourseQueryDTO;
import com.yq.train.dto.PaginationDTO;
import com.yq.train.dto.UpdateCourseDTO;
import com.yq.train.exception.CustomizeErrorCode;
import com.yq.train.exception.CustomizeException;
import com.yq.train.mapper.CourseExtMapper;
import com.yq.train.mapper.CourseMapper;
import com.yq.train.mapper.TeacherMapper;
import com.yq.train.model.Course;
import com.yq.train.model.CourseExample;
import com.yq.train.model.Student;
import com.yq.train.model.Teacher;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
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
        if (totalCount != 0){
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
                BeanUtils.copyProperties(course,courseDTO);

                courseDTO.setTeacher(teacher);
                courseDTOList.add(courseDTO);
            }
            paginationDTO.setData(courseDTOList);
        }else {
            paginationDTO.setData(null);
        }

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

    public void updateCourseImg(MultipartFile file, Course course, Teacher teacher, CourseExample courseExample, Course course1,String originalFilename) throws IOException {

        /**
         * 上传图片
         */
        //图片上传成功后，将图片的地址写到数据库
        //保存图片的路径（这是存在我项目中的images下了，你们可以设置路径）
        String filePath = "D:\\MyGitHub\\images";
        //new File("D:\\MyGitHub\\train\\src\\main\\resources\\static"+student.getHeadportraitUrl()).delete();
        //获取原始图片的拓展名

        String imageName[] = originalFilename.split("\\.");
        //新的文件名字
        String newFileName = teacher.getiName() + course1.getCourseDescribe() + "." + imageName[1];
        //封装上传文件位置的全路径
        File targetFile = new File(filePath, newFileName);
        //把本地文件上传到封装上传文件位置的全路径
        file.transferTo(targetFile);
        //productModel.setImage(newFileName);
        course.setHeadportraitUrl(newFileName);
        Date date = new Date();
        teacher.setGmtModified(date);
        courseMapper.updateByExampleSelective(course,courseExample);

    }
    public void updateCourse(UpdateCourseDTO updateCourseDTO,Course course ,CourseExample courseExample){
        if (updateCourseDTO.getCourseDescribe().equals("")||updateCourseDTO.getCourseDescribe() == null){
            updateCourseDTO.setType(1);
        }else {
            if(updateCourseDTO.getPrice() == null||updateCourseDTO.getPrice().equals("")){
                updateCourseDTO.setType(4);
            }else {
                if (!isNumeric(updateCourseDTO.getPrice())){
                    updateCourseDTO.setType(2);
                }else {
                    if (updateCourseDTO.getClassTime().equals("")||updateCourseDTO.getClassTime() == null){
                        course.setClassTime("时间未定");
                        course.setPrice(updateCourseDTO.getPrice());
                        course.setCourseDescribe(updateCourseDTO.getCourseDescribe());
                        Date date = new Date();
                        course.setGmtModified(date);
                        courseMapper.updateByExampleSelective(course,courseExample);
                        updateCourseDTO.setType(3);
                    }else {

                        course.setPrice(updateCourseDTO.getPrice());
                        course.setCourseDescribe(updateCourseDTO.getCourseDescribe());
                        course.setClassTime(updateCourseDTO.getClassTime());
                        Date date = new Date();
                        course.setGmtModified(date);
                        courseMapper.updateByExampleSelective(course,courseExample);
                        updateCourseDTO.setType(3);

                    }

                }
            }
        }
    }
    public static boolean isNumeric(String str){
        for (int i = str.length();--i>=0;){
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }
}
