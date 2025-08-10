package com.global.hr.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.global.hr.entity.Doctor;
import com.global.hr.entity.DoctorException;
import com.global.hr.entity.ExcuseId;
import com.global.hr.repository.ExcuseRepo;
@Service
public class ExcuseService {
	
	@Autowired
    private ExcuseRepo excuseRepository;

    public DoctorException save(DoctorException excuse) {
        return excuseRepository.save(excuse);
    }

    public void delete(ExcuseId id) {
        excuseRepository.deleteById(id);
    }

    public List<DoctorException> getExcusesByDoctor(Doctor doctor) {
        return excuseRepository.findByDoctor(doctor);
    }
    public Optional<DoctorException> getExcusesById(ExcuseId id) {
        return excuseRepository.findById(id);
    }
    

}
