package com.global.hr.patient.DataTransferObjects;

import com.global.hr.patient.Models.Day;

public class AppointmentBookRequest {
	private Integer doctor_id;
	private Integer patientId;
	private Day appointmentDay;
	public Integer getDoctor_id() {
		return doctor_id;
	}
	public void setDoctor_id(Integer doctor_id) {
		this.doctor_id = doctor_id;
	}

	public Day getAppointmentDay() {
		return appointmentDay;
	}
	public void setAppointmentDay(Day appointmentDay) {
		this.appointmentDay = appointmentDay;
	}
	public Integer getPatientId() {
		return patientId;
	}
	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}
}
