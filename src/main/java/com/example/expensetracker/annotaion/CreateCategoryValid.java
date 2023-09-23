package com.example.expensetracker.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.example.expensetracker.annotaion.validation.CreateCategoryValidation;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Constraint(validatedBy = CreateCategoryValidation.class)
public @interface CreateCategoryValid {
  String message();

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
