package com.uniovi.notaneitor.validators;

import com.uniovi.notaneitor.entities.Mark;
import com.uniovi.notaneitor.services.MarksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.*;

@Component
public class MarkEditValidator implements Validator {

    @Autowired
    private MarksService marksService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Mark.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Mark mark = (Mark) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "score", "Error.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "Error.empty");

        if (mark.getScore() < 0 || mark.getScore() > 10) {
            errors.rejectValue("score", "Error.mark.add.score.value");
        }

        if (mark.getDescription().length() < 20) {
            errors.rejectValue("description", "Error.mark.edit.description.length");
        }

    }

}
