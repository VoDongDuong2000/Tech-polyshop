package com.SpringBoot.polyshop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.SpringBoot.polyshop.interceptor.AdminAuthenticationInterceptor;

//lop chua cac mo ta de gan cac interceptor de kiem tra nguoi dung co dang nhap hay k
//neu chua dang nhap thi yeu cau dang nhap
@Configuration
public class AuthenticationInterceptorConfig implements WebMvcConfigurer {

	@Autowired
	private AdminAuthenticationInterceptor adminAuthenticationInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
//		addPathPatterns("/admin/**"): kiem tra xem neu co goi url co /admin/ thi goi interceptor kiem tra dang nhap
		registry.addInterceptor(adminAuthenticationInterceptor)
			.addPathPatterns("/admin/**");
	}

	
}
