package com.global.hr.patient.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.global.hr.doctor.entity.AvailableTime;
import com.global.hr.doctor.entity.Doctor;
import com.global.hr.doctor.service.AvailableTimeService;
import com.global.hr.doctor.service.DoctorService;
import com.global.hr.patient.DataTransferObjects.AppointmentBookRequest;
import com.global.hr.patient.DataTransferObjects.AppointmentCancelRequest;
import com.global.hr.patient.DataTransferObjects.AppointmentResponse;
import com.global.hr.patient.DataTransferObjects.PatientRequest;
import com.global.hr.patient.DataTransferObjects.PatientResponse;
import com.global.hr.patient.Exception.ResourceNotFoundException;
import com.global.hr.patient.Models.Gender;
import com.global.hr.patient.Services.AppointmentServices;
import com.global.hr.patient.Services.PatientServices;

@RestController
@RequestMapping("/patients")
public class PatientController {
	
	private final PatientServices patientService;
	private final AppointmentServices appointementService;
	
	@Autowired
	DoctorService docService;
	
	@Autowired
	AvailableTimeService availableservice;
	
	//@Autowire is omitted bec it is not needed unless there is confusion (more than 1 constructor)
	public PatientController(PatientServices patientService , AppointmentServices appointmentService) {
		this.patientService = patientService;
		this.appointementService = appointmentService;
	}
	
	@PostMapping(value = "/signup", produces = "application/json") 
	public ResponseEntity<PatientResponse> signUp (@RequestBody PatientRequest req){
		
		PatientResponse response = patientService.signUp(req);
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);

	}
	
	@PostMapping("/login") 
	public ResponseEntity<PatientResponse> login (@RequestBody PatientRequest req){  // patient login by email and password
	
		PatientResponse response = patientService.login(req);
		
		return ResponseEntity.status(HttpStatus.OK).body(response); 
		// both this and the one above are different forms of the creation of 
		// the response entity and both are valid 
	}

	@PatchMapping("/cancelappointment")
	public ResponseEntity<AppointmentResponse> cancelAppointment (@RequestBody AppointmentCancelRequest req){
		AppointmentResponse response = appointementService.cancelAppointment(req);
		
		return new ResponseEntity<>(response,HttpStatus.OK);
		
		
	}
	@PostMapping("/editprofile")
	public ResponseEntity<PatientResponse> editProfile(@RequestBody PatientRequest req){
		
		// it can call the same method as signUp too
		PatientResponse patient = this.patientService.updateProfile(req);
		
		return new ResponseEntity<>(patient,HttpStatus.OK);
	}
	
	@PostMapping("/bookappointment")
	public ResponseEntity<AppointmentResponse> BookAppointment(@RequestBody AppointmentBookRequest req) {
		// request should include appointment day , doctor_id and patient_id
		AppointmentResponse response = this.appointementService.bookAppointment(req);
		
		return new ResponseEntity<>(response,HttpStatus.CREATED) ;
	}
	
	// Doctor Views
	
	@GetMapping("/viewdoctors")
	public List<Doctor> ViewDoctors() {
		return docService.getAllDoctors();
	}
	
	@GetMapping("/{id}/availability")
	public ResponseEntity<List<AvailableTime>> getDoctorAvailability(@PathVariable Integer id) {
		Optional<Doctor> doctorOptional = docService.getDoctorById(id);

		if (doctorOptional.isPresent()) {
			Doctor doctor = doctorOptional.get();
			List<AvailableTime> availability = availableservice.getAvailabilityByDoctor(doctor);
			return ResponseEntity.ok(availability);
		} else {
			 new ResourceNotFoundException("Doctor not found");
			 return null;
		}
	}
	@GetMapping("/filter")
	public List<Doctor> filterDoctors(
			@RequestParam(required=false) String name,
			@RequestParam(required=false) String specialization,
			@RequestParam(required=false) String address,
			@RequestParam(required=false) String gender
			){
		return docService.filterDoctors(name, specialization, gender, address);
	}
	
}
