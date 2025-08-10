package com.example.demo.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Appointment")
public class Appointment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id") // exact DB column name
	private Integer appointmentId;

	private Integer doctor_id;		// should get doctor info not doctor id 
	/*
	@ManyToOne
	@JoinColumn(name = "doctor_id") // Foreign key in Appointment table
	private Doctor doctor;
	 */
	
	@Enumerated(EnumType.STRING)
	@Column(name = "appointment_day") 
	private Day appointmentDay;
	
	@Column(name = "patient_order") 
	private int patientOrder;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
    @ManyToOne
    @JoinColumn(name = "patient_id")  // the Foreign key in Appointment table
    private Patient patient;
    

	public Integer getDoctor_id() {
		return doctor_id;
	}

	public Day getAppointmentDay() {
		return appointmentDay;
	}

	public int getPatientOrder() {
		return patientOrder;
	}

	public Status getStatus() {
		return status;
	}

	public Patient getPatient() {
		return patient;
	}
	public void setDoctor_id(Integer doctor_id) {
		this.doctor_id = doctor_id;
	}

	public void setAppointmentDay(Day appointmentDay) {
		this.appointmentDay = appointmentDay;
	}

	public void setPatientOrder(int patientOrder) {
		this.patientOrder = patientOrder;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Integer getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(Integer appointmentId) {
		this.appointmentId = appointmentId;
	}

}
