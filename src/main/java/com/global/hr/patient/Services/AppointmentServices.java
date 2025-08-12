package com.global.hr.patient.Services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.global.hr.doctor.entity.AvailableTime;
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


	    // 1. Check doctor availability 
	    List<AvailableTime> times = this.timeService.getAvailabilityByDoctorId(req.getDoctor_id());
	    boolean available = times.stream()
	            .anyMatch(time -> time.getDayOfWeek().toString() == req.getAppointmentDay().toString());
	    
	    if(available == false) {
	    	throw new ResourceNotFoundException("Doctor not found or not available on this day");
	    }
	    logger.info("Doctor available");
	    logger.info(req.getAppointmentDay()+ " " + req.getDoctor_id() + " " + req.getPatientId());
	    

	    // 2. Check max patients
	    Integer maxPatients = this.timeService.DoctorMaxPatients(req.getDoctor_id(), req.getAppointmentDay().toString());
	    
	    if (maxPatients == null) {
	        throw new IllegalStateException("Max patients for doctor is not configured");
	    }
	    logger.info("Max patients = " + maxPatients);
	    
	    // 3. Get current order number
	    int order = this.appointmentRepo.findMaxPatientOrderBooked(req.getDoctor_id(), req.getAppointmentDay().toString());
	    if (order >= maxPatients) {
		    int cancelledOrder = this.appointmentRepo.findMinPatientOrderCancelled(req.getDoctor_id(),
		    		req.getAppointmentDay().toString()).orElseThrow( () ->
		    				new AlreadyUpdatedException("Doctor appointments are full")
		    				);
		    order = cancelledOrder-1;	// the -1 to be handled correctly below
	    }
	    logger.info("patient order = " + order);
	    
	    // 4. Create the new appointment entity
	    Appointment appointment = new Appointment();
	    appointment.setPatient(patient);
	    appointment.setDoctor_id(req.getDoctor_id());
	    appointment.setAppointmentDay(req.getAppointmentDay());
	    appointment.setPatientOrder((order + 1));
	    appointment.setStatus(Status.BOOKED);
	    
	    Appointment savedAppointment;
	    try {
	    	// 5. Save the new appointment   
	    	 savedAppointment = this.appointmentRepo.save(appointment);
	    }
	    catch(DataIntegrityViolationException e) {
	    	// --> what will happen if same doctor_id , same day
	    	throw new DataIntegrityViolationException("Patient already booked Doctor");
	    }
	    return modelMapper.map(savedAppointment, AppointmentResponse.class);
	}
}
