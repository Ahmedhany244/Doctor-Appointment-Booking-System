package com.global.hr.doctor.service;

import com.global.hr.doctor.entity.Doctor;
import com.global.hr.doctor.repository.DoctorRepo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {
	@Autowired
	private DoctorRepo doctorRepo;

	// Create or update a doctor
	public Doctor saveDoctor(Doctor doctor) {
		return doctorRepo.save(doctor);
	}

	// Get all doctors
	public List<Doctor> getAllDoctors() {
		return doctorRepo.findAll();
	}

	// Get a doctor by ID
	// optional deh bthandle hwar lw rag3 null
	public Optional<Doctor> getDoctorById(Integer id) {
		return doctorRepo.findById(id);
	}

	// Delete a doctor by ID
	public void deleteDoctor(Integer id) {
		if (doctorRepo.existsById(id)) {
			doctorRepo.deleteById(id);
		} else {
			throw new RuntimeException("Doctor not found with id " + id);
		}
	}

	public Doctor updateDoctor(Integer id, Doctor updatedDoctor) {
		Optional<Doctor> optionalDoctor = doctorRepo.findById(id);

		if (optionalDoctor.isPresent()) {
			Doctor existingDoctor = optionalDoctor.get();
			existingDoctor.setName(updatedDoctor.getName());
			existingDoctor.setPhone(updatedDoctor.getPhone());
			existingDoctor.setSpecialization(updatedDoctor.getSpecialization());
			existingDoctor.setAddress(updatedDoctor.getAddress());
			existingDoctor.setEmail(updatedDoctor.getEmail());
			existingDoctor.setGender(updatedDoctor.getGender());
			return doctorRepo.save(existingDoctor); // save updated doctor
		} else {
			throw new RuntimeException("Doctor not found with id " + id);
		}
	}

	public List<Doctor> filterDoctors(String name,String specialization,
			String gender,String address){
		return doctorRepo.findByFilters(name, specialization, gender, address);
	}
}
