package idv.tim.springrest.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import idv.tim.springrest.Exception.ContactNotFoundException;

@ControllerAdvice 
public class GlobalControllerIntercepter {
	
	@ExceptionHandler(ContactNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public Error contactNotFound(ContactNotFoundException e) {
		String contactId = e.getContactId();
		return new Error("Contact [" + contactId + "] not found");
	}

}
