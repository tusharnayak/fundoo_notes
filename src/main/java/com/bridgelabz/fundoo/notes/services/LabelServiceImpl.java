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
	private LabelRepository labelrepository;
	@Autowired
	private UserRepository userrepository;
	@Autowired
	private NoteRepository noterepository;
	@Autowired
	private Jwt jwt;
	@Autowired
	private Environment environment;

	@Override
	public Response createLabel(LabelDto labeldto, String token) {
		String email = jwt.getUserToken(token);
		User user = userrepository.findByemail(email);
		if (user != null) {
			ModelMapper mapper = new ModelMapper();
			Label label = mapper.map(labeldto, Label.class);
			label.setLableTitle(labeldto.getLableTitle());
			LocalDateTime datetime = LocalDateTime.now();
			label.setLocaldatetime(datetime);
			labelrepository.save(label);
			return new Response(200,environment.getProperty("LABEL_CREATED") , HttpStatus.OK);
		}
		return new Response(400, environment.getProperty("INVALID_MAIL_ID"), HttpStatus.BAD_REQUEST);
	}

	@Override
	public Response deleteLabel(String token, String labelid) {
		String email = jwt.getUserToken(token);
		User user = userrepository.findByemail(email);
		if (user != null) {
			Label label = labelrepository.findById(labelid).get();
			labelrepository.delete(label);
			return new Response(200,environment.getProperty("LABEL_DELETED") , HttpStatus.BAD_REQUEST);
		}
		return new Response(400, environment.getProperty("INVALID_MAIL_ID"),HttpStatus.BAD_REQUEST);
	}

	@Override
	public Response updateLabel(LabelDto labeldto, String token, String labelid) {
		String email = jwt.getUserToken(token);
		User user = userrepository.findByemail(email);
		if (user != null) {
			Label label = labelrepository.findById(labelid).get();
			label.setLableTitle(labeldto.getLableTitle());
			LocalDateTime localdatetime = LocalDateTime.now();
			label.setLocaldatetime(localdatetime);
			labelrepository.save(label);
			return new Response(200,environment.getProperty("LABEL_UPDATED") ,HttpStatus.OK);
		}
		return new Response(400, environment.getProperty("INVALID_MAIL_ID"),HttpStatus.BAD_REQUEST);
	}

	@Override
	public Response labelNoteAdd(String noteid, String labelid, String token) {
		String email = jwt.getUserToken(token);
		User user = userrepository.findByemail(email);
		if (user != null) {
			Label label = labelrepository.findById(labelid).get();
			Note note = noterepository.findById(noteid).get();
			note.getLabellist().add(label);
			label.getListnote().add(note);
			labelrepository.save(label);
			noterepository.save(note);

			return new Response(200, environment.getProperty("NOTE_AND_LABEL_ADDED"), HttpStatus.OK);
		}
		return new Response(400, environment.getProperty("INVALID_MAIL_ID"),HttpStatus.BAD_REQUEST);
	}
}
