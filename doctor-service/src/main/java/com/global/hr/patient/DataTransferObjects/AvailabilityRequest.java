package com.global.hr.patient.DataTransferObjects;

import java.time.LocalTime;

import com.global.hr.doctor.entity.DayOfWeek;

public class AvailabilityRequest {
	private DayOfWeek day;
    private LocalTime startTime;
    private LocalTime endTime;

    // getters & setters
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
