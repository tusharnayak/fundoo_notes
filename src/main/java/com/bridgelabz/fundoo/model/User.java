package com.bridgelabz.fundoo.model;

import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;
@Document("userlogin")
//@Data
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
	
	
	public String getForgetPassword() {
		return forgetPassword;
	}
	public void setForgetPassword(String forgetPassword) {
		this.forgetPassword = forgetPassword;
	}
	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
