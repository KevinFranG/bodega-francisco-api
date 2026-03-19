package com.bodegasfrancisco.authservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Document(collection = "users")
public class User {

    @Id
    private String id;

    @Field
    private String userId;

    @Field
    private String username;

    @Field
    private String email;

    @Field
    private String password;

    @Field
    private Boolean enabled = true;

    @Field
    private Roles type;

    @Field
    private Status status = Status.PENDING;

    @Field
    private List<Role> roles = new ArrayList<>();

    @Field
    @CreatedDate
    private Instant createdAt;

    @Field
    @LastModifiedDate
    private Instant updatedAt;


    public enum Roles {
        ADMIN,
        CUSTOMER,
        EMPLOYEE
    }

    public enum Status {
        ACTIVE,
        PENDING,
        DELETED,
    }
}
