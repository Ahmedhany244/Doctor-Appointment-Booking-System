package com.example.demo.DataTransferObjects;

import com.example.demo.Models.Gender;

public class PatientRequest {

	private Integer id;
	private String name;
	private String phone;
	private String email;
	private Gender gender;
	private int age;
	
	// should add password

	
	public String getName() {
		return name;
	}
	public String getPhone() {
		return phone;
	}
	public String getEmail() {
		return email;
	}
	public Gender getGender() {
		return gender;
	}
	public Integer getAge() {
		if(age == 0)
			return null;
		else
			return age;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	

}
