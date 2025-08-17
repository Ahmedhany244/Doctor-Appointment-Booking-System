package com.global.hr.doctor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.global.hr.doctor.entity.Doctor;
@Repository
public interface DoctorRepo extends JpaRepository<Doctor, Integer> , JpaSpecificationExecutor<Doctor>{
	
	/*
	//use instead specification filter
	@Query("SELECT d FROM Doctor d WHERE " +
		       "(:name is null or :name = '' or d.name LIKE %:name%) AND " +
		       "(:specialization is null or :specialization = '' or d.specialization = :specialization) AND " +
		       "(:gender is null or :gender = '' or d.gender = :gender) AND " +
		       "(:address is null or :address = '' or d.address = :address)")
		List<Doctor> findByFilters(@Param("name") String name,
		                           @Param("specialization") String specialization,
		                           @Param("gender") String gender,
		                           @Param("address") String address);
	
	*/
}
