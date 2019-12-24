package com.bridgelabz.fundoo.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.fundoo.dto.ForgetPasswordDto;
import com.bridgelabz.fundoo.dto.LoginDto;
import com.bridgelabz.fundoo.dto.RegistrationDto;
import com.bridgelabz.fundoo.dto.ResetPasswordDto;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.services.UserServiceImpl;

@RestController
@RequestMapping("/api")
public class UserLoginController {

	@Autowired
	UserServiceImpl serviceimpl;

	@PostMapping("/adduser")
	public ResponseEntity<Response> addUser(@Valid @RequestBody RegistrationDto registrationdto) {
//		String result = serviceimpl.addUser(registrationdto);
//		System.out.println("result:::" + result);
		return new ResponseEntity<Response>(serviceimpl.addUser(registrationdto),HttpStatus.OK);
	}
	
	

	@PostMapping("/login")
	public ResponseEntity<Response> loginUser(@Valid @RequestBody LoginDto logindto,@RequestParam String token) {
//		String result = serviceimpl.loginUser(logindto,token);
//		System.out.println("login successful"+result);
		return new ResponseEntity<Response>(serviceimpl.loginUser(logindto,token),HttpStatus.OK);
	}
	
	
	@PostMapping("/forgetPassword")
	public ResponseEntity<Response> forgetPassword(@Valid @RequestBody ForgetPasswordDto forgetpassword) {
	return new ResponseEntity<Response>(serviceimpl.forgetPassword(forgetpassword), HttpStatus.OK);
	}
	
	
	@PostMapping("/resetPassword")
	
	public ResponseEntity<Response> resetPassword(@Valid @RequestBody ResetPasswordDto resetpassword,String token){
		return new ResponseEntity<Response>(serviceimpl.resetPassword(resetpassword,token), HttpStatus.OK);
	}
}




