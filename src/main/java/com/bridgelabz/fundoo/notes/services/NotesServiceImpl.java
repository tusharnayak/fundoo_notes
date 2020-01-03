package com.bridgelabz.fundoo.notes.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
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
@PropertySource("classpath:message.properties")
public class NotesServiceImpl implements NotesService {
	@Autowired
	private NoteRepository noteRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private Jwt jwt;

	@Autowired
	private Environment environment;

	/**
	 * @purpose: to create a note. we should pass dto and token
	 * @return: returning the status which has taken from the environmental file
	 */
	@Override
	public Response createNote(NoteDto noteDto, String token) {
		String email = jwt.getUserToken(token);
		User user = userRepository.findByemail(email);
		if (user != null) {
			ModelMapper mapper = new ModelMapper();
			Note note = mapper.map(noteDto, Note.class);
			note.setTitle(noteDto.getTitle());
			note.setDescription(noteDto.getDescription());
			LocalDateTime now = LocalDateTime.now();
			note.setTime(now);
			noteRepository.save(note);
			return new Response(200, environment.getProperty("NOTE_CREATION"), HttpStatus.OK);

		}
		return new Response(400, environment.getProperty("INVALID_CREDENTIAL"), HttpStatus.BAD_REQUEST);
	}

	/**
	 * @purpose: to delete the note.
	 * @return: returning the status which has taken from the environmental file
	 *
	 */
	@Override
	public Response deleteNote(String token, String id) {
		String email = jwt.getUserToken(token);
		if (email != null) {
			Note noteId = noteRepository.findById(id).get();
			noteRepository.delete(noteId);
			return new Response(200, environment.getProperty("NOTE_DELETED"), HttpStatus.OK);
		}
		return new Response(400, environment.getProperty("NOTE_DELETED_FAILED"), HttpStatus.BAD_REQUEST);
	}

	/**
	 * @purpose: to update previous notes.
	 * @return: returning the status which has taken from the environmental file
	 */
	@Override
	public Response updateNote(NoteDto noteDto, String token, String id) {
		String email = jwt.getUserToken(token);
		User user = userRepository.findByemail(email);
		if (user != null) {
			System.out.println("emailId:::" + email);
			Note note = noteRepository.findById(id).get();
			System.out.println("note data:::" + note);
			note.setTitle(noteDto.getTitle());
			note.setDescription(noteDto.getDescription());
			LocalDateTime update = LocalDateTime.now();
			note.setLastUpdated(update);
			noteRepository.save(note);
			return new Response(200, environment.getProperty("NOTE_UPDATED"), HttpStatus.OK);
		}
		return new Response(400, environment.getProperty("INVALID_CREDENTIAL"), HttpStatus.BAD_REQUEST);
	}

	/**
	 * @purpose: to pin notes and unpin notes.
	 * @return: returning the status which has taken from the environmental file
	 */
	@Override
	public Response pin(String token, String id) {
		String email = jwt.getUserToken(token);
		User user = userRepository.findByemail(email);
		if (user != null) {
			Note note = noteRepository.findById(id).get();
			if (note.getId() != null) {
				note.setPin(!note.isPin());
				noteRepository.save(note);
				return new Response(200, environment.getProperty("VALUE"), note.isPin());
			}
			return new Response(400, environment.getProperty("INVALID_CREDENTIAL"), HttpStatus.BAD_REQUEST);
		}
		return new Response(400, environment.getProperty("INVALID_CREDENTIAL"), HttpStatus.BAD_REQUEST);
	}

	/**
	 * @purpose: to archive notes and unarchive notes.
	 * @return: returning the status which has taken from the environmental file
	 */
	@Override
	public Response archive(String token, String id) {
		String email = jwt.getUserToken(token);
		User user = userRepository.findByemail(email);
		if (user != null) {
			Note note = noteRepository.findById(id).get();
			if (note.getId() != null) {
				note.setArchive(!note.isArchive());
				noteRepository.save(note);
				return new Response(200, environment.getProperty("VALUE"), note.isArchive());
			}
			return new Response(400, environment.getProperty("INVALID_CREDENTIAL"), HttpStatus.BAD_REQUEST);
		}
		return new Response(400, environment.getProperty("INVALID_CREDENTIAL"), HttpStatus.BAD_REQUEST);

	}

	/**
	 * @purpose: to trash or restore data.so in one click you can trash and another
	 *           for restore data.
	 * @return: returning the status which has taken from the environmental file
	 */
	@Override
	public Response trash(String token, String id) {
		String email = jwt.getUserToken(token);
		User user = userRepository.findByemail(email);
		if (user != null) {
			Note note = noteRepository.findById(id).get();
			if (note.getId() != null) {
				note.setTrash(!note.isTrash());
				noteRepository.save(note);
				return new Response(200, environment.getProperty("VALUE"), note.isTrash());
			}
			return new Response(400, environment.getProperty("INVALID_CREDENTIAL"), HttpStatus.BAD_REQUEST);
		}
		return new Response(400, environment.getProperty("INVALID_CREDENTIAL"), HttpStatus.BAD_REQUEST);

	}

	/**
	 * @purpose: add any collaborator(mail-id) who can see your note data
	 * @return: returning the status which has taken from the environmental file
	 */
	@Override
	public Response collaborator(CollaboratorDto collabdto, String id, String token) {
		String email = jwt.getUserToken(token);
		User user = userRepository.findByemail(email);
		if (user != null) {
			Note note = noteRepository.findById(id).get();
			note.getCollabEmailId().add(collabdto);
			noteRepository.save(note);
			return new Response(200, environment.getProperty("COLLABORATOR"), HttpStatus.OK);
		}
		return new Response(400, environment.getProperty("INVALID_CREDENTIAL"), HttpStatus.BAD_REQUEST);
	}

	/**
	 * @purpose: sort all the name that we have given
	 * @return: returning what ever it got from the noterepository ,first of all
	 *          filter it, then sort it by title name order using parallel sorting
	 *          features
	 */
	@Override
	public List<?> sortByName() {
		return noteRepository.findAll().stream().sorted((u1, u2) -> u1.getTitle().compareToIgnoreCase(u2.getTitle()))
				.parallel().collect(Collectors.toList());
	}

	/**
	 * @purpose: to sort the time and date in ascending order
	 * @return: returning what ever it got from the noterepository ,first of all
	 *          filter it, then sort it by ascending date order using parallel
	 *          sorting features
	 */
	@Override
	public List<?> ascendingSortByDate() {
		return noteRepository.findAll().stream().sorted((u1, u2) -> u1.getTime().compareTo(u2.getTime())).parallel()
				.collect(Collectors.toList());
	}

	/**
	 * @purpose: getting the time and date in descending order
	 * @return: returning what ever it got from the note repository ,first of all
	 *          filter it, then sort it by descending date order using parallel
	 *          sorting features
	 */
	@Override
	public List<?> descendingSortByDate() {
		return noteRepository.findAll().stream().sorted((u1, u2) -> u2.getTime().compareTo(u1.getTime())).parallel()
				.collect(Collectors.toList());

	}

	/**
	 * @purpose: to add the reminder according to the user requirement
	 * @return: returning the reminder is added or not
	 */
	@Override
	public Response addReminder(String noteId, String token, int month, int year, int date, int hour, int minute,
			int second) {
		String email = jwt.getUserToken(token);
		User user = userRepository.findByemail(email);
		if (user != null) {
			Note note = noteRepository.findAllById(noteId);
			ZoneId indiaZoneId = ZoneId.of("Asia/Kolkata");
			LocalDateTime ldt = LocalDateTime.now(indiaZoneId);
			LocalDateTime specificDateTime = ldt.withMonth(month).withYear(year).withDayOfMonth(date).withHour(hour)
					.withMinute(minute).withSecond(second);
			note.setAddReminder(specificDateTime);
			noteRepository.save(note);
			return new Response(200, environment.getProperty("REMINDER_ADDED"), HttpStatus.OK);
		}
		return new Response(400, environment.getProperty("REMINDER_REMOVED"), HttpStatus.BAD_REQUEST);
	}

	/**
	 * @purpose: to delete the reminder
	 * @return: return the confirmation of reminder is deleted or not
	 */
	@Override
	public Response deleteReminder(String noteId) {
		Note note = noteRepository.findById(noteId).get();
		if (note.getAddReminder() == null) {
			return new Response(400, environment.getProperty("REMINDER_NOT_AVAILABLE"), HttpStatus.BAD_REQUEST);

		} else {
			note.setAddReminder(null);
			noteRepository.save(note);
			return new Response(200, environment.getProperty("REMINDER_REMOVED"), HttpStatus.OK);
		}
}

	/**
	 * @purpose: to edit the reminder according to the user
	 * @return: returning the confirmation that the reminder is edited or not
	 */
	@Override
	public Response editReminder(String noteId, String token, int month, int year, int date, int hour, int minute,
			int second) {
		Note note = noteRepository.findById(noteId).get();
		if (note.getAddReminder() == null) {
			return new Response(400, environment.getProperty("MESSAGE"), HttpStatus.BAD_REQUEST);
		} else {
			note.setAddReminder(null);
			LocalDateTime ldt = LocalDateTime.now();
			LocalDateTime editedReminderDateTime = ldt.withMonth(month).withYear(year).withDayOfMonth(date)
					.withHour(hour).withMinute(minute).withSecond(second);
			note.setAddReminder(editedReminderDateTime);
			noteRepository.save(note);
		}
		return new Response(200, environment.getProperty("EDIT_REMINDER"), HttpStatus.OK);
	}
}
