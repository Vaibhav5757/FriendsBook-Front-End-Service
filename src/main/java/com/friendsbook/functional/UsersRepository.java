package com.friendsbook.functional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.friendsbook.frontend.model.User;

@Repository
public interface UsersRepository extends CrudRepository<User, Integer>{

	User findByEmail(String email);
}
