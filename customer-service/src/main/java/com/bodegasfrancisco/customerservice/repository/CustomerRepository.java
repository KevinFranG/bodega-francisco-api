package com.bodegasfrancisco.customerservice.repository;

import com.bodegasfrancisco.customerservice.model.Customer;
import org.bson.types.ObjectId;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends MongoRepository<Customer, ObjectId> {

    @NullMarked
    @Query("{ 'status': 'ACTIVE' }")
    List<Customer> findAll();

    @NullMarked
    @Query("{ '_id': ?0, 'status': 'ACTIVE' }")
    Optional<Customer> findById(ObjectId id);

    Optional<Customer> findByEmail(String email);
}
