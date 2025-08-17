package com.global.hr.doctor.service;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.global.hr.patient.DataTransferObjects.AppointmentCancelRequest;
import com.global.hr.patient.DataTransferObjects.AppointmentResponse;

@Service
public class AppointmentClient {
	private final RestTemplate restTemplate;

	public AppointmentClient(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public AppointmentResponse deleteAppointment(AppointmentCancelRequest req) {
		String url = UriComponentsBuilder
		        .fromHttpUrl("http://PATIENT_SERVICE/patients/cancelappointment")
		        .queryParam("appointmentId", req.getAppointmentId())
		        .toUriString();
		        /*
		String url = "http://PATIENT_SERVICE/cancelappointment/" + req.getAppointmentId();
		*/
		ResponseEntity<AppointmentResponse> response = restTemplate.exchange(url, HttpMethod.PATCH, null,
				AppointmentResponse.class);

		return response.getBody();
	}
}
