package com.bridgelabz.fundoo.notes.model;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import com.bridgelabz.fundoo.label.model.Label;
import com.bridgelabz.fundoo.notes.dto.CollaboratorDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Document
@Data
public class Note {
	@Id
	private String id;

	private String description;
	private String title;
	private LocalDateTime time;
	private String email;

	private String collaboratorEmailid;
	private List<CollaboratorDto> collaboratorlist = new ArrayList<>();
	private boolean pin;
	private boolean archive;
	private boolean trash;

	@JsonIgnore
	@DBRef(lazy = true)
	private List<Label> labellist = new ArrayList<>();
}
