package com.SpringBoot.polyshop.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties("storage")
@Data
public class StorageProperties {
	
//	xac dinh vi tri luu file upload
	private String location;
}
