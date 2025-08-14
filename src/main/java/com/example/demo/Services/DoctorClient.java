package com.example.demo.Services;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.Models.AvailableTime;
import com.example.demo.Models.Doctor;

@Service
public class DoctorClient {

    private final RestTemplate restTemplate;

    public DoctorClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<AvailableTime> getAvailableTime(Integer doctorId) {
    	
    	// a hardcoded url (don't use preferably) 	WRONG URL !!!
    	
    	String url = "http://localhost:8082/{doctorId}/availability";
    	// or can be written/used as the method below	using "UriComponentsBuilder"


    	AvailableTime[] times = restTemplate.getForObject(url, AvailableTime[].class , doctorId);
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
   
    public List<Doctor> getAllDoctors(){
    	
        String url = "http://localhost:8082/doctors";

        Doctor[] doctors = restTemplate.getForObject(url, Doctor[].class);
        return Arrays.asList(doctors);
        
        // same as the method above about the case of HTTP Response
    	
    }

	@SuppressWarnings("deprecation")
	public List<Doctor> filterDoctors(String name, String specialization, String gender, String address) {
	    
		String url = UriComponentsBuilder
		        .fromHttpUrl("http://localhost:8082/filterdoctors")
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
    
}
