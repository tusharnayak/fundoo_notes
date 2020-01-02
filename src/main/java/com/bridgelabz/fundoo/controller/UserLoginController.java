package com.bridgelabz.fundoo.controller;

import java.io.IOException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.bridgelabz.fundoo.dto.ForgetPasswordDto;
import com.bridgelabz.fundoo.dto.LoginDto;
import com.bridgelabz.fundoo.dto.RegistrationDto;
import com.bridgelabz.fundoo.dto.ResetPasswordDto;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.services.UserService;

@RestController
@RequestMapping("/api")
public class UserLoginController {

	@Autowired
	private UserService serviceImpl;

	@PostMapping("/adduser")
	public ResponseEntity<Response> addUser(@Valid @RequestBody RegistrationDto registrationDto) {
//		String result = serviceimpl.addUser(registrationdto);
//		System.out.println("result:::" + result);
		return new ResponseEntity<Response>(serviceImpl.addUser(registrationDto),HttpStatus.OK);
	}
	
	

	@PostMapping("/login")
	public ResponseEntity<Response> loginUser(@Valid @RequestBody LoginDto loginDto,@RequestParam String token) {
//		String result = serviceimpl.loginUser(logindto,token);
//		System.out.println("login successful"+result);
		return new ResponseEntity<Response>(serviceImpl.loginUser(loginDto,token),HttpStatus.OK);
	}
	
	
	@PostMapping("/forgetPassword")
	public ResponseEntity<Response> forgetPassword(@Valid @RequestBody ForgetPasswordDto forgetpassword) {
	return new ResponseEntity<Response>(serviceImpl.forgetPassword(forgetpassword), HttpStatus.OK);
	}
	
	
	@PostMapping("/resetPassword")
	public ResponseEntity<Response> resetPassword(@Valid @RequestBody ResetPasswordDto resetpassword,String token){
		return new ResponseEntity<Response>(serviceImpl.resetPassword(resetpassword,token), HttpStatus.OK);
	}
	
	@PostMapping("/upload_profilepic")
	public ResponseEntity<Response>uploadProfilePic( @RequestParam String token,@RequestParam("file") MultipartFile file) throws IOException{
		return new ResponseEntity<Response>(serviceImpl.profilePic(token, file),HttpStatus.OK);
	}
	@PostMapping("/delete_profilepic")
	public ResponseEntity<Response>deleteProfilePic(@RequestParam String token){
		return new ResponseEntity<Response>(serviceImpl.deletePic(token),HttpStatus.OK);
	}
	@PostMapping("/edit_profilepic")
	public ResponseEntity<Response>editProfilePic(@RequestParam String token,@RequestParam("file")MultipartFile file) throws IOException{
		return new ResponseEntity<Response>(serviceImpl.editPic(token, file),HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
