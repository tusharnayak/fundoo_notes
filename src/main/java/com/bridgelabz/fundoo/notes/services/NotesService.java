package com.bridgelabz.fundoo.notes.services;


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

	public Response collaborator(CollaboratorDto collabdto,String noteid,String token);
	
}
