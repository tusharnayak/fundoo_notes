package com.bridgelabz.fundoo.label.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.fundoo.label.dto.LabelDto;
import com.bridgelabz.fundoo.label.services.LabelServiceImpl;
import com.bridgelabz.fundoo.response.Response;

@RestController
@RequestMapping("/api")
public class labelController {

	@Autowired
	private LabelServiceImpl labelserviceimpl;

	@PostMapping("/createLabel")
	public ResponseEntity<Response> createLabel(@RequestBody LabelDto labeldto, @RequestParam String token) {
		return new ResponseEntity<Response>(labelserviceimpl.createLabel(labeldto, token), HttpStatus.OK);
	}

	@PostMapping("/deleteLabel")
	public ResponseEntity<Response> deleteLabel(@RequestBody @RequestParam String token, @RequestParam String labelid) {
		return new ResponseEntity<Response>(labelserviceimpl.deleteLabel(token, labelid), HttpStatus.OK);
	}

	@PostMapping("/updateLabel")
	public ResponseEntity<Response> updateLabel(@RequestBody LabelDto labeldto, @RequestParam String token,@RequestParam String labelid) {
		return new ResponseEntity<Response>(labelserviceimpl.updateLabel(labeldto, token, labelid), HttpStatus.OK);
	}
	
	@PostMapping("/labelNoteAdd")
	public ResponseEntity<Response>labelNoteAdd(@RequestBody @RequestParam String noteid,@RequestParam String labelid,@RequestParam String token){
		return new ResponseEntity<Response>(labelserviceimpl.labelNoteAdd(noteid, labelid, token),HttpStatus.OK);
	}

}
