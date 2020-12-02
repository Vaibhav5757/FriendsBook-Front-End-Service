package com.friendsbook.frontend.user.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.friendsbook.frontend.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{
	
	User findByEmail(String email);
}
