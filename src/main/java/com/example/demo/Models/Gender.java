package com.example.demo.Models;


public enum Gender {
	F("F"), M("M");
	
	private final String gender;
	
	private Gender(String gender) {
		this.gender = gender;
	}
	
	@Override
	public String toString() {
		return gender;
	}
	
}
