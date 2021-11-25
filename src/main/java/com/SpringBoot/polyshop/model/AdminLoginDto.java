package com.SpringBoot.polyshop.model;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.SpringBoot.polyshop.domain.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminLoginDto {
	@NotEmpty
	private String username;
	
	@NotEmpty
	private String password;
	
//	dung de tu dong dang nhap
	private Boolean rememberMe = false;
}
