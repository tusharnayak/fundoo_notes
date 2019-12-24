package com.bridgelabz.fundoo.notes.services;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
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

	/**
	 * @author : Tushar ranjan nayak
	 * @purpose: to create a note. we should pass dto and token
	 *
	 */
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

	/**
	 * @author : Tushar ranjan nayak
	 * @purpose:to delete the note.
	 *
	 */
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

	/**
	 * @author : Tushar ranjan nayak
	 * @purpose: to update previous notes.
	 */
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

	/**
	 * @author : Tushar ranjan nayak
	 * @purpose: to pin notes and unpin notes.
	 *
	 */
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

	/**
	 * @author : Tushar ranjan nayak
	 * @purpose: to archive notes and unarchive notes.
	 *
	 */
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

	/**
	 * @author : Tushar ranjan nayak
	 * @purpose: to trash or restore data.so in one click you can trash and another
	 *           for restore data.
	 */
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

	/**
	 * @author : Tushar ranjan nayak
	 * @purpose: add any collaborator(mail-id) who can see your note data
	 */
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

	/**
	 * @author : Tushar ranjan nayak
	 * @purpose: sort all the name that we have given
	 *
	 */
	@Override
	public List<?> sortByName() {

		return noterepository.findAll().stream().sorted(Comparator.comparing(Note::getTitle)).parallel()
				.collect(Collectors.toList());

	}

	/**
	 * @author : Tushar ranjan nayak
	 * @purpose: to sort the time and date in ascending order
	 * 
	 */
	@Override
	public List<?> ascendingSortByDate() {
//		return noterepository.findAll().stream().sorted(Comparator.comparing(Note::getTime)).parallel()
//				.collect(Collectors.toList());
		return noterepository.findAll().stream().sorted((u1, u2) -> u1.getTime().compareTo(u2.getTime())).parallel()
				.collect(Collectors.toList());
	}

	/**
	 * @purpose: getting the time and date in descending order
	 * @return: returned the value in descending sorted order
	 */
	@Override
	public List<?> descendingSortByDate() {
		return noterepository.findAll().stream().sorted((u1, u2) -> u2.getTime().compareTo(u1.getTime())).parallel()
				.collect(Collectors.toList());

	}

}
