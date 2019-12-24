package com.bridgelabz.fundoo.dto;

import javax.validation.constraints.NotBlank;

import javax.validation.constraints.Pattern;

import lombok.Data;
@Data
public class RegistrationDto {
	@NotBlank
	private String firstName;
	private String lastName;
	@NotBlank
	private String userName;
	@NotBlank
	@Pattern(regexp="[a-zA-Z0-9][a-zA-Z0-9-.]*@[a-zA-Z]+([.][a-zA-z]*)*")
	private String email;
	@NotBlank
	private String password;
	@NotBlank
	private String confirmPassword;
	
	
	
}
