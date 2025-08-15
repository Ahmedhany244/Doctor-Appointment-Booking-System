package com.global.hr.doctor.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.global.hr.doctor.entity.AvailableTime;
import com.global.hr.doctor.entity.Doctor;
import com.global.hr.doctor.repository.AvailableTimeRepository;
import com.global.hr.doctor.repository.DoctorRepo;
import com.global.hr.exception.DoctorNotFoundException;
import com.global.hr.patient.DataTransferObjects.AvailabilityRequest;
import com.global.hr.patient.DataTransferObjects.AvailabilityResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class AvailableTimeService {
	// menf3sh final m3 auto l2n auto injection dh b3d m3 create lkn final deh w enta btcreate fi constructor
	@Autowired
	private  AvailableTimeRepository availableTimeRepository;
	@Autowired
    private DoctorService doctorService;
	@Autowired
	private DoctorRepo doctorRepo;
	
	    public AvailableTime save(AvailableTime availableTime) {
	        return availableTimeRepository.save(availableTime);
	    }
	    public AvailabilityResponse addAvailableTime(int doctorId, AvailabilityRequest request) {
	        // 1. Check if doctor exists
	        Doctor doctor = doctorRepo.findById(doctorId)
	                .orElseThrow(() -> new DoctorNotFoundException( "Doctor not found"));

	       
	        AvailableTime availableTime = new AvailableTime();
	        availableTime.setDayOfWeek(request.getDay());
	        availableTime.setStartTime(request.getStartTime());
	        availableTime.setEndTime(request.getEndTime());
	        availableTime.setDoctor(doctor);

	        
	        AvailableTime saved = availableTimeRepository.save(availableTime);

	        
	        AvailabilityResponse response = new AvailabilityResponse();
	        response.setDay(saved.getDayOfWeek());
	        response.setStartTime(saved.getStartTime());
	        response.setEndTime(saved.getEndTime());
	        response.setDoctorId(doctor.getId());

	        return response;
	    }
	    public List<AvailabilityResponse> getDoctorAvailability(Integer doctorId) {
	        Doctor doctor = doctorService.getDoctorById(doctorId)
	                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with ID: " + doctorId));

	        List<AvailableTime> availabilityList = availableTimeRepository.findByDoctor(doctor);

	        return availabilityList.stream()
	                .map(a -> new AvailabilityResponse(a.getDayOfWeek(), a.getStartTime(), a.getEndTime()))
	                .collect(Collectors.toList());
	    }
	    

	    public List<AvailableTime> findAll() {
	        return availableTimeRepository.findAll();
	    }
	    public List<AvailableTime> getAvailabilityByDoctor(Doctor doctor) {
	        return availableTimeRepository.findByDoctor(doctor);
	    }

	    public Optional<AvailableTime> findById(Integer id) {
	        return availableTimeRepository.findById(id);
	    }
	    public ResponseEntity<AvailabilityResponse> updateAvailabletime(Integer id, AvailabilityRequest request) {
	        Optional<AvailableTime> optionalTime = availableTimeRepository.findById(id);

	        if (optionalTime.isPresent()) {
	            AvailableTime existingTime = optionalTime.get();

	            // Map request DTO → entity
	            existingTime.setDayOfWeek(request.getDay());
	            existingTime.setStartTime(request.getStartTime());
	            existingTime.setEndTime(request.getEndTime());
	            

	            // Save updated entity
	            AvailableTime saved = availableTimeRepository.save(existingTime);

	            // Map entity → response DTO
	            AvailabilityResponse response = new AvailabilityResponse();
	            response.setId(saved.getId());
	            response.setDay(saved.getDayOfWeek());
	            response.setStartTime(saved.getStartTime());
	            response.setEndTime(saved.getEndTime());
	            response.setDoctorId(saved.getDoctor().getId());

	            return ResponseEntity.ok(response);
	        } else {
	            throw new DoctorNotFoundException("Available time not found for ID: " + id);
	        }
	    }


	    public ResponseEntity<Void> deleteById(Integer id) {
	    	if (availableTimeRepository.findById(id).isPresent()) {
	    		availableTimeRepository.deleteById(id);
	    		
	    		return ResponseEntity.noContent().build();
	    	}
	    	return ResponseEntity.notFound().build();
	    	
	        
	    }

}
