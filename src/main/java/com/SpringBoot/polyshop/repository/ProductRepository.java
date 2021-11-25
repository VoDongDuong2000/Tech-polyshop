package com.SpringBoot.polyshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SpringBoot.polyshop.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
//	cau truc JpaRepository<{kieu du lieu}, {kieu du lieu cho khoa chinh}>
	
	
}
