package com.global.hr.admin.controller;

import com.global.hr.admin.entity.Admin;
import com.global.hr.admin.service.AdminService;
import com.global.hr.doctor.entity.Doctor;
import com.global.hr.patient.DataTransferObjects.PatientRequest;
import com.global.hr.patient.DataTransferObjects.PatientResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // ===== Admin CRUD =====
    @PostMapping("/create")
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {
        return new ResponseEntity<>(adminService.createAdmin(admin), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Admin>> getAllAdmins() {
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable int id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }

    // ===== Doctor Management =====
    @PostMapping("/adddoctor")
    public ResponseEntity<Doctor> addDoctor(@RequestBody Doctor doctor) {
        return new ResponseEntity<>(adminService.addDoctor(doctor), HttpStatus.CREATED);
    }

    @PutMapping("/updatedoctor/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Integer id, @RequestBody Doctor doctor) {
        return ResponseEntity.ok(adminService.updateDoctor(id, doctor));
    }

    @DeleteMapping("/deletedoctor/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable int id) {
        adminService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }

    // ===== Patient Management =====
    @PostMapping("/addpatient")
    public ResponseEntity<PatientResponse> addPatient(@RequestBody PatientRequest request) {
        return new ResponseEntity<>(adminService.addPatient(request), HttpStatus.CREATED);
    }

    @PutMapping("/updatepatient/{id}")
    public ResponseEntity<PatientResponse> updatePatient(@PathVariable Integer id, @RequestBody PatientRequest request){
        return ResponseEntity.ok(adminService.updatePatient(id, request));
    }

    @DeleteMapping("/deletepatient/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable int id) {
        adminService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
