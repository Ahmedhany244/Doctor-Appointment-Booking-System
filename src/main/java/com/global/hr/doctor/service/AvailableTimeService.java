package com.global.hr.doctor.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.global.hr.doctor.entity.AvailableTime;
import com.global.hr.doctor.entity.Doctor;
import com.global.hr.doctor.repository.AvailableTimeRepository;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
@Service
public class AvailableTimeService {
	// menf3sh final m3 auto l2n auto injection dh b3d m3 create lkn final deh w enta btcreate fi constructor
	@Autowired
	private  AvailableTimeRepository availableTimeRepository;
	
	    public AvailableTime save(AvailableTime availableTime) {
	        return availableTimeRepository.save(availableTime);
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

}
