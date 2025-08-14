package com.global.hr.doctor.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.global.hr.doctor.entity.Doctor;
import com.global.hr.doctor.entity.DoctorException;
import com.global.hr.doctor.entity.ExcuseId;
import com.global.hr.doctor.repository.ExcuseRepo;
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

    public void deleteAllRecordsByDoctorId(Integer id )
    {
         excuseRepository.removeByDoctor(id);
    }    

}
