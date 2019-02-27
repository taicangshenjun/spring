package com.example.demo.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, String> {

	public User findByName(String name);
	
	public List<User> findAll();
	
	@Query(value = "select id, age, name, sex, salary from user", nativeQuery = true)
	public List<User> findList(Pageable pageable);
	
	public List<User> findByAgeGreaterThanEqual(Integer age);
	
}
