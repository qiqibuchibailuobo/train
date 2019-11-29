package com.yq.train.service;

import com.yq.train.dto.CourseDTO;
import com.yq.train.dto.CourseQueryDTO;
import com.yq.train.dto.PaginationDTO;
import com.yq.train.dto.TeacherCourseDTO;
import com.yq.train.exception.CustomizeErrorCode;
import com.yq.train.exception.CustomizeException;
import com.yq.train.mapper.CourseExtMapper;
import com.yq.train.mapper.CourseMapper;
import com.yq.train.mapper.TeacherMapper;
import com.yq.train.model.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private CourseExtMapper courseExtMapper;
    @Autowired
    private CourseMapper courseMapper;

    /**
     * 返回教师所有课程
     * @param search
     * @param page
     * @param size
     * @return
     */
    public PaginationDTO list(String search, Integer page, Integer size,Integer teachingId) {

        if(StringUtils.isNotBlank(search)){
            String [] tags = StringUtils.split(search," ");
            search = Arrays.stream(tags).collect(Collectors.joining("|"));
        }


        PaginationDTO paginationDTO = new PaginationDTO();

        Integer totalPage;

        //QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        //CourseQueryDTO courseQueryDTO = new CourseQueryDTO();
        TeacherCourseDTO teacherCourseDTO = new TeacherCourseDTO();
        teacherCourseDTO.setSearch(search);
        teacherCourseDTO.setTeachingId(teachingId);
        //courseQueryDTO.setSearch(search);
        //Integer totalCount = questionExtMapper.countBySearch(questionQueryDTO);

        Integer totalCount = courseExtMapper.teacherCourseCountBySearch(teacherCourseDTO);

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
        teacherCourseDTO.setSize(size);
        teacherCourseDTO.setPage(offset);
        //courseQueryDTO.setSize(size);

        //courseQueryDTO.setPage(offset);
        CourseExample courseExample1 = new CourseExample();
        courseExample1.createCriteria()
                .andTeachingIdEqualTo(teachingId);
        List<Course> courses1 = courseMapper.selectByExample(courseExample1);

        if(courses1.size()>0){
            List<Course> courses = courseExtMapper.selectByTeacherCourseSearch(teacherCourseDTO);
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
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public Model adminbatchImport(String fileName, MultipartFile file,Model model) throws Exception {

        boolean notNull = false;
        List<Teacher> teachers = new ArrayList<>();
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            model.addAttribute("msg","文件格式不正确");
            throw new CustomizeException(CustomizeErrorCode.NOT_EXCEL);
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream is = file.getInputStream();
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        if(sheet!=null){
            notNull = true;
        }
        Teacher teacher;
        int num = sheet.getLastRowNum();
        //System.out.println(num);
        //总列数
        int col = sheet.getRow(0).getLastCellNum();
        for (int r = 1; r <= num; r++) {//r = 2 表示从第三行开始循环 如果你的第三行开始是数据
            Row row = sheet.getRow(r);//通过sheet表单对象得到 行对象
            if (row == null){
                continue;
            }

            //sheet.getLastRowNum() 的值是 10，所以Excel表中的数据至少是10条；不然报错 NullPointerException

             teacher = new Teacher();

            if( row.getCell(0).getCellType() !=1){//循环时，得到每一行的单元格进行判断
                model.addAttribute("msg","文件格式不正确");
                throw new CustomizeException(CustomizeErrorCode.NOT_FORMAT);
            }

            String studentdepartments = row.getCell(0).getStringCellValue();//得到每一行第一个单元格的值

            if(studentdepartments == null || studentdepartments.isEmpty()|| !checkStudentName(studentdepartments)){//判断是否为空
                model.addAttribute("msg","输入正确姓名");
                throw new CustomizeException(CustomizeErrorCode.NOT_Chinese);
            }

            row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);//得到每一行的 第二个单元格的值

            String studentTel = row.getCell(1).getStringCellValue();

            if(studentTel==null || studentTel.isEmpty()||!isNumeric(studentTel)){
                model.addAttribute("msg","输入正确电话");
                throw new CustomizeException(CustomizeErrorCode.NOT_NUMBER);

            }

            //完整的循环一次 就组成了一个对象
            teacher.setiName(studentdepartments);
            teacher.setUserName(studentdepartments);
            teacher.setUserPwd("123456");
            try {
                int tel = Integer.parseInt(studentTel);
                teacher.setTel(tel);
            } catch (NumberFormatException e) {

                e.printStackTrace();

            }
            teacher.setTeacherDescribe("这个人很懒，什么都没有留下。");
            Date date = new Date();
            teacher.setGmtCreate(date);
            teacher.setGmtModified(date);
            teacher.setCourseTotal(0);
            teacher.setHeadportraitUrl("nice.jpg");
            teacher.setUserType(2);

            teachers.add(teacher);
        }
        for (Teacher userResord : teachers) {

            TeacherExample teacherExample = new TeacherExample();
            teacherExample.createCriteria()
                    .andUserNameEqualTo(userResord.getiName());
            List<Teacher> allTeacher = teacherMapper.selectByExample(teacherExample);

            int cnt = allTeacher.size();  //根据学生学号查询是否有此条信息
            if (cnt == 0) {
                teacherMapper.insert(userResord);
                System.out.println(" 插入 "+userResord);
            } else {
                teacherMapper.updateByPrimaryKeySelective(userResord); //如果有此条信息则更新操作
                System.out.println(" 更新 "+userResord);

            }
        }
        return model;
    }
    public static boolean isNumeric(String str){
        for (int i = str.length();--i>=0;){
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }
    public boolean checkStudentName(String name)
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
