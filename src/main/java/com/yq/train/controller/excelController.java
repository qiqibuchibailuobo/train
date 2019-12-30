package com.yq.train.controller;

import com.yq.train.exception.CustomizeErrorCode;
import com.yq.train.exception.CustomizeException;
import com.yq.train.mapper.ClassInfoMapper;
import com.yq.train.mapper.CourseMapper;
import com.yq.train.mapper.StudentMapper;
import com.yq.train.mapper.TeacherMapper;
import com.yq.train.model.*;
import com.yq.train.service.StudentService;
import com.yq.train.service.TeacherService;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
public class excelController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private ClassInfoMapper classInfoMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private TeacherService teacherService;
    @RequestMapping(value = "/teacherAddStudents", method = RequestMethod.POST)
    public Object exImport(MultipartFile excelFile, HttpServletRequest request, Model model) throws IOException {

        boolean a = false;
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");
        String fileName = excelFile.getOriginalFilename();

        try {
            model = studentService.batchImport(fileName, excelFile, teacher,model);

                return "redirect:/teacher";


        } catch (Exception e) {
            e.printStackTrace();
            if (model.getAttribute("msg")=="文件格式不正确"||model.getAttribute("msg").equals("文件格式不正确")) {
                model.addAttribute("message", "文件格式错误或文件数据为空！");
                return "error";
            }else if(model.getAttribute("msg")=="输入正确姓名"||model.getAttribute("msg").equals("输入正确姓名")){
                model.addAttribute("message", "核对表格，请输入正确姓名！");
                return "error";
            }else if(model.getAttribute("msg")=="输入正确电话"||model.getAttribute("msg").equals("输入正确电话")){
                model.addAttribute("message", "核对表格，请输入正确电话！");
                return "error";
            }else {
                return "error";
            }
        }
        //return "redirect:/teacher";   //这里需要修改，此处存在bug
    }
    @RequestMapping(value = "/adminAddStudents", method = RequestMethod.POST)
    public Object adminAddStudents(MultipartFile excelFile, Model model) throws IOException {

        boolean a = false;
        String fileName = excelFile.getOriginalFilename();

        try {
            model = studentService.adminbatchImport(fileName, excelFile, model);
            return "redirect:/admin";
        } catch (Exception e) {
            e.printStackTrace();
            if (model.getAttribute("msg")=="文件格式不正确"||model.getAttribute("msg").equals("文件格式不正确")) {
                model.addAttribute("message", "文件格式错误或文件数据为空！");
                return "error";
            }else if(model.getAttribute("msg")=="输入正确姓名"||model.getAttribute("msg").equals("输入正确姓名")){
                model.addAttribute("message", "核对表格，请输入正确姓名！");
                return "error";
            }else if(model.getAttribute("msg")=="输入正确电话"||model.getAttribute("msg").equals("输入正确电话")){
                model.addAttribute("message", "核对表格，请输入正确电话！");
                return "error";
            }else {
                return "error";
            }
        }
        //return "redirect:/teacher";   //这里需要修改，此处存在bug
    }
    @GetMapping("/download")   //此处尽量get请求,post不知为何有问题
    public void downLoadTemplate(HttpServletResponse response) throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        exportExcel(workbook,"学生姓名");
        response.setHeader("Content-type", "application/vnd.ms-excel");

        // 解决导出文件名中文乱码
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String("学生导入模版".getBytes("UTF-8"), "ISO-8859-1") + ".xls");
        workbook.write(response.getOutputStream());
    }
    @GetMapping("/downloadTeacher")   //此处尽量get请求,post不知为何有问题
    public void downloadTeacher(HttpServletResponse response) throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        exportExcel(workbook,"教师姓名");
        response.setHeader("Content-type", "application/vnd.ms-excel");

        // 解决导出文件名中文乱码
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String("教师导入模版".getBytes("UTF-8"), "ISO-8859-1") + ".xls");
        workbook.write(response.getOutputStream());
    }
    //导入为模版
    private void exportExcel(HSSFWorkbook workbook,String iName) throws Exception {
        //创建创建sheet
        HSSFSheet sheet = workbook.createSheet("Sheet1");

        //创建单元格样式
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);

        //设置首行标题标题
        HSSFRow headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellStyle(cellStyle);
        headerRow.createCell(0).setCellValue(iName);
        headerRow.createCell(1).setCellStyle(cellStyle);
        headerRow.createCell(1).setCellValue("电话");

        //创建三行数据
        HSSFRow row;
        for (int i = 0; i < 4; i++) {
            row = sheet.createRow(i + 1);
            row.createCell(0).setCellStyle(cellStyle);
            row.createCell(0).setCellValue("张三");
            row.createCell(1).setCellStyle(cellStyle);
            row.createCell(1).setCellValue(235254);
        }
    }
    @GetMapping("/downloadTeacherStudents/{id}")   //此处尽量get请求,post不知为何有问题
    public void downloadTeacherStudents(HttpServletResponse response,
                                        @PathVariable(name = "id") int courseid) throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        exportTeacherStudentsExcel(workbook,courseid);
        response.setHeader("Content-type", "application/vnd.ms-excel");

        // 解决导出文件名中文乱码
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String("学生上课信息表".getBytes("UTF-8"), "ISO-8859-1") + ".xls");
        workbook.write(response.getOutputStream());
    }
    //导入为模版
    private void exportTeacherStudentsExcel(HSSFWorkbook workbook,Integer courseid) throws Exception {
        //创建创建sheet
        HSSFSheet sheet = workbook.createSheet("Sheet1");

        //创建单元格样式
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);

        //设置首行标题标题
        HSSFRow headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellStyle(cellStyle);
        headerRow.createCell(0).setCellValue("学生姓名");
        headerRow.createCell(1).setCellStyle(cellStyle);
        headerRow.createCell(1).setCellValue("联系方式");
        headerRow.createCell(2).setCellStyle(cellStyle);
        headerRow.createCell(2).setCellValue("授课老师");
        headerRow.createCell(3).setCellStyle(cellStyle);
        headerRow.createCell(3).setCellValue("课程名称");
        headerRow.createCell(4).setCellStyle(cellStyle);
        headerRow.createCell(4).setCellValue("剩余课时");
        headerRow.createCell(5).setCellStyle(cellStyle);
        headerRow.createCell(5).setCellValue("预约状态");

        ClassInfoExample classInfoExample = new ClassInfoExample();
        classInfoExample.createCriteria()
                .andCourseIdEqualTo(courseid);
        List<ClassInfo> classInfos = classInfoMapper.selectByExample(classInfoExample);

        //创建三行数据
        HSSFRow row;
        for (int i = 0; i < classInfos.size(); i++) {

            Teacher teacher = teacherMapper.selectByPrimaryKey(classInfos.get(i).getTeacherId());
            Student student = studentMapper.selectByPrimaryKey(classInfos.get(i).getStudentId());
            Course course = courseMapper.selectByPrimaryKey(classInfos.get(i).getCourseId());


            row = sheet.createRow(i + 1);
            row.createCell(0).setCellStyle(cellStyle);
            row.createCell(0).setCellValue(student.getiName());
            row.createCell(1).setCellStyle(cellStyle);
            row.createCell(1).setCellValue(student.getTel());
            row.createCell(2).setCellStyle(cellStyle);
            row.createCell(2).setCellValue(teacher.getiName());
            row.createCell(3).setCellStyle(cellStyle);
            row.createCell(3).setCellValue(course.getCourseDescribe());
            row.createCell(4).setCellStyle(cellStyle);
            row.createCell(4).setCellValue(classInfos.get(i).getRemnantCourse());
            row.createCell(5).setCellStyle(cellStyle);
            if(classInfos.get(i).getStatus() == 1){
                row.createCell(5).setCellValue("已预约");
            }else {
                row.createCell(5).setCellValue("未预约");
            }
        }
    }
    @RequestMapping(value = "/adminAddTeachers", method = RequestMethod.POST)
    public Object adminAddTeachers(MultipartFile excelFile, Model model) throws IOException {

        boolean a = false;
        String fileName = excelFile.getOriginalFilename();

        try {
            model = teacherService.adminbatchImport(fileName, excelFile, model);
            return "redirect:/admin";
        } catch (Exception e) {
            e.printStackTrace();
            if (model.getAttribute("msg")=="文件格式不正确"||model.getAttribute("msg").equals("文件格式不正确")) {
                model.addAttribute("message", "文件格式错误或文件数据为空！");
                return "error";
            }else if(model.getAttribute("msg")=="输入正确姓名"||model.getAttribute("msg").equals("输入正确姓名")){
                model.addAttribute("message", "核对表格，请输入正确姓名！");
                return "error";
            }else if(model.getAttribute("msg")=="输入正确电话"||model.getAttribute("msg").equals("输入正确电话")){
                model.addAttribute("message", "核对表格，请输入正确电话！");
                return "error";
            }else {
                return "error";
            }
        }
        //return "redirect:/teacher";   //这里需要修改，此处存在bug
    }
}
