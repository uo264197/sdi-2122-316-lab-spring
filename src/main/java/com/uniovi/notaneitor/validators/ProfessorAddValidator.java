package com.uniovi.notaneitor.validators;

import com.uniovi.notaneitor.entities.Mark;
import com.uniovi.notaneitor.entities.Professor;
import com.uniovi.notaneitor.services.MarksService;
import com.uniovi.notaneitor.services.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.*;

@Component
public class ProfessorAddValidator implements Validator {

    @Autowired
    private ProfessorService professorService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Professor.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Professor professor = (Professor) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dni", "Error.empty");

        if (professor.getDni().length() < 9) {
            errors.rejectValue("dni", "Error.professor.dni.length");
        }

    }

}