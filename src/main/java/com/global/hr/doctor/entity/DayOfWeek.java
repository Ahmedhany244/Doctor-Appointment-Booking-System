package com.global.hr.doctor.entity;

public enum DayOfWeek {

	SUNDAY("SUNDAY"),MONDAY("MONDAY"),TUESDAY("TUESDAY"),WEDNESDAY("WEDNESDAY")
	,THURSDAY("THURSDAY"),FRIDAY("FRIDAY"),SATURDAY("SATURDAY");
	
	private final String day;
	
	private DayOfWeek(String day) {
		this.day = day;
	}
	
	@Override
	public String toString() {
		return day;
	}
	
}
