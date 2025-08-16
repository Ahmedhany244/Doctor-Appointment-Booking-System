package com.example.demo.Services;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.demo.DataTransferObjects.AppointmentBookRequest;
import com.example.demo.DataTransferObjects.AppointmentCancelRequest;
import com.example.demo.DataTransferObjects.AppointmentResponse;
import com.example.demo.Exception.AlreadyUpdatedException;
import com.example.demo.Exception.ResourceNotFoundException;
import com.example.demo.Models.Appointment;
import com.example.demo.Models.Doctor;
import com.example.demo.Models.Patient;
import com.example.demo.Models.Status;
import com.example.demo.Repositories.AppointmentRepository;
import com.example.demo.Repositories.PatientRepository;

import jakarta.transaction.Transactional;

@Service
public class AppointmentServices {

	private final AppointmentRepository appointmentRepo;
	private final ModelMapper modelMapper;
	private final PatientRepository patientRepo;
	private static final Logger logger = LoggerFactory.getLogger(AppointmentServices.class);
	private final DoctorClient docClient;
	
	public AppointmentServices(AppointmentRepository appointmentRepo,ModelMapper modelMapper
			, PatientRepository patientRepo, DoctorClient docClient) {
		this.appointmentRepo = appointmentRepo;
		this.modelMapper = modelMapper;
		this.patientRepo = patientRepo;
		this.docClient = docClient;
		
	}
	
	public AppointmentResponse cancelAppointment(AppointmentCancelRequest req) {
		Appointment appointment = appointmentRepo.findByAppointmentId(req.getAppointmentId())
				.orElseThrow(() -> new ResourceNotFoundException("Appointment Id not found"));
		
		if (appointment.getStatus().equals(Status.BOOKED))
				appointment.setStatus(Status.CANCELLED);
		else 
			 throw new AlreadyUpdatedException("Appointment was Cancelled Already");
		
		Appointment savedAppointment = appointmentRepo.save(appointment);
		
		return modelMapper.map(savedAppointment, AppointmentResponse.class);
		
	}
	@Transactional   // this is used for each sql to wait for the previous one (for multiple sqls)
	public AppointmentResponse bookAppointment(AppointmentBookRequest req){
	    // avoid mapper for foriegn key confusions
		
	    // 1. Get the patient first
	    Patient patient = patientRepo.findById(req.getPatientId())
	            .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
	    
	    //2- SHOULD ENSURE DOCTOR EXISTS FROM DOCTOR-SERVICE
	    Doctor doctor = docClient.findDoctorById(req.getDoctor_id());
	    if(doctor == null){
	    	throw new ResourceNotFoundException("Doctor not found");
	    }
	   
		// 3- SHOULD ENSURE MAXPATIENTS OF DOCTOR ,  RETURN FROM DOCTOR-SERVICE
	    Integer maxPatients = this.docClient.findMaxPatientOrder(req.getDoctor_id(), req.getAppointmentDay());
	    if(maxPatients == null) {
	    	throw new ResourceNotFoundException("max patients isnot configured");
	    }
	    
	    // 4. Get current order number
	    int order = this.appointmentRepo.findMaxPatientOrderBooked(req.getDoctor_id(), req.getAppointmentDay().toString());
	    if (order >= maxPatients) {
		    int cancelledOrder = this.appointmentRepo.findMinPatientOrderCancelled(req.getDoctor_id(),
		    		req.getAppointmentDay().toString()).orElseThrow( () ->
		    				new AlreadyUpdatedException("Doctor appointments are full")
		    				);
		    order = cancelledOrder-1;	// the -1 to be handled correctly below
	    }
	    logger.info("patient order = " + order);
	    
	    // 5- SHOULD ENSURE THERE IS NO SIMILAR RECORD FROM APPOINTMENTS INSTEAD OF DEPENDING ON DATABASE
	    
	    // 6. Create the new appointment entity
	    Appointment appointment = new Appointment();
	    appointment.setPatient(patient);
	    appointment.setDoctor_id(req.getDoctor_id());
	    appointment.setAppointmentDay(req.getAppointmentDay());
	    appointment.setPatientOrder((order + 1));
	    appointment.setStatus(Status.BOOKED);
	    
	    Appointment savedAppointment;
	    try {
	    	// 7. Save the new appointment   
	    	 savedAppointment = this.appointmentRepo.save(appointment);
	    }
	    catch(DataIntegrityViolationException e) {
	    	// --> what will happen if same doctor_id , same day
	    	throw new DataIntegrityViolationException("Patient already booked Doctor");
	    }
	    return modelMapper.map(savedAppointment, AppointmentResponse.class);
	}
	
}
