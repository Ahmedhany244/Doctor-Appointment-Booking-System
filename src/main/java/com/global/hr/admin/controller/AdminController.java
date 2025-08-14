package com.global.hr.admin.controller;

import com.global.hr.admin.DTO.AdminRequest;
import com.global.hr.admin.DTO.AdminResponse;
import com.global.hr.admin.entity.Admin;
import com.global.hr.admin.service.AdminService;
import com.global.hr.doctor.entity.Doctor;
import com.global.hr.patient.DataTransferObjects.PatientRequest;
import com.global.hr.patient.DataTransferObjects.PatientResponse;

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

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // ===== Admin CRUD =====
     @PostMapping("/")
    public ResponseEntity<String> login(@RequestBody Admin admin) {

        boolean isValid = adminService.validateAdmin(admin.getUsername(), admin.getPassword());

        if (isValid) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
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
    @PostMapping("/addDoctor")
    public ResponseEntity<Doctor> addDoctor(@RequestBody Doctor doctor) {
        return new ResponseEntity<>(adminService.addDoctor(doctor), HttpStatus.CREATED);
    }

    @PutMapping("/updateDoctor/{id}")
    public ResponseEntity<Doctor> updateDoctor(@NotNull @NumberFormat @Positive @PathVariable Integer id, @RequestBody Doctor doctor) {
        return ResponseEntity.ok(adminService.updateDoctor(id, doctor));
    }

    @DeleteMapping("/deleteDoctor/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable int id) {
        adminService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }

    // ===== Patient Management =====
    @PostMapping("/addPatient")
    public ResponseEntity<PatientResponse> addPatient(@Valid @RequestBody PatientRequest request) {
        return new ResponseEntity<>(adminService.addPatient(request), HttpStatus.CREATED);
    }

    @PutMapping("/updatePatient/{id}")
    public ResponseEntity<PatientResponse> updatePatient(@NotNull @NumberFormat @Positive  @PathVariable Integer id, @Valid @RequestBody PatientRequest request){
        return ResponseEntity.ok(adminService.updatePatient(id, request));
    }

    @DeleteMapping("/deletePatient/{id}")
    public ResponseEntity<Void> deletePatient(@NotNull @NumberFormat @Positive @PathVariable int id) {
        adminService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }


   
}
