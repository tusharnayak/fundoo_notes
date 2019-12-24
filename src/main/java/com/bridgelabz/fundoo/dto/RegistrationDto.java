package com.bridgelabz.fundoo.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
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
