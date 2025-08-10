package com.global.hr.patient.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.global.hr.patient.Models.Appointment;
import com.global.hr.patient.Models.Day;

public interface AppointmentRepository extends JpaRepository<Appointment,Integer>{
    
    // Correcting the typo and adding FOR UPDATE
	@Query(
		    value = "SELECT COALESCE(MAX(patient_order), 1) FROM appointment "
		    		+ "WHERE doctor_id = :doctorId AND appointment_day = :availableDay AND status = 'BOOKED' ",
		    nativeQuery = true
		)
	Integer findMaxPatientOrder(@Param("doctorId") Integer doctorId, @Param("availableDay") String availableDay);
	
	Optional<Appointment> findByAppointmentId(Integer appointmentId);
}