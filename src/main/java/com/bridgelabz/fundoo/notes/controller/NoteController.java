package com.bridgelabz.fundoo.notes.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.fundoo.notes.dto.CollaboratorDto;
import com.bridgelabz.fundoo.notes.dto.NoteDto;
import com.bridgelabz.fundoo.notes.services.NotesServiceImpl;
import com.bridgelabz.fundoo.response.Response;

@RestController
@RequestMapping("/api")
public class NoteController {

	@Autowired
	private NotesServiceImpl noteserviceimpl;

	@PostMapping("/createNote")
	public ResponseEntity<Response> createNote(@RequestBody NoteDto notedto, @RequestParam String token) {
		return new ResponseEntity<Response>(noteserviceimpl.createNote(notedto, token), HttpStatus.OK);
	}

	@PostMapping("/deleteNote")
	public ResponseEntity<Response> deleteNote(@RequestBody @RequestParam String token, @RequestParam String id) {
		return new ResponseEntity<Response>(noteserviceimpl.deleteNote(token, id), HttpStatus.OK);
	}

	@PostMapping("/updateNote")
	public ResponseEntity<Response> updateNote(@RequestBody NoteDto notedto, @RequestParam String token, String id) {
		return new ResponseEntity<Response>(noteserviceimpl.updateNote(notedto, token, id), HttpStatus.OK);
	}

	@PostMapping("/pinAndUnpin")
	public ResponseEntity<Response> pin(@RequestBody @RequestParam String token, @RequestParam String id) {
		return new ResponseEntity<Response>(noteserviceimpl.pin(token, id), HttpStatus.OK);

	}

	@PostMapping("/archieveUnArchieve")
	public ResponseEntity<Response> archieve(@RequestBody @RequestParam String token, @RequestParam String id) {
		return new ResponseEntity<Response>(noteserviceimpl.archive(token, id), HttpStatus.OK);
	}

	@PostMapping("/trashUntrash")
	public ResponseEntity<Response> trash(@RequestBody @RequestParam String token, @RequestParam String id) {
		return new ResponseEntity<Response>(noteserviceimpl.trash(token, id), HttpStatus.OK);
	}

	@PostMapping("/addCollaborator")
	public ResponseEntity<Response>collaborator(@RequestBody CollaboratorDto collabdto,@RequestParam String noteid,@RequestParam String token){
		return new ResponseEntity<Response>(noteserviceimpl.collaborator(collabdto, noteid, token),HttpStatus.OK);
	}
}



