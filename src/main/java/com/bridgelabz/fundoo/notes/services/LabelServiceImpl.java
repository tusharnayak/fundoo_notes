package com.bridgelabz.fundoo.notes.services;

import java.time.LocalDateTime;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.notes.dto.LabelDto;
import com.bridgelabz.fundoo.notes.model.Label;
import com.bridgelabz.fundoo.notes.model.Note;
import com.bridgelabz.fundoo.notes.repository.LabelRepository;
import com.bridgelabz.fundoo.notes.repository.NoteRepository;
import com.bridgelabz.fundoo.repository.UserRepository;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.utility.Jwt;

@Service
@PropertySource("classpath:message.properties")
public class LabelServiceImpl implements LabelService {
	@Autowired
	private LabelRepository labelRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private NoteRepository noteRepository;
	@Autowired
	private Jwt jwt;
	@Autowired
	private Environment environment;

	/**
	 * @purpose: creating the label
	 * @return: it returns the response that label is created or not
	 */
	@Override
	public Response createLabel(LabelDto labeldto, String token) {
		String email = jwt.getUserToken(token);
		User user = userRepository.findByemail(email);
		if (user == null) {
			return new Response(400, environment.getProperty("INVALID_MAIL_ID"), HttpStatus.BAD_REQUEST);
		} else {
			ModelMapper mapper = new ModelMapper();
			Label label = mapper.map(labeldto, Label.class);
			label.setLableTitle(labeldto.getLableTitle());
			LocalDateTime datetime = LocalDateTime.now();
			label.setLocaldatetime(datetime);
			labelRepository.save(label);
			return new Response(200, environment.getProperty("LABEL_CREATED"), HttpStatus.OK);
		}
	}

	/**
	 * @purpose: to delete the label that user created
	 * @return: return the response of label deleted or not
	 *
	 */
	@Override
	public Response deleteLabel(String token, String labelid) {
		String email = jwt.getUserToken(token);
		User user = userRepository.findByemail(email);
		if (user == null) {
			return new Response(400, environment.getProperty("INVALID_MAIL_ID"), HttpStatus.BAD_REQUEST);
		} else {
			Label label = labelRepository.findById(labelid).get();
			labelRepository.delete(label);
			return new Response(200, environment.getProperty("LABEL_DELETED"), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * @purpose: to update the label title
	 * @return: returns the label is update or not
	 *
	 */
	@Override
	public Response updateLabel(LabelDto labeldto, String token, String labelid) {
		String email = jwt.getUserToken(token);
		User user = userRepository.findByemail(email);
		if (user == null) {
			return new Response(400, environment.getProperty("INVALID_MAIL_ID"), HttpStatus.BAD_REQUEST);
		} else {
			Label label = labelRepository.findById(labelid).get();
			label.setLableTitle(labeldto.getLableTitle());
			LocalDateTime lastUpdated = LocalDateTime.now();
			label.setLastUpdateDate(lastUpdated);
			labelRepository.save(label);
			return new Response(200, environment.getProperty("LABEL_UPDATED"), HttpStatus.OK);
		}
	}

	/**
	 * @purpose: add the label list in note and note list in label
	 * @return: property that the label and note list are added or not
	 */
	@Override
	public Response labelNoteAdd(String noteid, String labelid, String token) {
		String email = jwt.getUserToken(token);
		User user = userRepository.findByemail(email);
		if (user == null) {
			return new Response(400, environment.getProperty("INVALID_MAIL_ID"), HttpStatus.BAD_REQUEST);
		} else {
			Label label = labelRepository.findById(labelid).get();
			Note note = noteRepository.findById(noteid).get();
			note.getLabellist().add(label);
			label.getListnote().add(note);
			labelRepository.save(label);
			noteRepository.save(note);
			return new Response(200, environment.getProperty("NOTE_AND_LABEL_ADDED"), HttpStatus.OK);
		}
	}
}
