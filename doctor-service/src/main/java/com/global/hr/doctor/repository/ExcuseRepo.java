package com.global.hr.doctor.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.global.hr.doctor.entity.Doctor;
import com.global.hr.doctor.entity.DoctorException;
import com.global.hr.doctor.entity.ExcuseId;

@Repository
public interface ExcuseRepo extends JpaRepository<DoctorException, ExcuseId> {
	 List<DoctorException> findByDoctor(Doctor doctor);
}
