package com.example.tmdbproject.check;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CheckValidator {

    @Autowired
    Validator validator;
    public <T> void validate(T validateObj) {
        Errors errors = new BeanPropertyBindingResult(validateObj, "validateObj");
        System.out.println(validateObj);
        validator.validate(validateObj, errors);

        if(errors.hasErrors()) {
            System.out.println("잘못된 정보가 전달되었습니다.");
            throw new RuntimeException("Invalid arguments");
        }
    }
}
