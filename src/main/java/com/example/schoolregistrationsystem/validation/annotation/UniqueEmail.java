package com.example.schoolregistrationsystem.validation.annotation;

import com.example.schoolregistrationsystem.validation.logic.UniqueEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueEmailValidator.class)
@Target( { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {
    String message() default "EMAIL_NOT_UNIQUE";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
