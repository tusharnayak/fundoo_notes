package com.bridgelabz.fundoo.exception.custom;

public class NoteNotFoundException extends RuntimeException{
	

	private static final long serialVersionUID = 1L;

	public NoteNotFoundException (String message) {

		super(message);

	}
}
