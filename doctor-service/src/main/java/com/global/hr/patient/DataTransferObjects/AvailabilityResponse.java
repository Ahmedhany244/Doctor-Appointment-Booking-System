package com.global.hr.patient.DataTransferObjects;

import java.time.LocalTime;

import com.global.hr.doctor.entity.DayOfWeek;

import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilityResponse {
	private int id;
    private int doctorId;
    private DayOfWeek day;
    private LocalTime startTime;
    private LocalTime endTime;
    public AvailabilityResponse() {
    }

    // Parameterized constructor
    public AvailabilityResponse(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.day= dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    // getters & setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getDoctorId() {
        return doctorId;
    }
    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }
    public DayOfWeek getDay() {
        return day;
    }
    public void setDay(DayOfWeek day) {
        this.day = day;
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
    
}
