package com.yq.train.interceptor;

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
            response.sendRedirect("/");
            
        }
//        Cookie[] cookies = request.getCookies();
//        if(cookies != null&&cookies.length != 0){
//            for (Cookie cookie:cookies){
//                if(cookie.getName().equals("token")){
//                    String token = cookie.getValue();
//                    UserExample userExample = new UserExample();
//                    userExample.createCriteria()
//                            .andTokenEqualTo(token);
//                    List<User> users = userMapper.selectByExample(userExample);
//                    //User user = userMapper.findByToken(token);
//                    if(users.size() != 0){
//                        request.getSession().setAttribute("user",users.get(0));
//                        User user = (User)request.getSession().getAttribute("user");
//                        Long unreadCount = notificationService.unreadCount(users.get(0).getId());
//                        request.getSession().setAttribute("unreadCount",unreadCount);
//                        //System.out.println(user.getId());
//                    }
//                    break;
//                }
//            }
//        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
