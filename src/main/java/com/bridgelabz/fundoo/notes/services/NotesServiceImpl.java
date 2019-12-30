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
	private NoteRepository noterepository;
	@Autowired
	private UserRepository userrepository;
	@Autowired
	private Jwt jwt;

	@Autowired
	private Environment environment;

	/**
	 * @purpose: to create a note. we should pass dto and token
	 * @return: returning the status which has taken from the environmental file
	 */
	@Override
	public Response createNote(NoteDto notedto, String token) {
		String email = jwt.getUserToken(token);
		User user = userrepository.findByemail(email);
		if (user != null) {
			ModelMapper mapper = new ModelMapper();
			Note note = mapper.map(notedto, Note.class);
			note.setTitle(notedto.getTitle());
			note.setDescription(notedto.getDescription());
			LocalDateTime now = LocalDateTime.now();
			note.setTime(now);
			noterepository.save(note);
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
			Note noteId = noterepository.findById(id).get();
			noterepository.delete(noteId);
			return new Response(200, environment.getProperty("NOTE_DELETED"), HttpStatus.OK);
		}
		return new Response(400, environment.getProperty("NOTE_DELETED_FAILED"), HttpStatus.BAD_REQUEST);
	}

	/**
	 * @purpose: to update previous notes.
	 * @return: returning the status which has taken from the environmental file
	 */
	@Override
	public Response updateNote(NoteDto notedto, String token, String id) {
		String email = jwt.getUserToken(token);
		User user = userrepository.findByemail(email);
		if (user != null) {
			System.out.println("emailId:::" + email);
			Note note = noterepository.findById(id).get();
			System.out.println("note data:::" + note);
			note.setTitle(notedto.getTitle());
			note.setDescription(notedto.getDescription());
			LocalDateTime update = LocalDateTime.now();
			note.setLastUpdated(update);
			noterepository.save(note);
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
		User user = userrepository.findByemail(email);
		if (user != null) {
			Note note = noterepository.findById(id).get();
			if (note.getId() != null) {
				note.setPin(!note.isPin());
				noterepository.save(note);
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
		User user = userrepository.findByemail(email);
		if (user != null) {
			Note note = noterepository.findById(id).get();
			if (note.getId() != null) {
				note.setArchive(!note.isArchive());
				noterepository.save(note);
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
		User user = userrepository.findByemail(email);
		if (user != null) {
			Note note = noterepository.findById(id).get();
			if (note.getId() != null) {
				note.setTrash(!note.isTrash());
				noterepository.save(note);
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
		User user = userrepository.findByemail(email);
		if (user != null) {
			Note note = noterepository.findById(id).get();
			note.getCollabEmailId().add(collabdto);
			noterepository.save(note);
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
		return noterepository.findAll().stream().sorted((u1, u2) -> u1.getTitle().compareToIgnoreCase(u2.getTitle()))
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
		return noterepository.findAll().stream().sorted((u1, u2) -> u1.getTime().compareTo(u2.getTime())).parallel()
				.collect(Collectors.toList());
	}

	/**
	 * @purpose: getting the time and date in descending order
	 * @return: returning what ever it got from the noterepository ,first of all
	 *          filter it, then sort it by descending date order using parallel
	 *          sorting features
	 */
	@Override
	public List<?> descendingSortByDate() {
		return noterepository.findAll().stream().sorted((u1, u2) -> u2.getTime().compareTo(u1.getTime())).parallel()
				.collect(Collectors.toList());

	}

	@Override
	public Response addReminder(String noteId, String token, int month, int year, int date, int hour, int minute,
			int second) {
		String email = jwt.getUserToken(token);
		User user = userrepository.findByemail(email);
		if (user != null) {
			Note note = noterepository.findAllById(noteId);
			ZoneId indiaZoneId = ZoneId.of("Asia/Kolkata");
			LocalDateTime ldt = LocalDateTime.now(indiaZoneId);
			System.out.println("date::::" + ldt);
			LocalDateTime specificDateTime = ldt.withMonth(month).withYear(year).withDayOfMonth(date).withHour(hour)
					.withMinute(minute).withSecond(second);
			note.setAddReminder(specificDateTime);
			noterepository.save(note);
			return new Response(200, environment.getProperty("REMINDER_ADDED"), HttpStatus.OK);
		}
		return new Response(400, environment.getProperty("REMINDER_REMOVED"), HttpStatus.BAD_REQUEST);
	}

	@Override
	public Response deleteReminder(String noteId) {
		Note note = noterepository.findById(noteId).get();
		if (note.getAddReminder() != null) {
			note.setAddReminder(null);
			return new Response(200, environment.getProperty("REMINDER_REMOVED"), HttpStatus.OK);
		}
		return new Response(400, environment.getProperty("REMINDER_NOT_AVAILABLE"), HttpStatus.BAD_REQUEST);

	}

}
