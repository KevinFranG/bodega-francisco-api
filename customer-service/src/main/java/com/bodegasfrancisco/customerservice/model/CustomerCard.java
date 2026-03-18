package com.bodegasfrancisco.customerservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCard {

    private String holderName;

    private String cardBrand;

    private String last4;

    private Boolean isDefault = false;
}
