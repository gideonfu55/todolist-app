package com.bestapp.todolist.validation;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateValidator implements ConstraintValidator<DateNotPast, Date> {

  @Override
  public boolean isValid(Date date, ConstraintValidatorContext context) {
    // Convert Date to LocalDate
    LocalDate inputDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    // Get the current date
    LocalDate currentDate = LocalDate.now();
    // Check if inputDate is not before the current date, and return true
    return !inputDate.isBefore(currentDate);
  }

}
