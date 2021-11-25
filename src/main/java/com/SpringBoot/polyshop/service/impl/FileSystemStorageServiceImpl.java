package com.SpringBoot.polyshop.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.SpringBoot.polyshop.config.StorageProperties;
import com.SpringBoot.polyshop.exception.StorageException;
import com.SpringBoot.polyshop.exception.StorageFileNotFoundException;
import com.SpringBoot.polyshop.service.StorageService;


@Service
public class FileSystemStorageServiceImpl implements StorageService {
	
//	xac dinh duong dan goc de luu hinh
	private final Path rootLocation;
	
//	tao ra file luu tru dua vao id duoc truyen vao
	@Override
	public String getStoredFilename(MultipartFile file, String id) {
//		ext: la phan mo rong cua file(jpg, png, ...)
		String ext = FilenameUtils.getExtension(file.getOriginalFilename());
		return "p" + id + "." + ext;
	}
	
//	day la constructor truyen cac cau hinh cho phan luu tru
	public FileSystemStorageServiceImpl(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}
	
	
//	luu noi dung cua file tu MultipartFile
	@Override
	public void store(MultipartFile file, String storedFilename) {
		try {
			if(file.isEmpty()) {
				throw new StorageException("Failed to store empty file");
			}
			
			Path destinationFile = this.rootLocation.resolve(Paths.get(storedFilename)).normalize().toAbsolutePath();
			
			if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
				throw new StorageException("Can't store file outside current directory");
			}
			
			try(InputStream inputStream = file.getInputStream()){
				Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
			}
			
		} catch (Exception e) {
			throw new StorageException("Failed to store file", e);
		}
	}
	
//	dung de nap noi dung file duoi dang Resource
	@Override
	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if(resource.exists() || resource.isReadable()) {
				return resource;
			}
			
			throw new StorageFileNotFoundException("Could not read file: " + filename);
		} catch (Exception e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename);
		}
	}
	
	
	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}
	
//	xoa file khi k can thiet
	@Override
	public void delete(String storedFilename) throws IOException {
		Path destinationFiel = rootLocation.resolve(Paths.get(storedFilename)).normalize().toAbsolutePath();
		
		Files.delete(destinationFiel);
	}
	
//	khoi tao cac thu muc
	@Override
	public void init() {
		try {
			Files.createDirectories(rootLocation);
			System.out.println(rootLocation.toString());
		} catch (Exception e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}
}
