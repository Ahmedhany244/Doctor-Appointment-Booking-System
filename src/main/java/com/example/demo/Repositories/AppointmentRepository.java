package com.example.demo.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.Models.Appointment;
import com.example.demo.Models.Day;

public interface AppointmentRepository extends JpaRepository<Appointment,Integer>{
    
    // Correcting the typo and adding FOR UPDATE
	@Query(
		    value = "SELECT COALESCE(MAX(patient_order), 0) FROM appointment "
		    		+ "WHERE doctor_id = :doctorId AND appointment_day = :availableDay AND status = 'BOOKED' ",
		    nativeQuery = true
		)
	Integer findMaxPatientOrder(@Param("doctorId") Integer doctorId, @Param("availableDay") Day availableDay);
	
	Optional<Appointment> findByAppointmentId(Integer appointmentId);
}