package com.global.hr.doctor.DTO;

import org.springframework.format.annotation.NumberFormat;

import com.global.hr.patient.Models.Gender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;


public class DoctorRequest {

    @NotBlank(message = "Name field is required")
     private String name;

        @NotBlank(message = "specialization field is required")
	    private String specialization;

     @NotNull(message = "Gender is required and must be either 'M' or 'F'" )
	    private Gender gender;

    @NotBlank(message = "Phone number is required.")
    @NumberFormat
    @PositiveOrZero
    @Size (min = 11 , max = 11, message = "Phone number must be 11 digits.")
	    private String phone;

    @NotBlank(message = "Email field is required")
    @Email(message = "Must add a valid email")
	    private String email;


	    private String address;

        public String getName() {
            return name;
        }
        public String getSpecialization() {
            return specialization;
        }
        public Gender getGender() {
            return gender;
        }
        public String getPhone() {
            return phone;
        }
        public String getEmail() {
            return email;
        }
        public String getAddress() {
            return address;
        }

        
}
