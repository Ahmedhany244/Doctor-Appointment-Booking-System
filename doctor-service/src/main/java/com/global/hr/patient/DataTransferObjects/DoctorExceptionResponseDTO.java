package com.global.hr.patient.DataTransferObjects;

import com.global.hr.doctor.entity.DayOfWeek;
import com.global.hr.doctor.entity.ExcuseId;

public class DoctorExceptionResponseDTO {
	
	private ExcuseId id;
    private DayOfWeek day;
   

    public DoctorExceptionResponseDTO(ExcuseId id, DayOfWeek day) {
        this.id = id;
        this.day = day;
        
    }

	public ExcuseId getId() {
		return id;
	}

	public void setId(ExcuseId id) {
		this.id = id;
	}

	public DayOfWeek getDay() {
		return day;
	}

	public void setDay(DayOfWeek day) {
		this.day = day;
	}

	
}
