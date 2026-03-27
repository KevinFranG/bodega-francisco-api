package com.bodegasfrancisco.authservice.service;

import com.bodegasfrancisco.authservice.model.Role;
import com.bodegasfrancisco.authservice.model.User;
import com.bodegasfrancisco.authservice.repository.UserRepository;
import com.bodegasfrancisco.data.CreateService;
import com.bodegasfrancisco.data.IndexService;
import com.bodegasfrancisco.exception.BadRequestException;
import com.bodegasfrancisco.exception.ErrorCodes;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@RequiredArgsConstructor

@Service
public class UserService implements
    IndexService<User, String>,
    CreateService<User, User> {

    private final UserRepository repository;
    private final MongoTemplate mongo;


    public User findByEmail(@NonNull String email) {
        return repository.findByEmail(email)
            .orElseThrow(() -> new BadRequestException(
                ErrorCodes.USER_NOT_FOUND,
                "user with email " + email + " not found"));
    }

    public void activeUser(@NonNull String email, @NonNull String userId) {
        var query = new Query(Criteria.where("email").is(email));

        var update = new Update()
            .set("status", User.Status.ACTIVE)
            .set("userId", userId);

        mongo.updateFirst(query, update, User.class);
    }

    @Override
    public User create(@NonNull User user) {
        if (repository.findByEmail(user.getEmail()).isPresent())
            throw new BadRequestException(
                ErrorCodes.USER_ALREADY_EXISTS,
                "user email duplicated"
            );

        var notHasOwnRole = user.getRoles()
            .stream()
            .noneMatch(role -> role.getName().equals(user.getType().name()));
        if (notHasOwnRole)
            user.getRoles().add(new Role(user.getType().name(), null));

        return repository.save(user);
    }

    @Override
    public List<User> index() {
        return repository.findAllByStatus(User.Status.ACTIVE);
    }

    @Override
    public User index(@NonNull String id) throws BadRequestException {
        return repository.findByIdAndStatus(id, User.Status.ACTIVE)
            .orElseThrow(() -> new BadRequestException(
                ErrorCodes.USER_NOT_FOUND,
                "user with id " + id + " not exists, was deleted or is not activated"));
    }
}
