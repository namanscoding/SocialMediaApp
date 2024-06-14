package com.user.userservice.repository;

import com.user.userservice.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<Users,String> {

    Optional<Users> findByEmail(String email);
    Optional<Users>findByNameContainingIgnoreCase(String name);
}
