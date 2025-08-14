package com.global.hr.admin.service;

import com.global.hr.admin.DTO.AdminRequest;
import com.global.hr.admin.DTO.AdminResponse;
import com.global.hr.admin.Exception.AdminNotFoundException;
import com.global.hr.admin.Exception.DoctorNotFoundException;
import com.global.hr.admin.Exception.DuplicateEntryException;
import com.global.hr.admin.entity.Admin;
import com.global.hr.admin.repository.AdminRepository;
import com.global.hr.doctor.entity.Doctor;
import com.global.hr.doctor.repository.AvailableTimeRepository;
import com.global.hr.doctor.service.AvailableTimeService;
import com.global.hr.doctor.service.DoctorService;
import com.global.hr.doctor.service.ExcuseService;
import com.global.hr.patient.DataTransferObjects.PatientRequest;
import com.global.hr.patient.DataTransferObjects.PatientResponse;
import com.global.hr.patient.Exception.ResourceNotFoundException;
import com.global.hr.patient.Models.Patient;
import com.global.hr.patient.Services.AppointmentServices;
import com.global.hr.patient.Services.PatientServices;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final AdminRepository adminRepo;
    private final DoctorService doctorService;
    private final PatientServices patientService;
    private final AvailableTimeService avalable_time_service;
    private final ExcuseService excuseService;
    private final AppointmentServices appointmentService;
    private final ModelMapper modelMapper;


    public AdminService(AdminRepository adminRepo, DoctorService doctorService, PatientServices patientService, AvailableTimeService avalable_time_service, ExcuseService excuseService, AppointmentServices appointmentService, ModelMapper modelMapper) {
        this.adminRepo = adminRepo;
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.avalable_time_service = avalable_time_service;
        this.excuseService = excuseService;
        this.appointmentService = appointmentService;
        this.modelMapper = modelMapper;
    }   

    // ===== Admin CRUD =====

    public boolean validateAdmin(String username, String password) {
        Admin admin = adminRepo.findByUsername(username);
        return admin != null && admin.getPassword().equals(password);
    }
    
    public AdminResponse createAdmin(AdminRequest admin_req) {
        if (adminRepo.existsByUsername(admin_req.getUsername())){
            throw new DuplicateEntryException("Username already exists");
        }
        Admin admin = modelMapper.map(admin_req, Admin.class);
        Admin new_admin =  adminRepo.save(admin);
        return modelMapper.map(new_admin, AdminResponse.class);
    }

    public List<AdminResponse> getAllAdmins() {
        List<Admin> all_admins = adminRepo.findAll();
        return modelMapper.map(all_admins, new org.modelmapper.TypeToken<List<AdminResponse>>(){}.getType());
    }

    public void deleteAdmin(int id) {
       if(!adminRepo.existsById(id)){
            throw new AdminNotFoundException("Couldn't delete the admin as no admin with id: "+ id + " is found in the database.");
       }
       else{
           adminRepo.deleteById(id);

       }
    }

    // ===== Doctor Management =====
    public Doctor addDoctor(Doctor doctor) {
       
        return doctorService.saveDoctor(doctor);
    }

    public Doctor updateDoctor(Integer id, Doctor doctor) {
        return doctorService.updateDoctor(id, doctor);
    }

    public void deleteDoctor(int id) {

        Optional<Doctor> doctor = doctorService.getDoctorById(id);
        if (doctor.isEmpty()){
            throw new DoctorNotFoundException("Couldn't delete the doctor as no doctor with id: "+ id + " is found in the database.");
        }
        avalable_time_service.deleteAllRecordsByDoctorId(id);
        excuseService.deleteAllRecordsByDoctorId(id);
        appointmentService.deleteAllRecordsByDoctorId(id);
        
        doctorService.deleteDoctor(id);
    }

    // ===== Patient Management =====
    public PatientResponse addPatient(PatientRequest request) {
        Patient patient = modelMapper.map(request, Patient.class);
        Optional<Patient> savedPatient = patientService.getPatientRepository().findByEmailAndName(patient.getEmail(), patient.getName());
        if (savedPatient.isPresent()){
            throw new DuplicateEntryException("Patient with name: "+ patient.getName() + " and email: "+ patient.getEmail() + " is already present.");
        }
				
        return patientService.signUp(request);
    }

    public PatientResponse updatePatient(Integer id, PatientRequest request) {
        return patientService.updateProfile(id, request);
    }

    public void deletePatient(int id) {
        appointmentService.deleteAllRecordsByPatient(id);
        patientService.deletePatientById(id);
    }
}
