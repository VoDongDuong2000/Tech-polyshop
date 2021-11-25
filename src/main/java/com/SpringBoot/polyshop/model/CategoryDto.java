package com.SpringBoot.polyshop.model;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.SpringBoot.polyshop.domain.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto implements Serializable{

	private Long categoryId;
	
	@NotEmpty
	@Length(min = 2)
	private String name;
	
	private Boolean isEdit = false;

	
}
