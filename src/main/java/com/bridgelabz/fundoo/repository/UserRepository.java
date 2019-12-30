package com.bridgelabz.fundoo.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String>
{
	public User findByemail(String email);	
}
