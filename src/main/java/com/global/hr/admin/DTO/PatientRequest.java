package com.global.hr.admin.DTO;

import org.springframework.format.annotation.NumberFormat;
import com.global.hr.admin.entity.Gender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class PatientRequest {
	
	private Integer id;

	@NotBlank(message = "Name field is required")
	private String name;

	@NotBlank(message = "Phone number is required.")
    @NumberFormat
    @PositiveOrZero
    @Size (min = 11 , max = 11, message = "Phone number must be 11 digits.")
	private String phone;

	@NotBlank(message = "Email field is required")
    @Email(message = "Must add a valid email")
	private String email;

	@NotNull(message = "Gender is required and must be either 'M' or 'F'" )
	private Gender gender;
	


    @NumberFormat
    @Positive
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
