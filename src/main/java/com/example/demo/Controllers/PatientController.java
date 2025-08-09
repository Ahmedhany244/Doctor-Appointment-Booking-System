package com.example.demo.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DataTransferObjects.AppointmentBookRequest;
import com.example.demo.DataTransferObjects.AppointmentCancelRequest;
import com.example.demo.DataTransferObjects.AppointmentResponse;
import com.example.demo.DataTransferObjects.PatientRequest;
import com.example.demo.DataTransferObjects.PatientResponse;
import com.example.demo.Models.Appointment;
import com.example.demo.Services.AppointmentServices;
import com.example.demo.Services.PatientServices;

@RestController
@RequestMapping("/patients")
public class PatientController {
	
	private final PatientServices patientService;
	private final AppointmentServices appointementService;
	
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
	/*
	@GetMapping("/cancelappointment/{id}")
	public ResponseEntity<Appointment> cancelAppointment (@PathVariable Integer id){
	
		AppointmentCancelRequest req = new AppointmentCancelRequest();
		req.setAppointmentId(id);
		
		Appointment response = appointementService.cancelAppointment(req);
		
		return new ResponseEntity<>(response,HttpStatus.OK);
		
		
	}
	*/
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
}
