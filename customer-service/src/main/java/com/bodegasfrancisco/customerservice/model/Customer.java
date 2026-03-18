package com.bodegasfrancisco.customerservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
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

@Document(collection = "customers")
public class Customer {

    @Id
    private ObjectId id;

    @Field
    private String name;

    @Field
    private String lastname;

    @Field
    private String email;

    @Field
    private String phone;

    @Field
    private Status status = Status.ACTIVE;

    @Field
    @CreatedDate
    private Instant createdAt;

    @Field
    @LastModifiedDate
    private Instant updatedAt;

    @Field
    private List<CartItem> cart = new ArrayList<>();

    @Field
    private List<CustomerAddress> addresses = new ArrayList<>();

    @Field
    private List<CustomerCard> cards = new ArrayList<>();



    public enum Status {
        ACTIVE,
        DELETED,
        BANNED,
    }

}
