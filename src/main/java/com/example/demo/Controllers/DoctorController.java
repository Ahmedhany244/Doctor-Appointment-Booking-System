package com.example.demo.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Models.AvailableTime;
import com.example.demo.Models.Day;
import com.example.demo.Models.Doctor;
import com.example.demo.Models.DoctorException;
import com.example.demo.Models.ExcuseId;
import com.example.demo.Services.AvailableTimeService;
import com.example.demo.Services.DoctorService;
import com.example.demo.Services.ExcuseService;

@RestController
@RequestMapping("/doctors")

public class DoctorController {
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private AvailableTimeService availableservice;
	@Autowired
	private ExcuseService excuseservice;

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

	// Create new doctor
	@PostMapping
	public Doctor createDoctor(@RequestBody Doctor doctor) {
		return doctorService.saveDoctor(doctor);
	}

	// Update existing doctor
	@PutMapping("/{id}")
	public Doctor updateDoctor(@PathVariable Integer id, @RequestBody Doctor doctor) {
		return doctorService.updateDoctor(id, doctor);
	}

	// Delete doctor by ID
	@DeleteMapping("/{id}")
	public void deleteDoctor(@PathVariable Integer id) {
		doctorService.deleteDoctor(id);
	}

	@PostMapping("/{id}/availability")
	public ResponseEntity<AvailableTime> addavailabletime(@PathVariable Integer id,
			@RequestBody AvailableTime availabletime) {
		Optional<Doctor> doctorOpt = doctorService.getDoctorById(id);

		if (doctorOpt.isPresent()) {
			availabletime.setDoctor(doctorOpt.get());
			AvailableTime saved = availableservice.save(availabletime);
			return ResponseEntity.status(HttpStatus.CREATED).body(saved);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/availability/{id}")
	public ResponseEntity<AvailableTime> updateAvailableTime(@PathVariable Integer id,
			@RequestBody AvailableTime availableTime) {
		return availableservice.updateAvailabletime(id, availableTime);

	}

	@DeleteMapping("/availability/{id}")
	public ResponseEntity<Void> deleteAvailableTime(@PathVariable Integer id) {
		if (availableservice.findById(id).isPresent()) {
			availableservice.deleteById(id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/{id}/availability")
	public ResponseEntity<List<AvailableTime>> getDoctorAvailability(@PathVariable Integer id) {
		Optional<Doctor> doctorOptional = doctorService.getDoctorById(id);

		if (doctorOptional.isPresent()) {
			Doctor doctor = doctorOptional.get();
			List<AvailableTime> availability = availableservice.getAvailabilityByDoctor(doctor);
			return ResponseEntity.ok(availability);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/{doctorId}/excuses")
	public ResponseEntity<DoctorException> addExcuse(@PathVariable Integer doctorId,
			@RequestBody DoctorException excuse) {
		Optional<Doctor> doctorOptional = doctorService.getDoctorById(doctorId);

		if (doctorOptional.isPresent()) {
			Doctor doctor = doctorOptional.get();
			// Create the composite key properly
	        if (excuse.getId() == null) {
	            excuse.setId(new ExcuseId());
	        }
	        
	        // Set the doctor ID in the composite key
	        excuse.getId().setDoctorid(doctorId);
	        
	        // Ensure excuseDay is set (this should come from request body)
	        if (excuse.getId().getExcuseDay() == null) {
	            return ResponseEntity.badRequest().build(); // or throw exception
	        }
			
			excuse.setDoctor(doctor);
			DoctorException saved = excuseservice.save(excuse);
			return ResponseEntity.status(HttpStatus.CREATED).body(saved);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/excuses/{doctorId}/{excuseDay}")
	public ResponseEntity<Void> deleteExcuse(
	        @PathVariable Integer doctorId,
	        @PathVariable Day excuseDay) {

	    ExcuseId excuseId = new ExcuseId(doctorId, excuseDay);

	    if (excuseservice.getExcusesById(excuseId).isPresent()) {
	        excuseservice.delete(excuseId);
	        return ResponseEntity.noContent().build();
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	@GetMapping("/{doctorId}/excuses")
    public ResponseEntity<List<DoctorException>> getExcuses(@PathVariable Integer doctorId) {
		Optional<Doctor> doctorOptional = doctorService.getDoctorById(doctorId);
		if (doctorOptional.isPresent()) {
			Doctor doctor = doctorOptional.get();
			List<DoctorException> excuses = excuseservice.getExcusesByDoctor(doctor);
			return ResponseEntity.ok(excuses);
			
		}
        
        return ResponseEntity.notFound().build();
    }
}