package com.global.hr.admin.service;

import com.global.hr.admin.entity.Admin;
import com.global.hr.admin.repository.AdminRepository;
import com.global.hr.doctor.entity.Doctor;
import com.global.hr.doctor.repository.AvailableTimeRepository;
import com.global.hr.doctor.service.AvailableTimeService;
import com.global.hr.doctor.service.DoctorService;
import com.global.hr.doctor.service.ExcuseService;
import com.global.hr.patient.DataTransferObjects.PatientRequest;
import com.global.hr.patient.DataTransferObjects.PatientResponse;
import com.global.hr.patient.Services.AppointmentServices;
import com.global.hr.patient.Services.PatientServices;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final AdminRepository adminRepo;
    private final DoctorService doctorService;
    private final PatientServices patientService;
    public final AvailableTimeService avalable_time_service;
    public final ExcuseService excuseService;
    public final AppointmentServices appointmentService;

    public AdminService(AdminRepository adminRepo, DoctorService doctorService, PatientServices patientService, AvailableTimeService avalable_time_service, ExcuseService excuseService, AppointmentServices appointmentService) {
        this.adminRepo = adminRepo;
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.avalable_time_service = avalable_time_service;
        this.excuseService = excuseService;
        this.appointmentService = appointmentService;
    }   

    // ===== Admin CRUD =====

    public boolean validateAdmin(String username, String password) {
        Admin admin = adminRepo.findByUsername(username);
        return admin != null && admin.getPassword().equals(password);
    }
    
    public Admin createAdmin(Admin admin) {
        return adminRepo.save(admin);
    }

    public List<Admin> getAllAdmins() {
        return adminRepo.findAll();
    }

    public void deleteAdmin(int id) {
        adminRepo.deleteById(id);
    }

    // ===== Doctor Management =====
    public Doctor addDoctor(Doctor doctor) {
        return doctorService.saveDoctor(doctor);
    }

    public Doctor updateDoctor(Integer id, Doctor doctor) {
        return doctorService.updateDoctor(id, doctor);
    }

    public void deleteDoctor(int id) {
        avalable_time_service.deleteAllRecordsByDoctorId(id);
        excuseService.deleteAllRecordsByDoctorId(id);
        appointmentService.deleteAllRecordsByDoctorId(id);
        
        doctorService.deleteDoctor(id);
    }

    // ===== Patient Management =====
    public PatientResponse addPatient(PatientRequest request) {
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
