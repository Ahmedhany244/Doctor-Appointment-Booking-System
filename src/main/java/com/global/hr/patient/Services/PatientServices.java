package com.global.hr.patient.Services;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.global.hr.patient.DataTransferObjects.PatientRequest;
import com.global.hr.patient.DataTransferObjects.PatientResponse;
import com.global.hr.patient.Exception.ResourceNotFoundException;
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
	    Patient existingPatient = patientRepo.findByEmail(req.getEmail())
	        .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

	    modelMapper.getConfiguration().setSkipNullEnabled(true);
	    modelMapper.map(req, existingPatient);

	    Patient savedPatient = patientRepo.save(existingPatient);
	    return modelMapper.map(savedPatient, PatientResponse.class);
	}

}
