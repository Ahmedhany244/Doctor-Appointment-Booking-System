package com.global.hr.patient.Services;


import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.global.hr.patient.DataTransferObjects.PatientRequest;
import com.global.hr.patient.DataTransferObjects.PatientResponse;
import com.global.hr.patient.Exception.ResourceNotFoundException;
import com.global.hr.patient.Models.Appointment;
import com.global.hr.patient.Models.Patient;
import com.global.hr.patient.Repositories.PatientRepository;


@Service
public class PatientServices {
	
	private final PatientRepository patientRepo;
	private final ModelMapper modelMapper;
	
	public PatientServices(PatientRepository patientRepo , ModelMapper modelMapper) {
		this.patientRepo = patientRepo ;
		this.modelMapper = modelMapper ;
	}
		
	public PatientResponse signUp(PatientRequest patientReq) {
		Patient patient = modelMapper.map(patientReq, Patient.class);
				
		Patient savedPatient = patientRepo.save(patient);
		
		return modelMapper.map(savedPatient, PatientResponse.class);
	}

	public PatientResponse login(PatientRequest req) {
	
		modelMapper.getConfiguration().setSkipNullEnabled(true);
		Patient patient = modelMapper.map(req, Patient.class);
		
		// should be password instead of name and should also verify passowrd 
		Patient savedPatient = patientRepo.findByEmailAndName(patient.getEmail(), patient.getName())
				.orElseThrow(() -> new ResourceNotFoundException("Patient not found"));;
		
		/* OR
		  if(Optional<Patient> savedPatient.present) return savedPatient.get()
		  else throw Exception
		  */
		
		/*List<Appointment> list = patient.getAppointments(); // 💥 could trigger LazyInitializationException
		 instead , used @EntityGraph in the method definition in the Repo
		*/
		
		return modelMapper.map(savedPatient, PatientResponse.class);
		
		
	}
	public PatientResponse updateProfile(PatientRequest req) {
	    Patient existingPatient = patientRepo.findById(req.getId())
	        .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

	    modelMapper.getConfiguration().setSkipNullEnabled(true);
	    modelMapper.map(req, existingPatient);

	    Patient savedPatient = patientRepo.save(existingPatient);
	    return modelMapper.map(savedPatient, PatientResponse.class);
	}


	public PatientResponse updateProfile(Integer id,PatientRequest req ){
		 Patient existingPatient = patientRepo.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
			 modelMapper.getConfiguration().setSkipNullEnabled(true);
	    	modelMapper.map(req, existingPatient);
			 Patient savedPatient = patientRepo.save(existingPatient);
			return modelMapper.map(savedPatient, PatientResponse.class);

	}

	public List<Appointment> getPatientAppointments(Integer id) {
	    Patient patient = patientRepo.findById(id)
		        .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
		return patient.getAppointments();
	}

	public void deletePatient(Integer id) {
	    if (!patientRepo.existsById(id)) {
	        throw new ResourceNotFoundException("Patient with ID " + id + " not found");
	    }
	    patientRepo.deleteById(id);
	}
		
	public void deletePatientById(int id) {
    Patient patient = patientRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id " + id));
    
    patientRepo.delete(patient);
}

public PatientRepository getPatientRepository(){
	return this.patientRepo;
}

}
