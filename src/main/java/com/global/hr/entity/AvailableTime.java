package com.global.hr.entity;
import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
public class AvailableTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;
	@Enumerated(EnumType.STRING)
	@Column(name = "available_day", nullable = false)
    private DayOfWeek dayOfWeek;
	
	private LocalTime startTime;
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(DayOfWeek dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	public LocalTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	public LocalTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	public int getMax_Patients() {
		return max_Patients;
	}
	public void setMax_Patients(int max_Patients) {
		this.max_Patients = max_Patients;
	}
	private LocalTime endTime;
    private int max_Patients;

}
