package com.inchwisp.tale.framework.configurer;

import com.inchwisp.tale.framework.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @ClassName: MVCConfig
 * @Description: 配置加载类
 * @Author: MSI
 * @Date: 2019/1/4 11:33
 * @Vresion: 1.0.0
 **/
@Configuration
public class MVCConfig extends WebMvcConfigurerAdapter {

    /**
     * @Author MSI
     * @Description 添加过滤器方法
     * @Date 2019/1/7 10:37
     * @Param [registry]
     * @return void
     **/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor())
                .excludePathPatterns("/static/*")
                .excludePathPatterns("/error")
                .excludePathPatterns("/login")
                .excludePathPatterns("/loginOut")
                .excludePathPatterns("/register")
                .excludePathPatterns("/test")//默认不拦截路径
                .addPathPatterns("/**"); //拦截所有路径
    }

    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }
}
