package com.global.hr.doctor.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.global.hr.doctor.entity.AvailableTime;
import com.global.hr.doctor.entity.Doctor;
@Repository
public interface AvailableTimeRepository extends JpaRepository<AvailableTime, Integer> {
	List<AvailableTime> findByDoctor(Doctor doctor);
	
	@Query(
		    value = "SELECT t.max_patients FROM available_time as t WHERE t.doctor_id = :id AND t.available_day = :day",
		    nativeQuery = true
		)
	Integer findMaxPatients(@Param("id") Integer id, @Param("day") String day);
}
