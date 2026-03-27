package com.bodegasfrancisco.authservice.repository;

import com.bodegasfrancisco.authservice.model.User;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    @NullMarked
    List<User> findAllByStatus(User.Status status);

    @NullMarked
    Optional<User> findByIdAndStatus(String id, User.Status status);

    @Query("{ 'email': ?0, 'status': 'ACTIVE' }")
    Optional<User> findByEmail(String email);

    @Query("{ 'userId': ?0, 'status': 'ACTIVE' }")
    Optional<User> findByUserId(String userId);
}
