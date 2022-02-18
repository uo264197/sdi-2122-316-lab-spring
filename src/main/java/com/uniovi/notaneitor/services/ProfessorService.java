package com.uniovi.notaneitor.services;

import com.uniovi.notaneitor.entities.Professor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;

@Service
public class ProfessorService {

    private List<Professor> professorList = new LinkedList<>();

    @PostConstruct
    public void init() {
        professorList.add(new Professor(1L, "1111111", "Profesor 1", "Apellidos 1", "Categoria 1"));
        professorList.add(new Professor(2L, "2222222", "Profesor 2","Apellidos 2",  "Categoria 2"));
    }

    public List<Professor> getProfessorList() {
        return professorList;
    }
    public Professor getProfessor(Long id) {
        return professorList.stream().filter(mark -> mark.getId().equals(id)).findFirst().get();
    }
    public void addProfessor(Professor professor) {
        if (professor.getId() == null) {
            professor.setId(professorList.get(professorList.size() - 1).getId() + 1);
        }
        professorList.add(professor);
    }
    public void deleteProfessor(Long id) {
        professorList.removeIf(professor -> professor.getId().equals(id));
    }
}
