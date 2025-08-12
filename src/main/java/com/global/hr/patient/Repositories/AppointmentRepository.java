package com.global.hr.patient.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.global.hr.patient.Models.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment,Integer>{
    
    
	@Query(
		    value = "SELECT COALESCE(MAX(patient_order), 1) FROM appointment "
		    		+ "WHERE doctor_id = :doctorId AND appointment_day = :availableDay AND status = 'BOOKED' ",
		    nativeQuery = true
		)
	Integer findMaxPatientOrderBooked(@Param("doctorId") Integer doctorId, @Param("availableDay") String availableDay);
	
	@Query(
		    value = "SELECT MIN(patient_order) FROM appointment "
		    		+ "WHERE doctor_id = :doctorId AND appointment_day = :availableDay AND status = 'Cancelled' ",
		    nativeQuery = true
		)
	Optional<Integer> findMinPatientOrderCancelled(@Param("doctorId") Integer doctorId, @Param("availableDay") String availableDay);
	
	Optional<Appointment> findByAppointmentId(Integer appointmentId);
}