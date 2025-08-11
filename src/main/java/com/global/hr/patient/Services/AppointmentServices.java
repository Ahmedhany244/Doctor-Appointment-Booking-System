package com.global.hr.patient.Services;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.global.hr.doctor.service.AvailableTimeService;
import com.global.hr.patient.DataTransferObjects.AppointmentBookRequest;
import com.global.hr.patient.DataTransferObjects.AppointmentCancelRequest;
import com.global.hr.patient.DataTransferObjects.AppointmentResponse;
import com.global.hr.patient.Exception.AlreadyUpdatedException;
import com.global.hr.patient.Exception.ResourceNotFoundException;
import com.global.hr.patient.Models.Appointment;
import com.global.hr.patient.Models.Patient;
import com.global.hr.patient.Models.Status;
import com.global.hr.patient.Repositories.AppointmentRepository;
import com.global.hr.patient.Repositories.PatientRepository;

import jakarta.transaction.Transactional;

@Service
public class AppointmentServices {

	private final AppointmentRepository appointmentRepo;
	private final ModelMapper modelMapper;
	private final PatientRepository patientRepo;
	private static final Logger logger = LoggerFactory.getLogger(AppointmentServices.class);
	
	private final AvailableTimeService timeService;
	
	public AppointmentServices(AppointmentRepository appointmentRepo,ModelMapper modelMapper
			, PatientRepository patientRepo,AvailableTimeService timeService ) {
		this.appointmentRepo = appointmentRepo;
		this.modelMapper = modelMapper;
		this.patientRepo = patientRepo;
		this.timeService = timeService;
		
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
	    System.out.println("DoctorId: " + req.getDoctor_id());
	    System.out.println("AvailableDay: " + req.getAppointmentDay());


	    // 2. Find the current max order number within this transaction
	    int order = this.appointmentRepo.findMaxPatientOrder(req.getDoctor_id(), req.getAppointmentDay().name());

	    Integer max_patients = this.timeService.DoctorMaxPatients(req.getDoctor_id(), req.getAppointmentDay());
	    
	    if (max_patients != null) {
		    if (order < max_patients){ 
			    // 3. Create the new appointment entity
			    Appointment appointment = new Appointment();
			    appointment.setPatient(patient);
			    appointment.setDoctor_id(req.getDoctor_id());
			    appointment.setAppointmentDay(req.getAppointmentDay());
			    appointment.setPatientOrder((order + 1));
			    appointment.setStatus(Status.BOOKED);
			    
			    // 4. Save the new appointment
			    Appointment savedAppointment = this.appointmentRepo.save(appointment);
			    
			    return modelMapper.map(savedAppointment, AppointmentResponse.class);
		    }
		    else {
			    new AlreadyUpdatedException("Doctor appointments are full ");
			    return null;
		    }
	    }
	    new ResourceNotFoundException("Doctor not found");
	    return null;
	    
	}
}
