package com.yq.train.controller;

import com.yq.train.exception.CustomizeErrorCode;
import com.yq.train.exception.CustomizeException;
import com.yq.train.model.Teacher;
import com.yq.train.service.StudentService;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class excelController {
    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/teacherAddStudents", method = RequestMethod.POST)
    public String exImport(MultipartFile excelFile, HttpServletRequest request, Model model) throws IOException {

        boolean a = false;
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");
        String fileName = excelFile.getOriginalFilename();

        try {
            a = studentService.batchImport(fileName, excelFile, teacher,model);
            if (!a) {
                model.addAttribute("msg", "文件格式错误或文件数据为空！");
                return "redirect:/msg";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/teacher";   //这里需要修改，此处存在bug
    }


    @GetMapping("/download")   //此处尽量get请求,post不知为何有问题
    public void downLoadTemplate(HttpServletResponse response) throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        exportExcel(workbook);
        response.setHeader("Content-type", "application/vnd.ms-excel");

        // 解决导出文件名中文乱码
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String("学生导入模版".getBytes("UTF-8"), "ISO-8859-1") + ".xls");
        workbook.write(response.getOutputStream());
    }

    //导入为模版
    private void exportExcel(HSSFWorkbook workbook) throws Exception {
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
}
