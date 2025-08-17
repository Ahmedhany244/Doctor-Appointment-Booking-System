package com.global.hr.admin.Clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.global.hr.admin.DTO.DoctorRequest;
import com.global.hr.admin.DTO.DoctorResponse;

import org.springframework.http.HttpMethod;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Component
public class DoctorClient {
    
   
    
    private  final String doctor_service ;

     private final RestTemplate doctorRestTemplate;

    public DoctorClient(@Qualifier("doctorRestTemplate") RestTemplate doctorRestTemplate,  @Value("${doctor.service.base-url}") String url) {
        this.doctorRestTemplate = doctorRestTemplate;
        this.doctor_service = url ;
    }

   

    public DoctorResponse addDoctor(DoctorRequest request) {
        return doctorRestTemplate.postForObject(doctor_service, request, DoctorResponse.class);
    }

    public DoctorResponse updateDoctor(Integer id, DoctorRequest request) {
        String url = doctor_service + "/" + id;
         ResponseEntity<DoctorResponse> response = doctorRestTemplate.exchange(
            url,
            HttpMethod.PUT,
            new HttpEntity<>(request),
            DoctorResponse.class
    );

    return response.getBody();

    }

    public void deleteDoctor(int id) {
        String url = doctor_service + "/" + id;
        doctorRestTemplate.delete(url);
    }
}