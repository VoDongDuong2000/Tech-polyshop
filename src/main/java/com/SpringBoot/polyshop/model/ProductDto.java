package com.SpringBoot.polyshop.model;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
	
	private Long productId;
	private String name;
	private int quantity;
	private double unitPrice;
	private String image;
	private String description;
	private double discount;
	private Date enteredDate;
	private short status;
	private Long categoryId;
	
//	dung de tiep nhan noi dung file hinh de gan vao image
	private MultipartFile imageFile;
	
	private Boolean isEdit = false;
	
}
