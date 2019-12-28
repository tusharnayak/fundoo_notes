package com.bridgelabz.fundoo.model;

import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
@Document("userlogin")
@Data
public class User {
	
	
	@Id
	private String id;
	
	private String firstName;
	private String lastName;
	private String userName;
	private String email;
	private String password;
	private String confirmPassword;
	private boolean isValid;
	private String forgetPassword;
	private String profilePic;
}
