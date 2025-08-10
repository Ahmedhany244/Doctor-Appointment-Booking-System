package com.global.hr.patient.Services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyUpdatedException extends RuntimeException{
	
	public AlreadyUpdatedException(String e){
		 super(e);
	}
}

