package com.bridgelabz.fundoo.label.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.label.model.Label;

@Repository
public interface LabelRepository extends MongoRepository<Label, String>{
	public Label findByEmail(String email);

}
