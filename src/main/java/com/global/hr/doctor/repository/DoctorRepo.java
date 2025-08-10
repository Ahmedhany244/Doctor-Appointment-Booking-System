package com.global.hr.doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.global.hr.doctor.entity.Doctor;
@Repository
public interface DoctorRepo extends JpaRepository<Doctor, Integer> {

}
