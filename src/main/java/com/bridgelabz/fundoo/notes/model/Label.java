package com.bridgelabz.fundoo.notes.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import com.bridgelabz.fundoo.notes.model.Note;

import lombok.Data;
@Data
public class Label {
	@Id
	private String labelId;
	
	private String email;
	private String lableTitle;
	private LocalDateTime localdatetime;
	private LocalDateTime lastUpdateDate;

	@DBRef
	List<Note>listnote=new ArrayList<>();

}
