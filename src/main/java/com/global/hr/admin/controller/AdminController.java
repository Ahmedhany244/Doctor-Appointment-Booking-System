package com.global.hr.admin.controller;

import com.global.hr.admin.DTO.AdminRequest;
import com.global.hr.admin.DTO.AdminResponse;
import com.global.hr.admin.DTO.DoctorRequest;
import com.global.hr.admin.DTO.DoctorResponse;
import com.global.hr.admin.DTO.PatientRequest;
import com.global.hr.admin.DTO.PatientResponse;
import com.global.hr.admin.Security.JwtUtil;
import com.global.hr.admin.service.AdminService;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Validated
@RestController
@RequestMapping("/admins")
public class AdminController {

    private final AdminService adminService;
     private final JwtUtil jwtUtil;



    public AdminController(AdminService adminService, JwtUtil jwtUtil) {
        this.adminService = adminService;
        this.jwtUtil = jwtUtil;
    }

    // ===== Admin CRUD =====
     @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AdminRequest admin) {

        boolean isValid = adminService.validateAdmin(admin.getUsername(), admin.getPassword());

        if (isValid) {
             jwtUtil.generateToken(admin.getUsername());
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed, Invalid credentials");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<AdminResponse> createAdmin( @Valid @RequestBody  AdminRequest admin_req) {
        
        return new ResponseEntity<>(adminService.createAdmin(admin_req), HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<AdminResponse>> getAllAdmins() {
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAdmin(@NotNull @NumberFormat @Positive @PathVariable int id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }

    // ===== Doctor Management =====

    // @GetMapping("/getDoctor/{id}")
    // public ResponseEntity<DoctorResponse> getAllDoctors(@NotNull @NumberFormat @Positive @PathVariable Integer id) {
    //     DoctorResponse doctor = restTemplate.getForObject("http://localhost:8081/doctors/6" + id, DoctorResponse.class);
    //      return new ResponseEntity<>( doctor , HttpStatus.CREATED);
    // }
    
    @PostMapping("/addDoctor")
    public ResponseEntity<DoctorResponse> addDoctor(@Valid @RequestBody DoctorRequest doctor_req) {
        return new ResponseEntity<>(adminService.addDoctor(doctor_req), HttpStatus.CREATED);
    }

    @PutMapping("/updateDoctor/{id}")
    public ResponseEntity<DoctorResponse> updateDoctor(@NotNull @NumberFormat @Positive @PathVariable Integer id, @Valid @RequestBody DoctorRequest doctor_req) {
        return ResponseEntity.ok(adminService.updateDoctor(id, doctor_req));
    }

    @DeleteMapping("/deleteDoctor/{id}")
    public ResponseEntity<Void> deleteDoctor(@NotNull @NumberFormat @Positive @PathVariable int id) {
        adminService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }

    // ===== Patient Management =====
    @PostMapping("/addPatient")
    public ResponseEntity<PatientResponse> addPatient(@Valid @RequestBody PatientRequest request) {
        return new ResponseEntity<>(adminService.addPatient(request), HttpStatus.CREATED);
    }

    @PutMapping("/updatePatient")
    public ResponseEntity<PatientResponse> updatePatient( @Valid @RequestBody PatientRequest request){
        return ResponseEntity.ok(adminService.updatePatient(request));
    }

    @DeleteMapping("/deletePatient/{id}")
    public ResponseEntity<Void> deletePatient(@NotNull @NumberFormat @Positive @PathVariable int id) {
        adminService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }


   
}
