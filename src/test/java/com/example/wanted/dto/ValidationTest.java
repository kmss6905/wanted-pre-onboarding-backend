package com.example.wanted.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

public class ValidationTest {

    protected Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

}