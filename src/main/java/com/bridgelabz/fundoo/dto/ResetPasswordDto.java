package com.bridgelabz.fundoo.dto;

import javax.validation.constraints.NotBlank;

public class ResetPasswordDto {
	@NotBlank
	private String newPassword;
	@NotBlank
	private String confirmPassword;

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
