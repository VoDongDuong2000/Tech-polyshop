package com.SpringBoot.polyshop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.SpringBoot.polyshop.domain.Account;
import com.SpringBoot.polyshop.repository.AccountRepository;
import com.SpringBoot.polyshop.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

//	thuc hien login
	@Override
	public Account login(String username, String password) {
		Optional<Account> optExist = findById(username);
		
//		kiem tra username co ton tai hay k va kiem tra xem mat khau moi nhap 
//		sau khi ma hoa co trung voi mat khau ma hoa da luu hay k
		if (optExist.isPresent() && bCryptPasswordEncoder.matches(password, 
				optExist.get().getPassword())) {
			
//			xoa trang mat khau vi mat khau k can hien thi
			optExist.get().setPassword("");
			
			return optExist.get();
		}
		
		return null;
	}
	
	
//	chinh sua phuong thuc save de ma hoa password
	@Override
	public <S extends Account> S save(S entity) {
		
//		kiem tra xem account da ton tai chua
		Optional<Account> optExist = findById(entity.getUsername());
		
//		neu account da ton tai
		if (optExist.isPresent()) {
//			kiem tra xem nguoi dung co nhap pass moi k
			if (StringUtils.isEmpty(entity.getPassword())) {
//				k nhap thi lay lai pass cu
				entity.setPassword(optExist.get().getPassword());
			} else {
//				neu nguoi dung nhap pass moi thi ma hoa roi luu lai
				entity.setPassword(bCryptPasswordEncoder.encode(entity.getPassword()));
			}
		} else {
			
//			neu account chua ton tai thi tao moi account, ma hoa pass roi luu lai
			entity.setPassword(bCryptPasswordEncoder.encode(entity.getPassword()));
		}
		
		return accountRepository.save(entity);
	}

	
	
	@Override
	public <S extends Account> Optional<S> findOne(Example<S> example) {
		return accountRepository.findOne(example);
	}

	@Override
	public Page<Account> findAll(Pageable pageable) {
		return accountRepository.findAll(pageable);
	}

	@Override
	public List<Account> findAll() {
		return accountRepository.findAll();
	}

	@Override
	public List<Account> findAll(Sort sort) {
		return accountRepository.findAll(sort);
	}

	@Override
	public List<Account> findAllById(Iterable<String> ids) {
		return accountRepository.findAllById(ids);
	}

	@Override
	public Optional<Account> findById(String id) {
		return accountRepository.findById(id);
	}

	@Override
	public <S extends Account> List<S> saveAll(Iterable<S> entities) {
		return accountRepository.saveAll(entities);
	}

	@Override
	public void flush() {
		accountRepository.flush();
	}

	@Override
	public <S extends Account> S saveAndFlush(S entity) {
		return accountRepository.saveAndFlush(entity);
	}

	@Override
	public boolean existsById(String id) {
		return accountRepository.existsById(id);
	}

	@Override
	public <S extends Account> List<S> saveAllAndFlush(Iterable<S> entities) {
		return accountRepository.saveAllAndFlush(entities);
	}

	@Override
	public <S extends Account> Page<S> findAll(Example<S> example, Pageable pageable) {
		return accountRepository.findAll(example, pageable);
	}

	@Override
	public void deleteInBatch(Iterable<Account> entities) {
		accountRepository.deleteInBatch(entities);
	}

	@Override
	public <S extends Account> long count(Example<S> example) {
		return accountRepository.count(example);
	}

	@Override
	public <S extends Account> boolean exists(Example<S> example) {
		return accountRepository.exists(example);
	}

	@Override
	public void deleteAllInBatch(Iterable<Account> entities) {
		accountRepository.deleteAllInBatch(entities);
	}

	@Override
	public long count() {
		return accountRepository.count();
	}


	@Override
	public void deleteById(String id) {
		accountRepository.deleteById(id);
	}



	@Override
	public void deleteAllByIdInBatch(Iterable<String> ids) {
		accountRepository.deleteAllByIdInBatch(ids);
	}

	@Override
	public void delete(Account entity) {
		accountRepository.delete(entity);
	}

	@Override
	public void deleteAllById(Iterable<? extends String> ids) {
		accountRepository.deleteAllById(ids);
	}

	@Override
	public void deleteAllInBatch() {
		accountRepository.deleteAllInBatch();
	}

	@Override
	public Account getOne(String id) {
		return accountRepository.getOne(id);
	}

	@Override
	public void deleteAll(Iterable<? extends Account> entities) {
		accountRepository.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		accountRepository.deleteAll();
	}

	@Override
	public Account getById(String id) {
		return accountRepository.getById(id);
	}

	@Override
	public <S extends Account> List<S> findAll(Example<S> example) {
		return accountRepository.findAll(example);
	}

	@Override
	public <S extends Account> List<S> findAll(Example<S> example, Sort sort) {
		return accountRepository.findAll(example, sort);
	}
	
	
}
