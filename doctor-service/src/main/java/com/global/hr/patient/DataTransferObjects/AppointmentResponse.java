package com.global.hr.patient.DataTransferObjects;

import com.global.hr.doctor.entity.DayOfWeek;
import com.global.hr.doctor.entity.Status;



public class AppointmentResponse {
	private Integer appointmentId;
	private Integer doctor_id;
	private DayOfWeek appointmentDay;
	private Status status;
	private int patientOrder;
	
	public Integer getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(Integer appointmentId) {
		this.appointmentId = appointmentId;
	}
	public Integer getDoctor_id() {
		return doctor_id;
	}
	public void setDoctor_id(Integer doctor_id) {
		this.doctor_id = doctor_id;
	}
	public DayOfWeek getAppointmentDay() {
		return appointmentDay;
	}
	public void setAppointmentDay(DayOfWeek appointmentDay) {
		this.appointmentDay = appointmentDay;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public int getPatientOrder() {
		return patientOrder;
	}
	public void setPatientOrder(int patientOrder) {
		this.patientOrder = patientOrder;
	}
	
}
