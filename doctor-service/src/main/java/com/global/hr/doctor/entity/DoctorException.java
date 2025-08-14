package com.global.hr.doctor.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "doctor_exception")
public class DoctorException {
	 
    @EmbeddedId
    private ExcuseId id;

    @MapsId("doctorid")  // This matches the field name in ExcuseId
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    // Constructors
    public DoctorException() {}

    public DoctorException(Doctor doctor, DayOfWeek excuseDay) {
        this.doctor = doctor;
        this.id = new ExcuseId(doctor.getId(), excuseDay);
    }

    // Getters and Setters
    public ExcuseId getId() {
        return id;
    }

    public void setId(ExcuseId id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
        if (this.id == null) {
            this.id = new ExcuseId();
        }
        this.id.setDoctorid(doctor.getId());
    }
    public void setExcuseDay(DayOfWeek excuseDay) {
        if (this.id == null) {
            this.id = new ExcuseId();
        }
        this.id.setExcuseDay(excuseDay);
    }
    
    public DayOfWeek getExcuseDay() {
        return this.id != null ? this.id.getExcuseDay() : null;
    }
	
	
}
