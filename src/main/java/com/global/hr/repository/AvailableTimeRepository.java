package com.global.hr.repository;
import com.global.hr.entity.AvailableTime;
import com.global.hr.entity.Doctor;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AvailableTimeRepository extends JpaRepository<AvailableTime, Integer> {
	List<AvailableTime> findByDoctor(Doctor doctor);
}
