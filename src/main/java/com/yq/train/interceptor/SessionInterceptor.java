package com.yq.train.interceptor;

import com.yq.train.exception.CustomizeErrorCode;
import com.yq.train.exception.CustomizeException;
import com.yq.train.mapper.AdminMapper;
import com.yq.train.mapper.StudentMapper;
import com.yq.train.mapper.TeacherMapper;
import com.yq.train.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class SessionInterceptor implements HandlerInterceptor {
   @Autowired
   private AdminMapper adminMapper;
   @Autowired
   private StudentMapper studentMapper;
   @Autowired
   private TeacherMapper teacherMapper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("URL:"+request.getRequestURL());

        if(request.getSession().getAttribute("user")==null){
            throw new CustomizeException(CustomizeErrorCode.NO_LOGIN);
            //response.sendRedirect("/");

        }else
            return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
