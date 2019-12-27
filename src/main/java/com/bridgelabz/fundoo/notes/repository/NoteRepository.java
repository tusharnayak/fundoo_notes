package com.bridgelabz.fundoo.notes.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.bridgelabz.fundoo.notes.model.Note;

@Repository
public interface NoteRepository extends MongoRepository<Note, String>{
	
	public Note findByemail(String email);	
	public Note findAllById(String id);
	//Object findByIdAndEmailId(String id,String email);
	
	
	
}
