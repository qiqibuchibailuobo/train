package com.yq.train.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration

public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private SessionInterceptor sessionInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(sessionInterceptor).addPathPatterns("/**")
                .excludePathPatterns(
                        "/",
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/fonts/**",
                        "/login",
                        "/getVerify",
                        "/register",
                        "/studentRegister",
                        "/course",
                        "/course/css/**",
                        "/course/js/**",
                        "/course/images/**",
                        "/course/fonts/**",
                        "/error",
                        "/teacher/css/**",
                        "/teacher/js/**",
                        "/teacher/images/**",
                        "/teacher/fonts/**",
                        "/teacher/**",
                        "/teacher/unit/bill/showeinvice2",
                        "/unit/bill/showeinvice2",
                        "/static/**",
                        "/admin/**"
                );
    }

    /**
     * 静态资源无法加载问题
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");

    }
}
