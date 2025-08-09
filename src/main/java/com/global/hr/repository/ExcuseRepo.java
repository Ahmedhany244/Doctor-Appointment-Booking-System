package com.global.hr.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.global.hr.entity.Doctor;
import com.global.hr.entity.DoctorException;
import com.global.hr.entity.ExcuseId;

@Repository
public interface ExcuseRepo extends JpaRepository<DoctorException, ExcuseId> {
	 List<DoctorException> findByDoctor(Doctor doctor);
}
