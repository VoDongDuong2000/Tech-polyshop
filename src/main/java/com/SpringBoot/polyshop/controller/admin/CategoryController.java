package com.SpringBoot.polyshop.controller.admin;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.SpringBoot.polyshop.domain.Category;
import com.SpringBoot.polyshop.model.CategoryDto;
import com.SpringBoot.polyshop.service.CategoryService;

@Controller
@RequestMapping("admin/categories")
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;
	
	@GetMapping("add")
	public String add(Model model) {
		model.addAttribute("category", new CategoryDto());
		return "admin/categories/addOrEdit";
	}
	
	@GetMapping("edit/{categoryId}")
	public ModelAndView edit(ModelMap model, @PathVariable("categoryId") Long categpryId) {
		
		Optional<Category> opt = categoryService.findById(categpryId);
		CategoryDto dto = new CategoryDto();
		
		if (opt.isPresent()) {
			Category entity = opt.get();
			BeanUtils.copyProperties(entity, dto);
			dto.setIsEdit(true);
			model.addAttribute("category", dto);
			return new ModelAndView("/admin/categories/addOrEdit", model);
		}
		
		model.addAttribute("message", "Category is not existed");
		return new ModelAndView("forward:/admin/categories", model);
	}
	
	@GetMapping("delete/{categoryId}")
	public ModelAndView delete(ModelMap model, @PathVariable("categoryId") Long categoryId) {
		
		categoryService.deleteById(categoryId);
		
		model.addAttribute("message", "Delete successful!");
		
//		sau khi xoa xong quay ve trang list
		return new ModelAndView("forward:/admin/categories", model);
	}
	
	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model, 
			@Valid @ModelAttribute("category") CategoryDto dto, BindingResult result) {
		
//		{@Valid @ModelAttribute("category") CategoryDto dto, BindingResult result} dung de kiem tra du lieu
//		ket qua cua qua trinh kiem tra duoc dua vao trong result, neu co loi thi thuc hien load lai trang trong if
//		qua ben view se su dung if de kiem tra dieu kien
//		neu co loi thi thong bao. vd nhu loi id thi id is required
//		muc dich cua viec nay la neu co loi moi xuat cau thong bao, k loi thi k hien thi thong bao
		if(result.hasErrors()) {
			return new ModelAndView("/admin/categories/addOrEdit");
		}
		
		Category entity = new Category();
		BeanUtils.copyProperties(dto, entity);
		
		categoryService.save(entity);
		model.addAttribute("message", "Saved!");
		
		return new ModelAndView("forward:/admin/categories", model);
	}
	
	
//	o day de getmapping("") trong vi de save va delete redirect se chuyen den ham ngam dinh la ham rong(vi o tren request la admin/categories roi)
	@RequestMapping("")
	public String list(ModelMap model) {
		List<Category> list = categoryService.findAll();
		
		model.addAttribute("categories", list);
		
		return "admin/categories/list";
	}
	
	@GetMapping("search")
	public String search(ModelMap model, 
			@RequestParam(name = "name", required = false) String name) {
//		required = false: nghia la cho phep nguoi dung co the xac dinh gia tri name or k thi ham van chay
		List<Category> list = null;
		if(StringUtils.hasText(name)) {
			list = categoryService.findByNameContaining(name);
		}
		else {
			list = categoryService.findAll();
		}
		
		model.addAttribute("categories", list);
		
		return "admin/categories/search";
	}
	
	@GetMapping("searchpaginated")
	public String search(ModelMap model, 
			@RequestParam(name = "name", required = false) String name, 
			@RequestParam("page") Optional<Integer> page, 
			@RequestParam("size") Optional<Integer> size) {
		
//		page.orElse(1) nghia la neu nguoi dung k nhap/chon thi page mac dinh la 1
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(5);
		
//		so trang cua pageable se bat dau tu 0
//		hien thi danh sach category, so luong category hien thi tren 1 trang la pageSize, sort theo name
		Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("name"));
		Page<Category> resultPage = null;
		
		
		if(StringUtils.hasText(name)) {
			resultPage = categoryService.findByNameContaining(name, pageable);
			model.addAttribute("name", name);
		}
		else {
			resultPage = categoryService.findAll(pageable);
		}
		
//		tinh toan so trang can su dung
		int totalPages = resultPage.getTotalPages();
		if(totalPages > 0) {
//			o day current - 2 de hien thi so trang truoc trang hien tai
//			vd nhu dang o trang 3 thi van hien thi so 2 va 1 : 1, 2 , [3], 4, 5
//			o day co max bat dau tu 1 nen se k bi < 0, min cung tuong tu se k vuot qua tong so trang
			int start = Math.max(1, currentPage-2);
			int end = Math.min(currentPage + 2, totalPages);
			
			if (totalPages > 5) {
				if (end == totalPages) start = end - 5;
				else if(start == 1) end = start + 5;
			}
			
//			chuyen day so tu start toi end thanh danh sach de hien thi tren view
			List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
					.boxed()
					.collect(Collectors.toList());
			
//			hien thi so trang tren view
			model.addAttribute("pageNumbers", pageNumbers);
		}
		
//		hien thi ket qua tim kiem tren view
		model.addAttribute("categoryPage", resultPage);
		
		return "admin/categories/searchpaginated";
	}
}
