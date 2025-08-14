package com.example.demo.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.Models.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment,Integer>{
    
    
	@Query(
		    value = "SELECT COALESCE(MAX(patient_order), 1) FROM appointment "
		    		+ "WHERE doctor_id = :doctorId AND appointment_day = :availableDay AND status = 'BOOKED' ",
		    nativeQuery = true
		)
	Integer findMaxPatientOrderBooked(@Param("doctorId") Integer doctorId, @Param("availableDay") String availableDay);
	

	@Query(
		    value = "SELECT MIN(a.patient_order) FROM appointment a "
		    		+ "WHERE a.doctor_id = :doctorId AND a.appointment_day = :availableDay "
		    		+ "AND a.status = 'CANCELLED' "
		    		+ "AND NOT EXISTS "
		    		+ "( SELECT 1 FROM appointment b WHERE b.doctor_id = a.doctor_id "
		    		+ "AND b.appointment_day = a.appointment_day "
		    		+ "AND b.patient_order = a.patient_order "
		    		+ "AND b.status = 'BOOKED')",nativeQuery = true
		)
	Optional<Integer> findMinPatientOrderCancelled(@Param("doctorId") Integer doctorId, @Param("availableDay") String availableDay);
	
	Optional<Appointment> findByAppointmentId(Integer appointmentId);
}