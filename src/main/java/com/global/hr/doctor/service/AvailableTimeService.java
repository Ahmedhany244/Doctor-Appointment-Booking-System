package com.global.hr.doctor.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.global.hr.doctor.entity.AvailableTime;
import com.global.hr.doctor.entity.Doctor;
import com.global.hr.doctor.entity.DoctorException;
import com.global.hr.doctor.repository.AvailableTimeRepository;
import com.global.hr.patient.Exception.ResourceNotFoundException;
import com.global.hr.patient.Models.Day;
@Service
public class AvailableTimeService {
	// menf3sh final m3 auto l2n auto injection dh b3d m3 create lkn final deh w enta btcreate fi constructor
	@Autowired
	private  AvailableTimeRepository availableTimeRepository;
	
	@Autowired
	private DoctorService docService;
	
	@Autowired
	private ExcuseService excuseService;
	
	    public AvailableTime save(AvailableTime availableTime) {
	        return availableTimeRepository.save(availableTime);
	    }

	    public List<AvailableTime> findAll() {
	        return availableTimeRepository.findAll();
	    }
	    public List<AvailableTime> getAvailabilityByDoctor(Doctor doctor) {
	        List<AvailableTime> times = availableTimeRepository.findByDoctor(doctor);

	        //Get doctor excuses
	        List<DoctorException> excuses = excuseService.getExcusesByDoctor(doctor);

	        //Filter out times where doctor has an excuse
	        return times.stream()
	                .filter(time -> excuses.stream()
	                    .noneMatch(excuse -> excuse.getExcuseDay().equals(time.getDayOfWeek())))
	                .toList();
	    }
	    public List<AvailableTime> getAvailabilityByDoctorId(Integer id) {
	    	
	    	Doctor doctor = this.docService.getDoctorById(id).orElseThrow(() 
	    			-> new ResourceNotFoundException("No Doctor with id " + id));
	    			
	    	
	        List<AvailableTime> times = availableTimeRepository.findByDoctor(doctor);

	        //Get doctor excuses
	        List<DoctorException> excuses = excuseService.getExcusesByDoctor(doctor);

	        //Filter out times where doctor has an excuse
	        return times.stream()
	                .filter(time -> excuses.stream()
	                    .noneMatch(excuse -> excuse.getExcuseDay().equals(time.getDayOfWeek())))
	                .toList();
	    }

	    public Optional<AvailableTime> findById(Integer id) {
	        return availableTimeRepository.findById(id);
	    }
		public ResponseEntity<AvailableTime> updateAvailabletime(Integer id, AvailableTime updatedtime) {
			Optional<AvailableTime> optionaltime = availableTimeRepository.findById(id);

			if (optionaltime.isPresent()) {
				AvailableTime existingtime = optionaltime.get();
				existingtime.setDayOfWeek(updatedtime.getDayOfWeek());
				existingtime.setStartTime(updatedtime.getStartTime());
				existingtime.setEndTime(updatedtime.getEndTime());
				existingtime.setMax_Patients(updatedtime.getMax_Patients());
				AvailableTime time= availableTimeRepository.save(existingtime); // save updated doctor
				return ResponseEntity.ok(time);
			} else {
				return ResponseEntity.notFound().build();
			}
		}

	    public void deleteById(Integer id) {
	        availableTimeRepository.deleteById(id);
	    }
	     
	    public Integer DoctorMaxPatients(Integer id, String day) {
	    	 return this.availableTimeRepository.findMaxPatients(id, day);
	    }
	    
	    
}
