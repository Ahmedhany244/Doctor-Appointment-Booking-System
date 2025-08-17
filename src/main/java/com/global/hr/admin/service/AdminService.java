package com.global.hr.admin.service;

import com.global.hr.admin.Clients.DoctorClient;
import com.global.hr.admin.Clients.PatientClient;
import com.global.hr.admin.DTO.AdminRequest;
import com.global.hr.admin.DTO.AdminResponse;
import com.global.hr.admin.DTO.DoctorRequest;
import com.global.hr.admin.DTO.DoctorResponse;
import com.global.hr.admin.DTO.PatientRequest;
import com.global.hr.admin.DTO.PatientResponse;
import com.global.hr.admin.Exception.AdminNotFoundException;
import com.global.hr.admin.Exception.DuplicateEntryException;
import com.global.hr.admin.entity.Admin;
import com.global.hr.admin.repository.AdminRepository;



import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class AdminService {

   

    private final AdminRepository adminRepo;
    private final ModelMapper modelMapper;
    private final PatientClient patientClient;
    private final DoctorClient doctorClient;


   public AdminService(AdminRepository adminRepo, 
                        ModelMapper modelMapper,
                        PatientClient patientClient,
                        DoctorClient doctorClient) {
        this.adminRepo = adminRepo;
        this.modelMapper = modelMapper;
        this.patientClient = patientClient;
        this.doctorClient = doctorClient;
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
    public DoctorResponse addDoctor(DoctorRequest doctor_req) {
    //    Doctor doctor = modelMapper.map(doctor_req, Doctor.class);
    //     Doctor saved_doc =  doctorService.saveDoctor(doctor);
    //     return modelMapper.map(saved_doc, DoctorResponse.class);
    return doctorClient.addDoctor(doctor_req);
    }

    public DoctorResponse updateDoctor(Integer id, DoctorRequest doctor_req) {
        // Doctor doctor  = modelMapper.map(doctor_req, Doctor.class);
        // Doctor updated_doctor = doctorService.updateDoctor(id, doctor);
        // return modelMapper.map(updated_doctor, DoctorResponse.class );
        return doctorClient.updateDoctor(id, doctor_req);
    }

    public void deleteDoctor(int id) {

        // Optional<Doctor> doctor = doctorService.getDoctorById(id);
        // if (doctor.isEmpty()){
        //     throw new DoctorNotFoundException("Couldn't delete the doctor as no doctor with id: "+ id + " is found in the database.");
        // }
        // avalable_time_service.deleteAllRecordsByDoctorId(id);
        // excuseService.deleteAllRecordsByDoctorId(id);
        // appointmentService.deleteAllRecordsByDoctorId(id);
        
        // doctorService.deleteDoctor(id);
         doctorClient.deleteDoctor(id);
    }

    // ===== Patient Management =====
    public PatientResponse addPatient(PatientRequest request) {
        // Patient patient = modelMapper.map(request, Patient.class);
        // Optional<Patient> savedPatient = patientService.getPatientRepository().findByEmailAndName(patient.getEmail(), patient.getName());
        // if (savedPatient.isPresent()){
        //     throw new DuplicateEntryException("Patient with name: "+ patient.getName() + " and email: "+ patient.getEmail() + " is already present.");
        // }
				
        // return patientService.signUp(request);
        return patientClient.createPatient(request);
    }

    public PatientResponse updatePatient( PatientRequest request) {
        // return patientService.updateProfile(id, request);
        return patientClient.updatePatient(request);
    }

    public void deletePatient(int id) {
        // appointmentService.deleteAllRecordsByPatient(id);
        // patientService.deletePatientById(id);
        patientClient.deletePatient(id);
    }
}
