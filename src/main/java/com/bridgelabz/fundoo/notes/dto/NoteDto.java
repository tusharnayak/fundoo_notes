package com.bridgelabz.fundoo.notes.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class NoteDto {
	@NotBlank
	private String description;
	@NotBlank
	private String title;
}
