package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

	@Id
	@Column(name = "ID",length = 50)
	private String id;
	
	@Column(name = "USER_NAME",length = 40)
	private String userName;
	
	@Column(name = "NAME",length = 20)
	private String name;
	
	@Column(name = "AGE")
	private Integer age;
	
	@Column(name = "BALANCE")
	private Double balance;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
}
