package com.global.hr.patient.DataTransferObjects;

import java.time.LocalTime;

import com.global.hr.doctor.entity.DayOfWeek;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilityResponse {
	private int id;
    private int doctorId;
    private String day;
    private LocalTime startTime;
    private LocalTime endTime;
    public AvailabilityResponse() {
    }

    // Parameterized constructor
    public AvailabilityResponse(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime,int doctorId) {
        this.setDay(dayOfWeek);
        this.startTime = startTime;
        this.endTime = endTime;
        this.doctorId = doctorId;
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


	public String getDay() {
		return day;
	}

	public void setDay(DayOfWeek day) {
		this.day = day.toString();
	}
    
}
