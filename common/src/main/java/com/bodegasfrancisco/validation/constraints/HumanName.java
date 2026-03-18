package com.bodegasfrancisco.validation.constraints;

import com.bodegasfrancisco.validation.HumanNameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = HumanNameValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT})
@Retention(RetentionPolicy.RUNTIME)
public @interface HumanName {

    String message() default "name is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
