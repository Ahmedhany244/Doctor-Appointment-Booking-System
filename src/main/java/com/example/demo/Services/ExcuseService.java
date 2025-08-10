package com.example.demo.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Models.Doctor;
import com.example.demo.Models.DoctorException;
import com.example.demo.Models.ExcuseId;
import com.example.demo.Repositories.ExcuseRepo;
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
