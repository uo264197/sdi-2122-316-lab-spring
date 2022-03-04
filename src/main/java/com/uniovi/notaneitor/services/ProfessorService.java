package com.uniovi.notaneitor.services;

import com.uniovi.notaneitor.entities.Professor;
import com.uniovi.notaneitor.entities.User;
import com.uniovi.notaneitor.repositories.MarksRepository;
import com.uniovi.notaneitor.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    /*
    private List<Professor> professorList = new LinkedList<>();

    @PostConstruct
    public void init() {
        professorList.add(new Professor(1L, "1111111", "Profesor 1", "Apellidos 1", "Categoria 1"));
        professorList.add(new Professor(2L, "2222222", "Profesor 2","Apellidos 2",  "Categoria 2"));
    }
     */

    public List<Professor> getProfessorList() {
        List<Professor> professors = new ArrayList<Professor>();
        professorRepository.findAll().forEach(professors::add);
        return professors;
    }
    public Professor getProfessor(Long id) {
        return professorRepository.findById(id).get();
    }

    public Professor getProfessorByDni(String dni){
        return professorRepository.findByDni(dni);
    }

    public void addProfessor(Professor professor) {
        professorRepository.save(professor);
    }
    public void deleteProfessor(Long id) {
        professorRepository.deleteById(id);
    }
}
