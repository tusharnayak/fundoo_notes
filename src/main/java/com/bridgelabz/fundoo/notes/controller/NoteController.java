package com.bridgelabz.fundoo.notes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.fundoo.notes.dto.CollaboratorDto;
import com.bridgelabz.fundoo.notes.dto.NoteDto;
import com.bridgelabz.fundoo.notes.model.Note;
import com.bridgelabz.fundoo.notes.services.NotesService;
import com.bridgelabz.fundoo.response.Response;

@RestController
@RequestMapping("/note")
public class NoteController {

	@Autowired
	private NotesService noteserviceimpl;

	/**
	 * @param notedto
	 * @param token
	 * @return: the value of notedto and token
	 */
	@PostMapping("/createnote")
	public ResponseEntity<Response> createNote(@RequestBody NoteDto notedto, @RequestParam String token) {
		return new ResponseEntity<Response>(noteserviceimpl.createNote(notedto, token), HttpStatus.OK);
	}

	/**
	 * @param token
	 * @param id
	 * @return: return the response entity of token and id
	 */
	@PostMapping("/deletenote")
	public ResponseEntity<Response> deleteNote(@RequestBody @RequestParam String token, @RequestParam String id) {
		return new ResponseEntity<Response>(noteserviceimpl.deleteNote(token, id), HttpStatus.OK);
	}

	/**
	 * @param notedto
	 * @param token
	 * @param id
	 * @return: response entity of update notes
	 */
	@PostMapping("/updatenote")
	public ResponseEntity<Response> updateNote(@RequestBody NoteDto notedto, @RequestParam String token, @RequestParam String id) {
		return new ResponseEntity<Response>(noteserviceimpl.updateNote(notedto, token, id), HttpStatus.OK);
	}

	/**
	 * @param token
	 * @param id
	 * @return: response entity of pin and unpin
	 */
	@PostMapping("/pin and unpin")
	public ResponseEntity<Response> pin(@RequestBody @RequestParam String token, @RequestParam String id) {
		return new ResponseEntity<Response>(noteserviceimpl.pin(token, id), HttpStatus.OK);

	}

	/**
	 * @param token
	 * @param id
	 * @return: response entity of archive and restore
	 */
	@PostMapping("/archieve-unArchieve")
	public ResponseEntity<Response> archieve(@RequestBody @RequestParam String token, @RequestParam String id) {
		return new ResponseEntity<Response>(noteserviceimpl.archive(token, id), HttpStatus.OK);
	}

	/**
	 * @param token
	 * @param id
	 * @return: response entity of trash and untrash
	 */
	@PostMapping("/trash-untrash")
	public ResponseEntity<Response> trash(@RequestBody @RequestParam String token, @RequestParam String id) {
		return new ResponseEntity<Response>(noteserviceimpl.trash(token, id), HttpStatus.OK);
	}

	/**
	 * @param collabdto
	 * @param noteid
	 * @param token
	 * @return: response entity of collaborator
	 */
	@PostMapping("/addcollaborator")
	public ResponseEntity<Response> collaborator(@RequestBody CollaboratorDto collabdto, @RequestParam String noteid,
			@RequestParam String token) {
		return new ResponseEntity<Response>(noteserviceimpl.collaborator(collabdto, noteid, token), HttpStatus.OK);
	}

	/**@purpose: to sort the note by its title
	 * @return: its return list of note according to title
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/sortByTitle")
	public List<Note> sortByTitle() {
		return (List<Note>) noteserviceimpl.sortByName();
	}

	/**@purpose: to sort the note by its date and time in ascending order
	 * @return: its returning the ascending sorting order date and time
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/ascending-datetimeSort")
	public List<Note> sortByAscending() {
		return (List<Note>) noteserviceimpl.ascendingSortByDate();
	}

	/**@purpose: to sort the note by its date and time in descending order
	 * @return: its returning the sorting order date and time
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/descending-dateTimeSort")
	public List<Note> sortByDescending() {
		return (List<Note>) noteserviceimpl.descendingSortByDate();
	}

}
