package com.bodegasfrancisco.validation.constraints;

import com.bodegasfrancisco.validation.NotBlankIfPresentValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NotBlankIfPresentValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlankIfPresent {

    String message() default "must not be blank if present";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
