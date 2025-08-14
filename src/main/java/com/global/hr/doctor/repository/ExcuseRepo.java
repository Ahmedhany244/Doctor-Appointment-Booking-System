package com.global.hr.doctor.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.global.hr.doctor.entity.Doctor;
import com.global.hr.doctor.entity.DoctorException;
import com.global.hr.doctor.entity.ExcuseId;

import jakarta.transaction.Transactional;

@Repository
public interface ExcuseRepo extends JpaRepository<DoctorException, ExcuseId> {
	 List<DoctorException> findByDoctor(Doctor doctor);


	@Modifying
	@Transactional
	@Query(
		value = "DELETE FROM doctor_exception WHERE doctor_id = :doctor_id",
		nativeQuery = true
	)
	void removeByDoctor(@Param("doctor_id") Integer doctor_id);
}
