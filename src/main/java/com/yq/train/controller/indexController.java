package com.yq.train.controller;

import com.yq.train.dto.PaginationDTO;
import com.yq.train.model.Student;
import com.yq.train.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

@Controller
public class indexController {
    @Autowired
    private CourseService courseService;

    @GetMapping("/")
    public String index(Model model,
                        HttpServletRequest request,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "6") Integer size ,
                        @RequestParam(name = "search",required = false) String  search ){
        if(search == ""){
            search = null;
        }
        PaginationDTO pagination = courseService.list(search,page,size);
        model.addAttribute("pagination",pagination);
        model.addAttribute("search",search);
        return "index";
    }
    @RequestMapping("unit/bill/showeinvoiceIndex")
    @ResponseBody
    public void showEInvoiceIndex(HttpServletRequest request,HttpServletResponse response){
        FileInputStream fis = null;
        OutputStream os = null;
        Student student = (Student)request.getSession().getAttribute("user");

        String filepath = student.getHeadportraitUrl();     //path是你服务器上图片的绝对路径
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
