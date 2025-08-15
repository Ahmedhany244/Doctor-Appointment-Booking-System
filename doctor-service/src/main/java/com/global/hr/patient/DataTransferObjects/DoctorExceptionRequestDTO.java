package com.global.hr.patient.DataTransferObjects;

import com.global.hr.doctor.entity.DayOfWeek;

public class DoctorExceptionRequestDTO {
	private DayOfWeek excuseDay;

	public DayOfWeek getExcuseDay() {
		return excuseDay;
	}

	public void setExcuseDay(DayOfWeek excuseDay) {
		this.excuseDay = excuseDay;
	}
}
