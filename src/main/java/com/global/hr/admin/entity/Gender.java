package com.global.hr.admin.entity;


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