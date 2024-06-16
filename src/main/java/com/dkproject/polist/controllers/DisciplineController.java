package com.dkproject.polist.controllers;

import com.dkproject.polist.entities.Discipline;
import com.dkproject.polist.repos.DisciplineRepo;
import com.dkproject.polist.services.DisciplineService;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/discipline")
public class DisciplineController {
    private final DisciplineService disciplineService;

    public DisciplineController(DisciplineService disciplineService) {
        this.disciplineService = disciplineService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addDiscipline(@RequestParam String name){
        return disciplineService.addDisciplineService(name);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteDiscipline(@RequestParam Long id){
        return disciplineService.deleteDisciplineService(id);
    }

    @GetMapping("/all")
    public List<Discipline> getDiscipline(){
        return disciplineService.allDisciplines();
    }
}
