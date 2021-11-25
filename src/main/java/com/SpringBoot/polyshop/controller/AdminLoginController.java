package com.SpringBoot.polyshop.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.SpringBoot.polyshop.domain.Account;
import com.SpringBoot.polyshop.model.AdminLoginDto;
import com.SpringBoot.polyshop.service.AccountService;

@Controller
public class AdminLoginController {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private HttpSession session;
	
	
	@GetMapping("alogin")
	public String login(ModelMap model) {
		model.addAttribute("account", new AdminLoginDto());
		return "/admin/accounts/login";
	}
	
	@PostMapping("alogin")
	public ModelAndView login(ModelMap model, 
			@Valid @ModelAttribute("account") AdminLoginDto dto,
			BindingResult result) {
		
//		kiem tra xem du lieu co loi k(k hop le voi cac yeu cau da dat ra: @Notempty,...)
		if(result.hasErrors()) {
//			neu co loi thi tra ve view login
			return new ModelAndView("/admin/accounts/login", model);
		}
		
//		su dung phuong thuc login de tra ve account
		Account account = accountService.login(dto.getUsername(), dto.getPassword());
		
//		neu account bang null thi nghia la login failed
		if (account == null) {
//			thuc hien thong bao va tra ve view login
			model.addAttribute("message", "Invalid username or password");
			return new ModelAndView("/admin/accounts/login", model);
		}
		
		System.out.println("dang nhap thanh cong");
		
		
		session.setAttribute("username", account.getUsername());
//		lay duong dan ma nguoi dung muon truy cap truoc khi dang nhap
//		sau khi dang nhap thanh cong thi chuyen toi trang nay
		Object ruri = session.getAttribute("redirect-uri");
		if (ruri != null) {
//			sau khi so sanh xong thi co thua remove vi k can luu tru thong tin nay
			session.removeAttribute("redirect-uri");
			return new ModelAndView("redirect:" + ruri);
		}
//		neu k co yeu cau uri nao thi se chuyen toi uri mac dinh o duoi la forward:/admin/categories
		
		
//		neu dang nhap thanh cong, thiet lap thuoc tinh username cua session
//		de biet la nguoi dung da dang nhap vao he thong
		
		
		return new ModelAndView("forward:/admin/categories", model);
	}
}
