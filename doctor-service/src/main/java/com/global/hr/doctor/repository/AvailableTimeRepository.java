package com.global.hr.doctor.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.global.hr.doctor.entity.AvailableTime;
import com.global.hr.doctor.entity.Doctor;
@Repository
public interface AvailableTimeRepository extends JpaRepository<AvailableTime, Integer> {
	List<AvailableTime> findByDoctor(Doctor doctor);
}
