package com.friendsbook.repository;

import org.springframework.data.repository.CrudRepository;

import com.friendsbook.frontend.model.User;

public interface UsersRepository extends CrudRepository<User, Integer>{

	User findByEmail(String email);
}
