package com.global.hr.admin.DTO;

import javax.xml.validation.*;

import org.springframework.format.annotation.NumberFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class AdminRequest {
    
    @NotBlank(message = "Username field is required")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    private String address;

    @NotBlank(message = "Email field is required")
    @Email(message = "Must add a valid email")
    private String email; 

    @NotBlank(message = "Phone number is required.")
    @NumberFormat
    @PositiveOrZero
    @Size (min = 11 , max = 11, message = "Phone number must be 11 digits.")
    private String phone;

    public String getUsername() {
        return username;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    } 


}
