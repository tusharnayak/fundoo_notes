package com.bridgelabz.fundoo.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;
@Data
public class ForgetPasswordDto {
	@NotBlank
	@Pattern(regexp="[a-zA-Z0-9][a-zA-Z0-9-.]*@[a-zA-Z]+([.][a-zA-z]*)*")

	private String email;



}
