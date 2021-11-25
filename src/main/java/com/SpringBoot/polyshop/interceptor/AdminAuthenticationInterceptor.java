package com.SpringBoot.polyshop.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminAuthenticationInterceptor implements HandlerInterceptor {

	@Autowired
	private HttpSession session;
	
	
//	phuong thuc nay duoc implement
//	phuong thuc xua ly yeu cau dang nhap
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
//		kiem tra nguoi dung da dang nhap chua
		System.out.println("pre handle of request " + request.getRequestURI());
		if (session.getAttribute("username") != null) {
//			dang nhap roi thi tra ve true
			return true;
		}
		
//		lay thuoc tinh redirect-uri de su dung trong login controller
		session.setAttribute("redirect-uri", request.getRequestURI());
//		neu nguoi dung chua dang nhap thi return false va chuyen huong toi trang dang nhap
		response.sendRedirect("/alogin");
		return false;
	}
	
}
