package com.bridgelabz.fundoo.notes.services;

import java.util.List;

import com.bridgelabz.fundoo.notes.dto.CollaboratorDto;
import com.bridgelabz.fundoo.notes.dto.NoteDto;
import com.bridgelabz.fundoo.response.Response;

public interface NotesService {
	public Response createNote(NoteDto createdto, String token);

	public Response deleteNote(String token, String id);

	public Response updateNote(NoteDto notedto, String token, String id);

	public Response pin(String token, String id);

	public Response archive(String token, String id);

	public Response trash(String token, String id);

	public Response collaborator(CollaboratorDto collabdto, String noteid, String token);

	public Response addReminder(String noteId, String token, int month, int year, int date, int hour, int minute,
			int second);

	public Response deleteReminder(String noteId);

	public Response editReminder(String noteId, String token, int month, int year, int date, int hour, int minute,
			int second);

	public List<?> sortByName();

	public List<?> ascendingSortByDate();

	public List<?> descendingSortByDate();

}
