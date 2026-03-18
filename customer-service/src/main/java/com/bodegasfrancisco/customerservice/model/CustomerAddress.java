package com.bodegasfrancisco.customerservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAddress {

    private String label;

    private String city;

    private String state;

    private String postalCode;

    private String line1;

    private Boolean isDefault = false;
}
