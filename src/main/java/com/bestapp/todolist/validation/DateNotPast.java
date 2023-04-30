package com.bestapp.todolist.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateValidator.class)
public @interface DateNotPast {

  String message() default "The date should be at present or future.";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
  
}
