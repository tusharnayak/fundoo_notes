package com.bridgelabz.fundoo.dto;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;


@Data


public class LoginDto {
	@NotNull
	@Pattern(regexp="[a-zA-Z0-9][a-zA-Z0-9-.]*@[a-zA-Z]+([.][a-zA-z]*)*")
	
	private String email;
	@NotNull
	private String password;
	
}
