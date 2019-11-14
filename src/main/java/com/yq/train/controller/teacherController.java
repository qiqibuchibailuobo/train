package com.yq.train.controller;

import com.yq.train.dto.PaginationDTO;
import com.yq.train.model.Student;
import com.yq.train.model.Teacher;
import com.yq.train.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

@Controller
public class teacherController {
    @Autowired
    private TeacherService teacherService;
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

    @RequestMapping("unit/bill/showeinvoice2")
    @ResponseBody
    public void showEInvoice2(HttpServletRequest request, HttpServletResponse response){
        FileInputStream fis = null;
        OutputStream os = null;
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");
        String filepath = teacher.getHeadportraitUrl();     //path是你服务器上图片的绝对路径
        File file = new File(filepath);
        if(file.exists()){
            try {
                fis = new FileInputStream(file);
                long size = file.length();
                byte[] temp = new byte[(int) size];
                fis.read(temp, 0, (int) size);
                fis.close();
                byte[] data = temp;
                response.setContentType("image/png");
                os = response.getOutputStream();
                os.write(data);
                os.flush();
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




}
