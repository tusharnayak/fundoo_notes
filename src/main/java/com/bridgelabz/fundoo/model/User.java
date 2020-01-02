package com.bridgelabz.fundoo.model;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
@Document("userlogin")
@Data
public class User {
	
	
	@Id
	private String id;
	@NotBlank
	private String firstName;
	private String lastName;
	@NotBlank
	private String userName;
	@NotBlank
	private String email;
	@NotBlank
	private String password;
	@NotBlank
	private String confirmPassword;
	private boolean isValid;
	private String forgetPassword;
	private String profilePic;
}
