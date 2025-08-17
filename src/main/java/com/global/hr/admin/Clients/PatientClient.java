package com.global.hr.admin.Clients;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.global.hr.admin.DTO.PatientRequest;
import com.global.hr.admin.DTO.PatientResponse;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

@Component
public class PatientClient {

    private final RestTemplate patientRestTemplate;
  

       public PatientClient(@Qualifier("patientRestTemplate") RestTemplate patientRestTemplate ) {
        this.patientRestTemplate = patientRestTemplate;
      
       }
    

    // Remove the constructor, as field injection is used


    public PatientResponse createPatient(PatientRequest request ) {
        String url = "http://PATIENT_SERVICE/patients/signup";

        // used entity to save the status code for any error and exception handling later on 
       return   patientRestTemplate.postForObject(url, request, PatientResponse.class);
        
    }

    public void deletePatient(Integer id) {
        String url = "http://PATIENT_SERVICE/patients/patients?id=" +id;
        
        patientRestTemplate.delete(url);
    }

    public PatientResponse updatePatient( PatientRequest request){
        String url = "http://PATIENT_SERVICE/patients/editprofile";
        ResponseEntity<PatientResponse> response = patientRestTemplate.postForEntity(url,request, PatientResponse.class);
        return response.getBody();
    }
}