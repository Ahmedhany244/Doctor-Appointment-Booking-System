package com.global.hr.admin.service;

import com.global.hr.admin.entity.Admin;
import com.global.hr.admin.repository.AdminRepository;
import com.global.hr.doctor.entity.Doctor;
import com.global.hr.doctor.service.DoctorService;
import com.global.hr.patient.DataTransferObjects.PatientRequest;
import com.global.hr.patient.DataTransferObjects.PatientResponse;
import com.global.hr.patient.Services.PatientServices;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AdminService {

    private final AdminRepository adminRepo;
    private final DoctorService doctorService;
    private final PatientServices patientService;

    public AdminService(AdminRepository adminRepo, DoctorService doctorService, PatientServices patientService) {
        this.adminRepo = adminRepo;
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    // ===== Admin CRUD =====
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
        doctorService.deleteDoctor(id);
    }

    // ===== Patient Management =====
    public PatientResponse addPatient(PatientRequest request) {
        return patientService.signUp(request);
    }

    public PatientResponse updatePatient(PatientRequest request) {
        return patientService.updateProfile(request);
    }

    public void deletePatient(int id) {
        patientService.deletePatientById(id);
    }
}
