package com.SpringBoot.polyshop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SpringBoot.polyshop.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
//	ham nay duoc tao theo qui uoc chuan jpa, chi can dat ten ham theo qui uoc, noi dung tu sinh ra
	List<Category> findByNameContaining(String name);
	
//	tim kiem theo ten co hien thi phan trang du lieu
	Page<Category> findByNameContaining(String name, Pageable pageable);
}
