package com.example.demo.Repositories;

import com.example.demo.Models.AvailableTime;
import com.example.demo.Models.Doctor;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AvailableTimeRepository extends JpaRepository<AvailableTime, Integer> {
	List<AvailableTime> findByDoctor(Doctor doctor);
}
