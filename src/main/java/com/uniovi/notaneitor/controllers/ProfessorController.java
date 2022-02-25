package com.uniovi.notaneitor.controllers;

import com.uniovi.notaneitor.entities.Professor;
import com.uniovi.notaneitor.services.ProfessorService;
import com.uniovi.notaneitor.validators.ProfessorAddValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProfessorController {

    @Autowired
    private ProfessorAddValidator professorAddValidator;

    @Autowired
    private ProfessorService professorService;

    @RequestMapping("/professor/list")
    public String getList() {
        return professorService.getProfessorList().toString();
    }

    @RequestMapping(value = "/professor/add", method = RequestMethod.POST)
    public String setProfessor(@ModelAttribute Professor professor) {
        professorService.addProfessor(professor);
        return "Adding Professor " + professor.getNombre() + " " + professor.getApellidos();
    }

    @RequestMapping("/professor/details/{id}")
    public String getDetail(@PathVariable Long id) {
        return professorService.getProfessor(id).toString();
    }

    @RequestMapping("/professor/delete/{id}")
    public String deleteProfessor(@PathVariable Long id) {
        professorService.deleteProfessor(id);
        return "Deleting Professor";
    }

    @RequestMapping(value="/professor/edit/{id}", method=RequestMethod.POST)
    public String editProfessor(@ModelAttribute Professor professor, @PathVariable Long id) {
        professor.setId(id);
        professorService.addProfessor(professor);
        return "Deleting Professor";
    }

}
