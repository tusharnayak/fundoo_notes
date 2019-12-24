package com.bridgelabz.fundoo.notes.services;

import java.time.LocalDateTime;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.notes.dto.CollaboratorDto;
import com.bridgelabz.fundoo.notes.dto.NoteDto;
import com.bridgelabz.fundoo.notes.model.Note;
import com.bridgelabz.fundoo.notes.repository.NoteRepository;
import com.bridgelabz.fundoo.repository.UserRepository;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.utility.Jwt;

@Service
public class NotesServiceImpl implements NotesService {

	@Autowired
	private NoteRepository noterepository;
	@Autowired
	private UserRepository userrepository;
	@Autowired
	private Jwt jwt;

	@Override
	public Response createNote(NoteDto notedto, String token) {
		String email = jwt.getUserToken(token);
		// User user = userRepository.findByemail(token);
		if (email != null) {
			ModelMapper mapper = new ModelMapper();
			Note note = mapper.map(notedto, Note.class);
			if (notedto.getEmail().equals(email)) {
				note.setTitle(notedto.getTitle());
				note.setDescription(notedto.getDescription());
				LocalDateTime now = LocalDateTime.now();
				note.setTime(now);
				noterepository.save(note);
				return new Response(200, "note created", true);
			}
			return new Response(400, "invalid mail id", false);
		}
		return new Response(400, "invalid credential", false);
	}

	@Override
	public Response deleteNote(String token, String id) {
		String email = jwt.getUserToken(token);
		if (email != null) {
			List<Note> listnote = noterepository.findAll();
			Note note = listnote.stream().filter(i -> i.getId().equals(id)).findAny().orElse(null);
			System.out.println("note::" + note);
			noterepository.delete(note);
			return new Response(200, "note deleted", true);
		}
		return new Response(400, "invalid note id", false);
	}

	@Override
	public Response updateNote(NoteDto notedto, String token, String id) {
		String email = jwt.getUserToken(token);
		User user = userrepository.findByemail(email);
		System.out.println("emailId:::" + email);
		if (user != null) {
			if (email.equals(notedto.getEmail())) {
				List<Note> listnote = noterepository.findAll();
				Note note = listnote.stream().filter(i -> i.getId().equals(id)).findAny().orElse(null);
				System.out.println("note data:::" + note);
				note.setTitle(notedto.getTitle());
				note.setDescription(notedto.getDescription());
				noterepository.save(note);
				return new Response(200, "note updated", true);
			}
			return new Response(400, "invalid mail id", false);
		}
		return new Response(400, "invalid credential", false);
	}

	@Override
	public Response pin(String token, String id) {
		String email = jwt.getUserToken(token);
		User user = userrepository.findByemail(email);
		if (user != null) {
			List<Note> listnote = noterepository.findAll();
			Note note = listnote.stream().filter(i -> i.getId().equals(id)).findAny().orElse(null);
			if (note.getId() != null) {
				note.setPin(!note.isPin());
				noterepository.save(note);
				return new Response(200, "pinUnpin updated", true);
			}
			return new Response(400, "check your id", false);
		}
		return new Response(400, "emailId incorrect", false);
	}

	@Override
	public Response archive(String token, String id) {
		String email = jwt.getUserToken(token);
		User user = userrepository.findByemail(email);
		if (user != null) {
			List<Note> listnote = noterepository.findAll();
			Note note = listnote.stream().filter(i -> i.getId().equals(id)).findAny().orElse(null);
			if (note.getId() != null) {
				note.setArchive(!note.isArchive());
				noterepository.save(note);
				return new Response(200, "archieveUnArchieve updated", true);
			}
			return new Response(400, "check your id", false);
		}
		return new Response(400, "emailId incorrect", false);

	}

	@Override
	public Response trash(String token, String id) {
		String email = jwt.getUserToken(token);
		User user = userrepository.findByemail(email);
		if (user != null) {
			List<Note> listnote = noterepository.findAll();
			Note note = listnote.stream().filter(i -> i.getId().equals(id)).findAny().orElse(null);
			if (note.getId() != null) {
				note.setTrash(!note.isTrash());
				noterepository.save(note);
				return new Response(200, "trashUnthrashed updated", true);
			}
			return new Response(400, "check your id", false);
		}
		return new Response(400, "emailId incorrect", false);

	}


	@Override
	public Response collaborator(CollaboratorDto collabdto, String noteid, String token) {
		String email = jwt.getUserToken(token);
		User user = userrepository.findByemail(email);
		if (user != null) {
			List<Note> notelist = noterepository.findAll();
			Note note = notelist.stream().filter(i -> i.getId().equals(noteid)).findAny().orElse(null);
			note.setCollaboratorEmailid(collabdto.getCollaboratorEmailid());
			noterepository.save(note);
			return new Response(200, "collaborator added", true);
		}
		return new Response(400, "invalid email id", false);
	}
	
	
	

}
