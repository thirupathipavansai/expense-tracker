package com.example.expensetracker.annotaion.validation;

import static com.example.expensetracker.miscellaneous.CategoryErrorMessage.INVALID_CATEGORY_NAME;

import java.util.EnumSet;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.example.expensetracker.annotaion.CreateCategoryValid;
import com.example.expensetracker.entity.category.CategoryName;
import com.example.expensetracker.request.category.CreateCategoryWebRequest;

public class CreateCategoryValidation implements ConstraintValidator<CreateCategoryValid, CreateCategoryWebRequest> {
  @Override
  public void initialize(CreateCategoryValid constraintAnnotation) {

  }

  @Override
  public boolean isValid(CreateCategoryWebRequest createCategoryWebRequest,
      ConstraintValidatorContext constraintValidatorContext) {
    if (!(EnumSet.allOf(CategoryName.class).stream()
        .anyMatch(categoryName -> categoryName.name().equals(createCategoryWebRequest.getCategoryName())))) {
      constraintValidatorContext.disableDefaultConstraintViolation();
      constraintValidatorContext.buildConstraintViolationWithTemplate(INVALID_CATEGORY_NAME).addConstraintViolation();

      return false;
    }
    return true;
  }
}
