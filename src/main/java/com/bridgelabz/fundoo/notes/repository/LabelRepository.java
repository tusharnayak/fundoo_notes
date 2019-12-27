package com.bridgelabz.fundoo.notes.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.notes.model.Label;
@Repository
public interface LabelRepository extends MongoRepository<Label, String>{
	public Label findByEmail(String email);
}
