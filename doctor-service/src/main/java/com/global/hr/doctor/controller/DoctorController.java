package com.global.hr.doctor.controller;

import com.global.hr.doctor.entity.AvailableTime;
import com.global.hr.doctor.entity.DayOfWeek;
import com.global.hr.doctor.entity.Doctor;
import com.global.hr.doctor.entity.DoctorException;
import com.global.hr.doctor.entity.ExcuseId;
import com.global.hr.doctor.service.AppointmentClient;
import com.global.hr.doctor.service.AvailableTimeService;
import com.global.hr.doctor.service.DoctorService;
import com.global.hr.doctor.service.ExcuseService;
import com.global.hr.patient.DataTransferObjects.AppointmentCancelRequest;
import com.global.hr.patient.DataTransferObjects.AppointmentResponse;
import com.global.hr.patient.DataTransferObjects.AvailabilityRequest;
import com.global.hr.patient.DataTransferObjects.AvailabilityResponse;
import com.global.hr.patient.DataTransferObjects.DoctorExceptionRequestDTO;
import com.global.hr.patient.DataTransferObjects.DoctorExceptionResponseDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/doctors")

public class DoctorController {
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private AvailableTimeService availableservice;
	@Autowired
	private ExcuseService excuseservice;
	@Autowired
	private AppointmentClient appointementclientService;

	// Get all doctors
	@GetMapping
	public List<Doctor> getAllDoctors() {
		return doctorService.getAllDoctors();
	}

	// Get doctor by ID
	@GetMapping("/{id}")
	public Doctor getDoctorById(@PathVariable Integer id) {
		return doctorService.getDoctorById(id)
				.orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + id));
	}

	// Update existing doctor
	@PutMapping("/{id}")
	public Doctor updateDoctor(@PathVariable Integer id, @RequestBody Doctor doctor) {
		return doctorService.updateDoctor(id, doctor);
	}

	@PostMapping("/{id}/availability")
	public ResponseEntity<AvailabilityResponse> addAvailableTime(
	        @PathVariable Integer id,
	        @RequestBody AvailabilityRequest requestDTO) {

		AvailabilityResponse responseDTO = availableservice.addAvailableTime(id, requestDTO);
	    return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
	}

	@PostMapping("/availability/{id}")
	public ResponseEntity<AvailabilityResponse> updateAvailableTime(@PathVariable Integer id,
			@RequestBody AvailabilityRequest availableTime) {
		return availableservice.updateAvailabletime(id, availableTime);

	}

	@DeleteMapping("/availability/{id}")
	public ResponseEntity<Void> deleteAvailableTime(@PathVariable Integer id) {
			 return availableservice.deleteById(id);
			
		
	}

	@GetMapping("/{id}/availability")
    public ResponseEntity<List<AvailabilityResponse>> getDoctorAvailability(@PathVariable Integer id) {
        List<AvailabilityResponse> availability = availableservice.getDoctorAvailability(id);
        return ResponseEntity.ok(availability);
    }


	@PostMapping("/{doctorId}/excuses")
	public ResponseEntity<DoctorExceptionResponseDTO> addExcuse(
	        @PathVariable Integer doctorId,
	        @RequestBody DoctorExceptionRequestDTO request) {

	    DoctorExceptionResponseDTO response = excuseservice.addExcuse(doctorId, request);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@DeleteMapping("/excuses/{doctorId}/{excuseDay}")
	public ResponseEntity<Void> deleteExcuse(@PathVariable Integer doctorId, @PathVariable DayOfWeek excuseDay) {

		ExcuseId excuseId = new ExcuseId(doctorId, excuseDay);
			return excuseservice.delete(excuseId);
			
		
	}

	@GetMapping("/{doctorId}/excuses")
    public ResponseEntity<List<DoctorExceptionResponseDTO>> getExcuses(@PathVariable Integer doctorId) {
        List<DoctorExceptionResponseDTO> excuses = excuseservice.getExcusesByDoctorId(doctorId);
        return ResponseEntity.ok(excuses);
    }
	@PatchMapping("/cancelappointment")
	public ResponseEntity<AppointmentResponse> cancelAppointment(@RequestBody AppointmentCancelRequest req) {
		AppointmentResponse response = appointementclientService.deleteAppointment(req);

		return new ResponseEntity<>(response, HttpStatus.OK);

	}
}
