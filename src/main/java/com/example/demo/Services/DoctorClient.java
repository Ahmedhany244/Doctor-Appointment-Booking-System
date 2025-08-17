package com.example.demo.Services;

import java.util.Arrays;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.Exception.ResourceNotFoundException;
import com.example.demo.Models.AvailableTime;
import com.example.demo.Models.Day;
import com.example.demo.Models.Doctor;

@Service
public class DoctorClient {

    private final RestTemplate restTemplate;

    public DoctorClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<AvailableTime> getDoctorAvailableTime(Integer doctorId) {
    	
    	// a hardcoded url (don't use preferably) 	WRONG URL !!!
    	
    	String url = "http://DOCTOR-SERVICE/doctors/{doctorId}/availability";
    	// or can be written/used as the method below	using "UriComponentsBuilder"


    	AvailableTime[] times = restTemplate.getForObject(url, AvailableTime[].class , doctorId);
    	if(times == null)
    		throw new ResourceNotFoundException("available time is null");
    	return Arrays.asList(times);
        
        /*	can also use this if the returned a ResponseEntity (Http Response) as it should
           ResponseEntity<List<AvailableTime>> response = restTemplate.exchange(
	        url,
	        HttpMethod.GET,
	        null,
	        new ParameterizedTypeReference<List<AvailableTime>>() {}
	    );
	
	    return response.getBody(); 
         */
    }
   
    public List<Doctor> getAllDoctors(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<List<Doctor>> response = restTemplate.exchange(
            "http://DOCTOR-SERVICE/doctors",
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference<List<Doctor>>() {}
        );

        return response.getBody();
    }
    
    public List<Doctor> getAllDoctors(){
    	
        String url = "http://DOCTOR-SERVICE/doctors";

        Doctor[] doctors = restTemplate.getForObject(url, Doctor[].class);
        return Arrays.asList(doctors);
        
        // same as the method above about the case of HTTP Response
    	
    }

	public List<Doctor> filterDoctors(String name, String specialization, String gender, String address) {
	    
		String url = UriComponentsBuilder
		        .fromHttpUrl("http://DOCTOR-SERVICE/doctors/filterdoctors")
		        .queryParam("name", name)
		        .queryParam("specialization", specialization)
		        .queryParam("gender", gender)
		        .queryParam("address", address)
		        .toUriString();
		/*
		String url = "http://localhost:8082/filterdoctors?name=" + URLEncoder.encode(name, StandardCharsets.UTF_8)
        + "&specialization=" + URLEncoder.encode(specialization, StandardCharsets.UTF_8)
        + "&gender=" + URLEncoder.encode(gender, StandardCharsets.UTF_8)
        + "&address=" + URLEncoder.encode(address, StandardCharsets.UTF_8);4
        */
	    Doctor[] doctors = restTemplate.getForObject(url, Doctor[].class);
	    return Arrays.asList(doctors);
	}
	
	// from appointment service 
	
	public Doctor findDoctorById(Integer id) {
		
		String url = "http://DOCTOR-SERVICE/doctors/{id}";
    	return restTemplate.getForObject(url, Doctor.class , id);	
	}
	
	public Integer findMaxPatientOrder(Integer id , Day appointmentDay) {
		String url = UriComponentsBuilder
        .fromHttpUrl("http://DOCTOR-SERVICE/doctors/findmaxpatients")
        .queryParam("id", id)
        .queryParam("available_day", appointmentDay.toString())
        .toUriString();
		
		return restTemplate.getForObject(url, Integer.class);
		
	}
	
    
}
