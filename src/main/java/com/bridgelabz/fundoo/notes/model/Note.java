package com.bridgelabz.fundoo.notes.model;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Document
@Data
public class Note {
	@Id
	private String id;
	@NotBlank
	private String description;
	@NotBlank
	private String title;
	private LocalDateTime time;
	private LocalDateTime lastUpdated;
	@Pattern(regexp = "[a-zA-Z0-9][a-zA-Z0-9-.]*@[a-zA-Z]+([.][a-zA-z]*)*")
	private String email;
	private String collaboratorEmailid;
	private boolean pin;
	private boolean archive;
	private boolean trash;

	@JsonIgnore
	@DBRef(lazy = true)
	private List<Label> labellist = new ArrayList<>();
}
