package com.global.hr.patient.Models;

public enum Day {

	SUNDAY("SUNDAY"),MONDAY("MONDAY"),TUESDAY("TUESDAY"),WEDNESDAY("WEDNESDAY")
	,THURSDAY("THURSDAY"),FRIDAY("FRIDAY"),SATURDAY("SATURDAY");
	
	private final String day;
	
	private Day(String day) {
		this.day = day;
	}
	
	@Override
	public String toString() {
		return day;
	}
	
}
