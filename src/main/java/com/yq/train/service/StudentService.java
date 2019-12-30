package com.yq.train.service;

import com.yq.train.dto.*;
import com.yq.train.exception.CustomizeErrorCode;
import com.yq.train.exception.CustomizeException;
import com.yq.train.mapper.*;
import com.yq.train.model.*;
import com.yq.train.tool.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.*;
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
    /**
     * 返回所有未选课学生
     * @param search
     * @param page
     * @param size
     * @return
     */
    public PaginationDTO AllStudents(String search, Integer page, Integer size) {

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
        teacherStudentDTO.setCourseId(0);

        Integer totalCount = classInfoExtMapper.AllStudentCountBySearch(teacherStudentDTO);
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
            List<ClassInfo> classInfos = classInfoExtMapper.selectByAllStudentSearch(teacherStudentDTO);


            List<Student> students = new ArrayList<>();

            for (ClassInfo classInfo : classInfos) {
                Student student = studentMapper.selectByPrimaryKey(classInfo.getStudentId());
                students.add(student);
            }
            paginationDTO.setData(students);
        }else {
            paginationDTO.setData(null);
        }

        return paginationDTO;
    }

    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public Model batchImport(String fileName, MultipartFile file, Teacher teacher, Model model) throws Exception {

        boolean notNull = false;
        List<Student> studentList = new ArrayList<>();
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
        Student student;
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

            student = new Student();

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
            student.setiName(studentdepartments);
            student.setUserName(studentdepartments);
            MD5Utils md5Utils = new MD5Utils();
            student.setUserPwd(md5Utils.toMD5("123456"));
            try {
                int tel = Integer.parseInt(studentTel);
                student.setTel(tel);
            } catch (NumberFormatException e) {

                e.printStackTrace();

            }
            student.setAddTeacher(0);
            Date date = new Date();
            student.setGmtCreate(date);
            student.setGmtModified(date);
            student.setHeadportraitUrl("nice.jpg");
            student.setUserType(1);
            student.setAddTeacher(teacher.getId());


            studentList.add(student);
        }
        for (Student userResord : studentList) {

            StudentExample studentExample = new StudentExample();
            studentExample.createCriteria()
                    .andUserNameEqualTo(userResord.getiName());
            List<Student> allstudent = studentMapper.selectByExample(studentExample);

            int cnt = allstudent.size();  //根据学生学号查询是否有此条信息
            if (cnt == 0) {
                ClassInfo classInfo = new ClassInfo();

                classInfo.setCourseId(0);
                classInfo.setRemnantCourse(0);
                classInfo.setStatus(0);
                classInfo.setTeacherId(0);

                studentMapper.insert(userResord);
                studentExample.createCriteria()
                        .andUserNameEqualTo(userResord.getiName());
                List<Student> allstudents = studentMapper.selectByExample(studentExample);
                Student student1 = allstudents.get(0);
                classInfo.setStudentId(student1.getId());
                classInfoMapper.insert(classInfo);  //如果没有此条信息则插入操作
                System.out.println(" 插入 "+userResord);
            } else {
                studentMapper.updateByPrimaryKeySelective(userResord); //如果有此条信息则更新操作
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
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public Model adminbatchImport(String fileName, MultipartFile file,Model model) throws Exception {

        boolean notNull = false;
        List<Student> studentList = new ArrayList<>();
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
        Student student;
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

             student = new Student();

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
            student.setiName(studentdepartments);
            student.setUserName(studentdepartments);
            MD5Utils md5Utils = new MD5Utils();
            student.setUserPwd(md5Utils.toMD5("123456"));
            try {
                int tel = Integer.parseInt(studentTel);
                student.setTel(tel);
            } catch (NumberFormatException e) {

                e.printStackTrace();

            }
            student.setAddTeacher(0);
            Date date = new Date();
            student.setGmtCreate(date);
            student.setGmtModified(date);
            student.setHeadportraitUrl("nice.jpg");
            student.setUserType(1);
            student.setAddTeacher(0);


            studentList.add(student);
        }
        for (Student userResord : studentList) {

            StudentExample studentExample = new StudentExample();
            studentExample.createCriteria()
                    .andUserNameEqualTo(userResord.getiName());
            List<Student> allstudent = studentMapper.selectByExample(studentExample);

            int cnt = allstudent.size();  //根据学生学号查询是否有此条信息
            if (cnt == 0) {
                ClassInfo classInfo = new ClassInfo();

                classInfo.setCourseId(0);
                classInfo.setRemnantCourse(0);
                classInfo.setStatus(0);
                classInfo.setTeacherId(0);

                studentMapper.insert(userResord);
                studentExample.createCriteria()
                        .andUserNameEqualTo(userResord.getiName());
                List<Student> allstudents = studentMapper.selectByExample(studentExample);
                Student student1 = allstudents.get(0);
                classInfo.setStudentId(student1.getId());
                classInfoMapper.insert(classInfo);  //如果没有此条信息则插入操作
                System.out.println(" 插入 "+userResord);
            } else {
                studentMapper.updateByPrimaryKeySelective(userResord); //如果有此条信息则更新操作
                System.out.println(" 更新 "+userResord);

            }
        }
        return model;
    }
}
