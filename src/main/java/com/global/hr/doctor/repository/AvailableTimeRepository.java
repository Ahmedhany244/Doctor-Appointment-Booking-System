package com.global.hr.doctor.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.global.hr.doctor.entity.AvailableTime;
import com.global.hr.doctor.entity.Doctor;
import com.global.hr.patient.Models.Day;

@Repository
public interface AvailableTimeRepository extends JpaRepository<AvailableTime, Integer> {
	List<AvailableTime> findByDoctor(Doctor doctor);
	
	@Query("select t.max_patients from availabletime t where t.doctor_id = :id and t.available_day = :day ")
	Integer findMaxPatients(@Param("id") Integer id, @Param("day") Day day);
}
