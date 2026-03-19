package com.bodegasfrancisco.validation;

import com.bodegasfrancisco.validation.constraints.PhoneNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    private static final String PHONE_NUMBER_REGEX = "^\\+[1-9]\\d{1,14}$";


    @Override
    public boolean isValid(String number, ConstraintValidatorContext context) {
        if (number == null || number.isBlank()) return true;
        return number.matches(PHONE_NUMBER_REGEX);
    }
}
