package com.bridgelabz.fundoo.notes.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.fundoo.notes.dto.LabelDto;
import com.bridgelabz.fundoo.notes.services.LabelService;
import com.bridgelabz.fundoo.response.Response;

@RestController
@RequestMapping("/label")
public class labelController {

	@Autowired
	private LabelService labelService;

	@PostMapping("/createlabel")
	public ResponseEntity<Response> createLabel(@Valid @RequestBody LabelDto labelDto, @RequestParam String token) {
		return new ResponseEntity<Response>(labelService.createLabel(labelDto, token), HttpStatus.OK);
	}

	@PostMapping("/deletelabel")
	public ResponseEntity<Response> deleteLabel(@RequestBody @RequestParam String token, @RequestParam String labelId) {
		return new ResponseEntity<Response>(labelService.deleteLabel(token, labelId), HttpStatus.OK);
	}

	@PostMapping("/updatelabel")
	public ResponseEntity<Response> updateLabel(@Valid @RequestBody LabelDto labelDto, @RequestParam String token,@RequestParam String labelId) {
		return new ResponseEntity<Response>(labelService.updateLabel(labelDto, token, labelId), HttpStatus.OK);
	}
	
	@PostMapping("/labelnotesadd")
	public ResponseEntity<Response>labelNoteAdd(@RequestBody @RequestParam String noteId,@RequestParam String labelid,@RequestParam String token){
		return new ResponseEntity<Response>(labelService.labelNoteAdd(noteId, labelid, token),HttpStatus.OK);
	}
}
