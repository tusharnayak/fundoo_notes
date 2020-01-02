package com.bridgelabz.fundoo.notes.dto;




import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


import lombok.Data;

@Data
public class CollaboratorDto {
	@Pattern(regexp="[a-zA-Z0-9][a-zA-Z0-9-.]*@[a-zA-Z]+([.][a-zA-z]*)*")
	@NotBlank
	private String collaboratorEmailid;
	//private List<Note>collaboratorEmailid;
	//private List<Note>collaboratorMail;
}