package com.global.hr.admin.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.NOT_FOUND)
public class AdminNotFoundException  extends RuntimeException{

    public AdminNotFoundException(String e){
		 super(e);
	}
    
}






