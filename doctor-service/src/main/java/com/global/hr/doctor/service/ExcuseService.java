package com.global.hr.doctor.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.global.hr.doctor.entity.Doctor;
import com.global.hr.doctor.entity.DoctorException;
import com.global.hr.doctor.entity.ExcuseId;
import com.global.hr.doctor.repository.DoctorRepo;
import com.global.hr.doctor.repository.ExcuseRepo;
import com.global.hr.exception.ResourceNotFoundException;
import com.global.hr.patient.DataTransferObjects.DoctorExceptionRequestDTO;
import com.global.hr.patient.DataTransferObjects.DoctorExceptionResponseDTO;
@Service
public class ExcuseService {
	
	@Autowired
    private ExcuseRepo excuseRepository;
	@Autowired
    private DoctorService doctorService;
	@Autowired
	private DoctorRepo doctorRepo;

    public DoctorException save(DoctorException excuse) {
        return excuseRepository.save(excuse);
    }

    public  ResponseEntity<Void>  delete(ExcuseId id) {
    	if (excuseRepository.findById(id).isPresent()) {
    		excuseRepository.deleteById(id);
    		return ResponseEntity.noContent().build();
    	}
    	return ResponseEntity.notFound().build();
    	}
    public List<DoctorExceptionResponseDTO> getExcusesByDoctorId(Integer doctorId) {
        Doctor doctor = doctorService.getDoctorById(doctorId)
        .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID " + doctorId + " not found"));

        
            List<DoctorException> excuses = excuseRepository.findByDoctor(doctor);
            if (excuses.isEmpty()) {
                throw new ResourceNotFoundException("No excuses found for doctor with ID " + doctorId);
            }

            return excuses.stream()
                    .map(e -> new DoctorExceptionResponseDTO(
                            e.getId(),
                            e.getExcuseDay()  // assuming DayOfWeek Enum
                            
                    ))
                    .toList();
        
        
    }
    public DoctorExceptionResponseDTO addExcuse(Integer doctorId, DoctorExceptionRequestDTO request) {
        // 1. Find doctor or throw
        Doctor doctor = doctorRepo.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor with ID " + doctorId + " not found"));

        // 2. Create composite key
        ExcuseId excuseId = new ExcuseId();
        excuseId.setDoctorid(doctorId);
        excuseId.setExcuseDay(request.getExcuseDay());

        // 3. Create entity
        DoctorException doctorException = new DoctorException();
        doctorException.setId(excuseId);
        doctorException.setDoctor(doctor);

        // 4. Save to DB
        DoctorException saved = excuseRepository.save(doctorException);

        // 5. Map entity to response DTO
        return new DoctorExceptionResponseDTO(
                saved.getId(),
                saved.getId().getExcuseDay()
        );
    }
        
    

    public List<DoctorException> getExcusesByDoctor(Doctor doctor) {
        return excuseRepository.findByDoctor(doctor);
    }
    public Optional<DoctorException> getExcusesById(ExcuseId id) {
        return excuseRepository.findById(id);
    }
    

}
