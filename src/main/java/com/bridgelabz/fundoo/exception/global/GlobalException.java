package com.bridgelabz.fundoo.exception.global;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bridgelabz.fundoo.exception.custom.LabelNotFoundException;
import com.bridgelabz.fundoo.exception.custom.NoteNotFoundException;
import com.bridgelabz.fundoo.exception.custom.TokenException;
import com.bridgelabz.fundoo.exception.custom.UserNotFoundException;
import com.bridgelabz.fundoo.response.Response;

@ControllerAdvice
public class GlobalException {

	@ExceptionHandler(TokenException.class)
	public ResponseEntity<Response> tokenException(Exception e) {
		return new ResponseEntity<Response>(new Response(401, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(LabelNotFoundException.class)
	public ResponseEntity<Response> labelNotFoundException(Exception e) {
		return new ResponseEntity<Response>(new Response(401, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(NoteNotFoundException.class)
	public ResponseEntity<Response> noteNotFoundException(Exception e) {
		return new ResponseEntity<Response>(new Response(401, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Response>userNotFoundException(Exception e){
		return new ResponseEntity<Response>(new Response(401, e.getMessage(), null),HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
