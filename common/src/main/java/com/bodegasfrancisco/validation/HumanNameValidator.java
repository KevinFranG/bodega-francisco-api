package com.bodegasfrancisco.validation;

import com.bodegasfrancisco.validation.constraints.HumanName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HumanNameValidator implements ConstraintValidator<HumanName, String> {

    private static final String NAME_REGEX = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ]+(?:[ '\\-][a-zA-ZáéíóúÁÉÍÓÚñÑ]+)*$";


    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if (name == null || name.isEmpty()) return true;
        return name.matches(NAME_REGEX);
    }
}
