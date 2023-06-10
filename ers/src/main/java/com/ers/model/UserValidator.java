package com.ers.model;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UserValidator {
    private static final String emailRegExp =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-zZa-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private Pattern pattern;

    public UserValidator() { pattern = Pattern.compile(emailRegExp);}

    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors){
        User user = (User)target;
        if(user.getEmail() == null || user.getEmail().trim().isEmpty()){
            errors.rejectValue("email", "required");
        }else{
            Matcher matcher = pattern.matcher(user.getEmail());
            if(!matcher.matches()){
                errors.rejectValue("email", "Email");
            }
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "required");

    }



}
