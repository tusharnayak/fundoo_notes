package com.bridgelabz.fundoo.notes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.fundoo.notes.dto.CollaboratorDto;
import com.bridgelabz.fundoo.notes.dto.NoteDto;
import com.bridgelabz.fundoo.notes.model.Note;
import com.bridgelabz.fundoo.notes.services.NotesServiceImpl;
import com.bridgelabz.fundoo.response.Response;

@RestController
@RequestMapping("/api")
public class NoteController {

	@Autowired
	private NotesServiceImpl noteserviceimpl;

	/**
	 * @param notedto
	 * @param token
	 * @return: the value of notedto and token
	 */
	@PostMapping("/createNote")
	public ResponseEntity<Response> createNote(@RequestBody NoteDto notedto, @RequestParam String token) {
		return new ResponseEntity<Response>(noteserviceimpl.createNote(notedto, token), HttpStatus.OK);
	}

	/**
	 * @param token
	 * @param id
	 * @return: return the response entity of token and id
	 */
	@PostMapping("/deleteNote")
	public ResponseEntity<Response> deleteNote(@RequestBody @RequestParam String token, @RequestParam String id) {
		return new ResponseEntity<Response>(noteserviceimpl.deleteNote(token, id), HttpStatus.OK);
	}

	/**
	 * @param notedto
	 * @param token
	 * @param id
	 * @return: response entity of update notes
	 */
	@PostMapping("/updateNote")
	public ResponseEntity<Response> updateNote(@RequestBody NoteDto notedto, @RequestParam String token, String id) {
		return new ResponseEntity<Response>(noteserviceimpl.updateNote(notedto, token, id), HttpStatus.OK);
	}

	/**
	 * @param token
	 * @param id
	 * @return: response entity of pin and unpin
	 */
	@PostMapping("/pinAndUnpin")
	public ResponseEntity<Response> pin(@RequestBody @RequestParam String token, @RequestParam String id) {
		return new ResponseEntity<Response>(noteserviceimpl.pin(token, id), HttpStatus.OK);

	}

	/**
	 * @param token
	 * @param id
	 * @return: response entity of archive and restore
	 */
	@PostMapping("/archieveUnArchieve")
	public ResponseEntity<Response> archieve(@RequestBody @RequestParam String token, @RequestParam String id) {
		return new ResponseEntity<Response>(noteserviceimpl.archive(token, id), HttpStatus.OK);
	}

	/**
	 * @param token
	 * @param id
	 * @return: response entity of trash and untrash
	 */
	@PostMapping("/trashUntrash")
	public ResponseEntity<Response> trash(@RequestBody @RequestParam String token, @RequestParam String id) {
		return new ResponseEntity<Response>(noteserviceimpl.trash(token, id), HttpStatus.OK);
	}

	/**
	 * @param collabdto
	 * @param noteid
	 * @param token
	 * @return: response entity of collaborator
	 */
	@PostMapping("/addCollaborator")
	public ResponseEntity<Response> collaborator(@RequestBody CollaboratorDto collabdto, @RequestParam String noteid,
			@RequestParam String token) {
		return new ResponseEntity<Response>(noteserviceimpl.collaborator(collabdto, noteid, token), HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/sortByTitle")
	public List<Note> sortByTitle() {
		return (List<Note>) noteserviceimpl.sortByName();
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/ascendingSort")
	public List<Note> sortByAscending() {
		return (List<Note>) noteserviceimpl.ascendingSortByDate();
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/descendingSort")
	public List<Note> sortByDescending() {
		return (List<Note>) noteserviceimpl.descendingSortByDate();
	}

}
