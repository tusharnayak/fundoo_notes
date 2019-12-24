package com.bridgelabz.fundoo.notes.dto;

import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class CollaboratorDto {
	@Pattern(regexp="[a-zA-Z0-9][a-zA-Z0-9-.]*@[a-zA-Z]+([.][a-zA-z]*)*")
	private String collaboratorEmailid;
}