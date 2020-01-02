package com.bridgelabz.fundoo.notes.dto;



import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LabelDto {
	@NotBlank
	private String lableTitle;
}