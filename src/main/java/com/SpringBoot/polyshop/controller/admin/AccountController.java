package com.SpringBoot.polyshop.controller.admin;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.SpringBoot.polyshop.domain.Account;
import com.SpringBoot.polyshop.model.AccountDto;
import com.SpringBoot.polyshop.service.AccountService;

@Controller
@RequestMapping("admin/accounts")
public class AccountController {
	 
	@Autowired
	AccountService accountService;
	
	@GetMapping("add")
	public String add(Model model) {
		model.addAttribute("account", new AccountDto());
		return "admin/accounts/addOrEdit";
	}
	
	
	@RequestMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model, 
			@Valid @ModelAttribute("account") AccountDto dto, BindingResult result) {
		
		
		if(result.hasErrors()) {
			return new ModelAndView("/admin/accounts/addOrEdit");
		}
		
		Account entity = new Account();
		BeanUtils.copyProperties(dto, entity);
		
		accountService.save(entity);
		model.addAttribute("message", "Account is saved!");
		
		return new ModelAndView("forward:/admin/accounts", model);
	}
	
	
	
	@RequestMapping("")
	public String list(ModelMap model) {
		List<Account> list = accountService.findAll();
		
		model.addAttribute("accounts", list);
		
		return "admin/accounts/list";
	}
	
	@GetMapping("edit/{username}")
	public ModelAndView edit(ModelMap model, @PathVariable("username") String username) {
		
		Optional<Account> opt = accountService.findById(username);
		AccountDto dto = new AccountDto();
		
		if (opt.isPresent()) {
			Account entity = opt.get();
			BeanUtils.copyProperties(entity, dto);
			dto.setIsEdit(true);
			
//			password la thong tin private nen k gui kem, nguoi dung co the nhap pass moi trong form
			dto.setPassword("");
			
			model.addAttribute("account", dto);
			return new ModelAndView("/admin/accounts/addOrEdit", model);
		}
		
		model.addAttribute("message", "account is not existed");
		return new ModelAndView("forward:/admin/accounts", model);
	}
	
	
	@GetMapping("delete/{username}")
	public ModelAndView delete(ModelMap model, @PathVariable("username") String username) {
		
		accountService.deleteById(username);
		
		model.addAttribute("message", "Delete successful!");
		
//		sau khi xoa xong quay ve trang list
		return new ModelAndView("forward:/admin/accounts", model);
	}
	


	
//	@GetMapping("search")
//	public String search(ModelMap model, 
//			@RequestParam(name = "name", required = false) String name) {
////		required = false: nghia la cho phep nguoi dung co the xac dinh gia tri name or k thi ham van chay
//		List<account> list = null;
//		if(StringUtils.hasText(name)) {
//			list = accountService.findByNameContaining(name);
//		}
//		else {
//			list = accountService.findAll();
//		}
//		
//		model.addAttribute("accounts", list);
//		
//		return "admin/accounts/search";
//	}
//	
//	@GetMapping("searchpaginated")
//	public String search(ModelMap model, 
//			@RequestParam(name = "name", required = false) String name, 
//			@RequestParam("page") Optional<Integer> page, 
//			@RequestParam("size") Optional<Integer> size) {
//		
////		page.orElse(1) nghia la neu nguoi dung k nhap/chon thi page mac dinh la 1
//		int currentPage = page.orElse(1);
//		int pageSize = size.orElse(5);
//		
////		so trang cua pageable se bat dau tu 0
////		hien thi danh sach account, so luong account hien thi tren 1 trang la pageSize, sort theo name
//		Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("name"));
//		Page<account> resultPage = null;
//		
//		
//		if(StringUtils.hasText(name)) {
//			resultPage = accountService.findByNameContaining(name, pageable);
//			model.addAttribute("name", name);
//		}
//		else {
//			resultPage = accountService.findAll(pageable);
//		}
//		
////		tinh toan so trang can su dung
//		int totalPages = resultPage.getTotalPages();
//		if(totalPages > 0) {
////			o day current - 2 de hien thi so trang truoc trang hien tai
////			vd nhu dang o trang 3 thi van hien thi so 2 va 1 : 1, 2 , [3], 4, 5
////			o day co max bat dau tu 1 nen se k bi < 0, min cung tuong tu se k vuot qua tong so trang
//			int start = Math.max(1, currentPage-2);
//			int end = Math.min(currentPage + 2, totalPages);
//			
//			if (totalPages > 5) {
//				if (end == totalPages) start = end - 5;
//				else if(start == 1) end = start + 5;
//			}
//			
////			chuyen day so tu start toi end thanh danh sach de hien thi tren view
//			List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
//					.boxed()
//					.collect(Collectors.toList());
//			
////			hien thi so trang tren view
//			model.addAttribute("pageNumbers", pageNumbers);
//		}
//		
////		hien thi ket qua tim kiem tren view
//		model.addAttribute("accountPage", resultPage);
//		
//		return "admin/accounts/searchpaginated";
//	}
}
